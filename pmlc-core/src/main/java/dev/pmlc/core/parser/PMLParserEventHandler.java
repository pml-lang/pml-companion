package dev.pmlc.core.parser;

import dev.pdml.core.data.AST.attribute.ASTNodeAttributes;
import dev.pdml.core.parser.eventHandler.NodeEndEvent;
import dev.pdml.core.parser.eventHandler.NodeStartEvent;
import dev.pdml.core.parser.eventHandler.PDMLParserEventHandler;
import dev.pdml.core.reader.PDMLReader;
import dev.pdml.ext.utilities.parser.TextTokenParametersPDMLParser;
import dev.pmlc.core.PMLAttributeUtils;
import dev.pmlc.core.data.formalnode.FormalNodeRegistry;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.formalnode.block.FormalDocumentNode;
import dev.pmlc.core.data.formalnode.block.footnote.FormalFootnotesPlaceholderNode;
import dev.pmlc.core.data.formalnode.inline.FormalXrefNode;
import dev.pmlc.core.data.node.PMLNode;
import dev.pmlc.core.data.node.block.*;
import dev.pmlc.core.data.node.block.chapter.ChapterNode;
import dev.pmlc.core.data.node.block.chapter.SubtitleNode;
import dev.pmlc.core.data.node.block.chapter.TitleNode;
import dev.pmlc.core.data.node.block.footnote.FootnoteDefinitionNode;
import dev.pmlc.core.data.node.block.footnote.FootnotesPlaceholderNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pmlc.core.data.node.inline.TextNode;
import dev.pmlc.core.data.node.inline.XrefNode;
import dev.pmlc.core.data.node.inline.footnote.FootnoteReferenceNode;
import dev.pmlc.core.data.node.inline.footnote.InlineFootnoteNode;
import dev.pmlc.core.parser.PMLWhitespaceHelper.TextOrWhitespaceSegment;
import dev.pmlc.core.parser.PMLWhitespaceHelper.TextSegment;
import dev.pmlc.core.parser.PMLWhitespaceHelper.WhitespaceSegment;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.parameters.parameter.Parameters;
import dev.pp.parameters.parameter.ParametersCreator;
import dev.pp.parameters.textTokenParameter.TextTokenParameter;
import dev.pp.parameters.textTokenParameter.TextTokenParameters;
import dev.pp.text.error.TextError;
import dev.pp.text.error.handler.TextErrorHandler;
import dev.pp.text.error.TextErrorException;
import dev.pp.text.token.TextToken;


import java.io.IOException;
import java.util.*;

public class PMLParserEventHandler implements PDMLParserEventHandler<PMLNode, DocumentNode> {

    private static final @NotNull String CHAPTER_AUTO_ID_PREFIX = "ch__";
    private static final @NotNull String FOOTNOTE_REFERENCE_AUTO_ID_PREFIX = "fnr__";
    private static final @NotNull String FOOTNOTE_DEFINITION_AUTO_ID_PREFIX = "fnd__";

    private static class ParsedNodeWithId {

        @NotNull PMLBlockNode node;
        @Nullable TextToken idToken;

        ParsedNodeWithId (
            @NotNull PMLBlockNode node,
            @Nullable TextToken idToken ) {

            this.node = node;
            this.idToken = idToken;
        }
    }

    private static class PendingXrefNodeToCheck {

        @NotNull XrefNode xrefNode;
        @Nullable TextToken idToken;

        PendingXrefNodeToCheck (
            @NotNull XrefNode xrefNode,
            @Nullable TextToken idToken ) {

            this.xrefNode = xrefNode;
            this.idToken = idToken;
        }
    }


    private final @NotNull TextErrorHandler errorHandler;

    private @Nullable DocumentNode documentNode = null;
    private @Nullable NodeStartEvent currentNodeStartEvent = null;
    private @Nullable FormalPMLNode<?, ?> currentFormalNode = null;
    private int currentChapterLevel = 1;
    private int chapterCounter = 1;
    private boolean keepWhitespaceInText = false;
    private @Nullable ParagraphNode implicitParagraphNode = null;
    private @Nullable PMLBlockNode parentBlockNodeOfImplicitParagraphNode = null;
    private final @NotNull List<PendingXrefNodeToCheck> pendingXrefNodesToCheck = new ArrayList<>();
    private final @NotNull Map<String, ParsedNodeWithId> parsedNodesWithId = new HashMap<>();

    // Footnotes
    private int nextFootnoteReferenceAutoId = 1;
    private int nextFootnoteDefinitionAutoId = 1;
    private int nextRenderedFootnotePosition = 1;
    private final Map<String, FootnoteDefinitionNode> footnoteDefinitions = new HashMap<>();
    private final Map<String, List<FootnoteReferenceNode>> unresolvedFootnoteReferences = new HashMap<>();
    private final List<FootnoteReferenceNode> unrenderedFootnoteReferences = new ArrayList<>();
    private final Set<String> renderedFootnoteDefinitionIds = new HashSet<>();

    private final @NotNull PDMLReader PDMLReader;


    public PMLParserEventHandler (
        @NotNull TextErrorHandler errorHandler,
        @NotNull PDMLReader PDMLReader ) {

        this.errorHandler = errorHandler;
        this.PDMLReader = PDMLReader;
    }


    public void onStart() {}

    public void onEnd() {}

    public @NotNull PMLNode onRootNodeStart ( @NotNull NodeStartEvent event ) throws Exception {

        PMLNode rootNode = sharedNodeStart ( event );

        if ( rootNode instanceof DocumentNode docNode ) {
            documentNode = docNode;
        } else {
            cancelingError (
                "INVALID_ROOT_NODE",
                "Root node '" + event.getName() + "' is invalid. The root node of a PML document must be '" + FormalDocumentNode.NAME + "'.",
                event.getName().getToken() );
        }
        return documentNode;
    }

    public void onRootNodeEnd ( @NotNull NodeEndEvent event, @NotNull PMLNode rootNode ) {

        stopImplicitParagraphNode();

        // check referenced node ids in 'xref' nodes for nodes that appear after the 'xref' node.
        for ( PendingXrefNodeToCheck pendingXrefNodeToCheck : pendingXrefNodesToCheck ) {
            String nodeId = pendingXrefNodeToCheck.xrefNode.getReferencedNodeId();
            ParsedNodeWithId referencedNode = parsedNodesWithId.get ( nodeId );
            if ( referencedNode != null ) {
                pendingXrefNodeToCheck.xrefNode.setReferencedNode ( referencedNode.node );
            } else {
                nonCancelingError (
                    "INVALID_NODE_ID",
                    "Node id '" + nodeId + "' doesn't exist.",
                    pendingXrefNodeToCheck.idToken );
            }
        }

        checkFootnotesAtRootNodeEnd();
    }

    public @NotNull PMLNode onNodeStart ( @NotNull NodeStartEvent event, @NotNull PMLNode parentNode ) throws Exception {

        PMLNode node = sharedNodeStart ( event );
        node.setParentNode ( parentNode );

        if ( node instanceof PMLBlockNode blockNode ) {

            if ( blockNode instanceof ChapterNode chapterNode ) {
                chapterNode.setLevel ( currentChapterLevel );
                currentChapterLevel++;
                chapterNode.setNodeId ( CHAPTER_AUTO_ID_PREFIX + chapterCounter ); // might be overridden by attribute 'id'
                chapterCounter++;

            } else if ( blockNode instanceof TitleNode chapterTitleNode ) {
                chapterTitleNode.setLevel ( currentChapterLevel );

            } else if ( blockNode instanceof SubtitleNode chapterSubtitleNode ) {
                chapterSubtitleNode.setLevel ( currentChapterLevel );

            } else if ( blockNode instanceof MonospaceNode ) {
                keepWhitespaceInText = true;

            } else if ( blockNode instanceof OptionsNode configNode ) {
                handleConfigNode ( configNode, event.getToken () );
            }
        }

        @Nullable String errorReason = null;

        if ( node instanceof PMLBlockNode childBlockNode ) {

            stopImplicitParagraphNode();

            if ( parentNode instanceof PMLBlockNode parentBlockNode ) {
                if ( parentNode.getFormalNode().isBlockChildNodesAllowed() ) {
                    parentBlockNode.addBlockChildNode ( childBlockNode );
                } else {
                    errorReason = "'" + parentNode.getName() + "' cannot contain block nodes.";
                }
            } else {
                errorReason = "inline nodes cannot contain block nodes";
            }

        } else if ( node instanceof PMLInlineNode childInlineNode ) {
            if ( parentNode instanceof PMLBlockNode parentBlockNode ) {
                if ( parentNode.getFormalNode().isInlineChildNodesAllowed() ) {
                    parentNode.addInlineChildNode ( childInlineNode );
                } else {
                    if ( parentNode.getFormalNode().isBlockChildNodesAllowed() ) {
                        ParagraphNode paragraphNode = createImplicitParagraphNodeIfNotExists ( parentBlockNode, event.getToken() );
                        paragraphNode.addInlineChildNode ( childInlineNode );
                    } else {
                        errorReason = "'" + parentNode.getName() + "' cannot contain inline child nodes.";
                    }
                }

            } else {
                if ( parentNode.getFormalNode().isInlineChildNodesAllowed() ) {
                    parentNode.addInlineChildNode ( childInlineNode );
                } else {
                    errorReason = "'" + parentNode.getName() + "' cannot contain inline child nodes.";
                }
            }

        } else {
            throw new RuntimeException ( "Unexpected type of node '" + node.getName() + "'." );
        }

        if ( errorReason != null ) {
            cancelingError (
                "INVALID_CHILD_NODE",
                "Node '" + node.getName() +
                    "' cannot be contained in node '" + parentNode.getName() +
                    "', because " + errorReason + ".",
                event.getToken() );
        }

        return node;
    }

    public void onNodeEnd ( @NotNull NodeEndEvent event, @NotNull PMLNode node ) throws Exception {

        node.createData();

        if ( node instanceof PMLBlockNode ) {
            stopImplicitParagraphNode();
        }

        if ( node instanceof ChapterNode ) {
            currentChapterLevel --;
        } else if ( node instanceof MonospaceNode ) {
            keepWhitespaceInText = false;
        } else if ( node instanceof FootnoteReferenceNode footnoteReferenceNode ) {
            handleFootnoteReferenceNode ( footnoteReferenceNode );
        } else if ( node instanceof FootnoteDefinitionNode footnoteDefinitionNode ) {
            handleFootnoteDefinitionNode ( footnoteDefinitionNode );
        } else if ( node instanceof InlineFootnoteNode inlineFootnoteNode ) {
            handleInlineFootnoteNode ( inlineFootnoteNode );
        } else if ( node instanceof FootnotesPlaceholderNode footnotesPlaceholderNode ) {
            handleFootnotesPlaceholderNode ( footnotesPlaceholderNode );
        }
    }

    public void onAttributes ( @Nullable ASTNodeAttributes attributes, @NotNull PMLNode parentNode ) throws Exception {

        setAttributes ( parentNode, attributes );
    }

    public void onText ( @NotNull TextToken textToken, @NotNull PMLNode parentNode ) {

        if ( parentNode.getFormalNode().isRawTextNode() ) {
            parentNode.setRawText ( textToken.getText() );

        } else if ( parentNode.getFormalNode().isInlineChildNodesAllowed() ) {
            addTextToInlineChildren ( textToken, parentNode );

        } else if ( parentNode instanceof PMLBlockNode parentBlockNode
            && parentBlockNode.getFormalNode().isBlockChildNodesAllowed() ) {
            addTextToBlockNode ( textToken, parentBlockNode );

        } else {
            nonCancelingError (
                "INVALID_TEXT",
                "Node '" + parentNode.getName() + "' cannot contain text.",
                textToken );
        }
    }

    public void onComment ( @NotNull TextToken comment, @NotNull PMLNode parentNode ) {}

    public @NotNull DocumentNode getResult() {

        assert documentNode != null;
        return documentNode;
    }


    // Private helpers


    private @NotNull PMLNode sharedNodeStart ( @NotNull NodeStartEvent event ) throws DataValidatorException {

        currentNodeStartEvent = event;
        currentFormalNode = requireFormalNode ( event );

        PMLNode node = currentFormalNode.createInitialNode();
        node.setStartToken ( event.getToken() );
        return node;
    }


    // Formal Node

    private @NotNull FormalPMLNode<?, ?> requireFormalNode ( @NotNull NodeStartEvent event ) throws DataValidatorException {

        String nodeName = event.getName().qualifiedName();
        FormalPMLNode<?, ?> formalNode = FormalNodeRegistry.getByNameOrNull ( nodeName );
        if ( formalNode == null ) cancelingError (
            "INVALID_NODE_NAME",
            "Node '" + nodeName + "' does not exist.",
            event.getName().getToken() );

        return formalNode;
    }


    // Attributes

    private void setAttributes (
        @NotNull PMLNode node,
        @Nullable ASTNodeAttributes parsedAttributes ) throws Exception {

        @Nullable TextTokenParameters textTokenParameters =
            parsedAttributes == null ? null : parsedAttributes.toTextParameters();

        textTokenParameters = setHTMLAttributes ( node, textTokenParameters );

        TextToken attributesStartToken = parsedAttributes == null ? null : parsedAttributes.getStartToken();
        if ( attributesStartToken == null ) {
            assert currentNodeStartEvent != null;
            attributesStartToken = currentNodeStartEvent.getName().getToken();
        }

        // if ( textTokenParameters.isEmpty() ) textTokenParameters = null;
        assert currentFormalNode != null;
        @Nullable TextError initialLastError = errorHandler.lastError();
        @Nullable Parameters attributes = ParametersCreator.createFromTextParameters (
            textTokenParameters,
            attributesStartToken,
            currentFormalNode.getAttributes(),
            errorHandler );
        errorHandler.throwIfNewErrors ( initialLastError );

        // if ( attributes != null ) node.createAttributes ( attributes );
        node.createAttributes ( attributes );

        checkNodeId ( node, textTokenParameters );
        checkXrefNodeId ( node, textTokenParameters );
    }

    private void checkNodeId (
        @NotNull PMLNode node,
        @Nullable TextTokenParameters textTokenParameters ) {

        if ( ! ( node instanceof @NotNull PMLBlockNode blockNode ) ) return; // only block nodes can have ids

        @Nullable String nodeId = blockNode.getNodeId();
        if ( nodeId == null ) return;

        if ( nodeId.startsWith ( CHAPTER_AUTO_ID_PREFIX ) ||
            nodeId.equals ( DocumentNode.DOCUMENT_AUTO_ID ) ) return;

        assert textTokenParameters != null;
        @NotNull TextTokenParameter parameter = textTokenParameters.get ( SharedFormalNodeAttributes.ID_ATTRIBUTE_NAME );
        @Nullable TextToken token = parameter.getValueToken();

        if ( ! parsedNodesWithId.containsKey ( nodeId ) ) {
            parsedNodesWithId.put ( nodeId, new ParsedNodeWithId ( blockNode, token ) );
        } else {
            nonCancelingError (
                "DUPLICATE_ID",
                "A node with id '" + nodeId + "' has already been declared at the following location:" +
                    StringConstants.OS_NEW_LINE + parsedNodesWithId.get ( nodeId ).idToken,
                token );
        }
    }

    private void checkXrefNodeId (
        @NotNull PMLNode node,
        @Nullable TextTokenParameters textTokenParameters ) {

        if ( ! ( node instanceof XrefNode xrefNode ) ) return;

        @NotNull String referencedNodeId = xrefNode.getReferencedNodeId();
        @Nullable ParsedNodeWithId referencedNode = parsedNodesWithId.get ( referencedNodeId );
        if ( referencedNode != null ) {
            xrefNode.setReferencedNode ( referencedNode.node );
        } else {
            assert textTokenParameters != null;
            @NotNull TextTokenParameter parameter =
                textTokenParameters.get ( FormalXrefNode.REFERENCED_NODE_ID_ATTRIBUTE.getName() );
            @Nullable TextToken token = parameter.getValueToken();
            pendingXrefNodesToCheck.add ( new PendingXrefNodeToCheck ( xrefNode, token ) );
        }
    }

    private @Nullable TextTokenParameters setHTMLAttributes (
        @NotNull PMLNode node,
        @Nullable TextTokenParameters textTokenParameters ) {

        if ( textTokenParameters == null ) return null ;

        @Nullable Map<String, String> HTMLAttributes = new LinkedHashMap<>();
        for ( TextTokenParameter textTokenParameter : textTokenParameters.getList() ) {
            String name = textTokenParameter.getName();
            if ( PMLAttributeUtils.isHTMLAttributeName ( name ) ) {
                HTMLAttributes.put (
                    PMLAttributeUtils.removeHTMLAttributePrefix ( name ),
                    textTokenParameter.getValue() );
                textTokenParameters.remove ( name );
            }
        }

        node.setHTMLAttributes ( HTMLAttributes.isEmpty() ? null : HTMLAttributes );

        return textTokenParameters.isEmpty() ? null : textTokenParameters;
    }


    // Text

    private void addTextToInlineChildren ( @NotNull TextToken textToken, @NotNull PMLNode parentNode ) {

        if ( keepWhitespaceInText ) {
            parentNode.addInlineChildNode ( new TextNode ( textToken ) );
        } else {
            addTextSegmentsToInlineChildren ( textToken, parentNode );
        }
    }

    private void addTextToBlockNode ( final @NotNull TextToken textToken, final @NotNull PMLBlockNode parentBlockNode ) {

        if ( keepWhitespaceInText ) {
            // TODO?
            addTextTokenToNewOrExistingImplicitParagraphNode ( textToken, parentBlockNode );
        } else {
            addTextSegmentsToImplicitParagraphNode ( textToken, parentBlockNode );
        }
    }

    private void addTextSegmentsToInlineChildren ( @NotNull TextToken textToken, @NotNull PMLNode parentNode ) {

        for ( TextOrWhitespaceSegment segment : PMLWhitespaceHelper.createTextOrWhitespaceSegments ( textToken ) ) {

            String segmentString;
            if ( segment instanceof TextSegment textSegment ) {
                segmentString = textSegment.replaceNewLinesWithSpace();
            } else if ( segment instanceof WhitespaceSegment ) {
                segmentString = " "; // replace whitespace with a single space
            } else {
                throw new RuntimeException ( "Unexpected segment " + segment );
            }

            parentNode.addInlineChildNode ( new TextNode ( segmentString, segment.location ) );
        }
    }

    private void addTextSegmentsToImplicitParagraphNode (
        final @NotNull TextToken startToken,
        final @NotNull PMLBlockNode parentBlockNode ) {

        for ( TextOrWhitespaceSegment segment : PMLWhitespaceHelper.createTextOrWhitespaceSegments ( startToken ) ) {

            if ( segment instanceof TextSegment textSegment ) {
                TextToken token = new TextToken ( textSegment.replaceNewLinesWithSpace(), segment.location );
                addTextTokenToNewOrExistingImplicitParagraphNode ( token, parentBlockNode );

            } else if ( segment instanceof WhitespaceSegment whitespaceSegment ) {
                if ( whitespaceSegment.isParagraphBreak () ) {
                    implicitParagraphBreak ( new TextToken ( segment.string, segment.location ), parentBlockNode );

                } else {
                    TextToken token = new TextToken ( " ", segment.location );
                    addTextTokenToNewOrExistingImplicitParagraphNode ( token, parentBlockNode );
                }

            } else {
                throw new RuntimeException ( "Unexpected segment " + segment );
            }
        }
    }

    private void addTextTokenToNewOrExistingImplicitParagraphNode (
        @NotNull TextToken token,
        @NotNull PMLBlockNode parentBlockNode ) {

        @NotNull ParagraphNode paragraphNode = createImplicitParagraphNodeIfNotExists ( parentBlockNode, token );
        paragraphNode.addInlineChildNode ( new TextNode ( token ) );
    }

    private @NotNull ParagraphNode createImplicitParagraphNodeIfNotExists (
        @NotNull PMLBlockNode parentBlockNode,
        @NotNull TextToken startToken ) {

        return implicitParagraphNode == null
            ? createImplicitParagraphNode ( parentBlockNode, startToken )
            : implicitParagraphNode;
    }

    private @NotNull ParagraphNode createImplicitParagraphNode (
        @NotNull PMLBlockNode parentBlockNode,
        @NotNull TextToken startToken ) {

        assert implicitParagraphNode == null;
        assert parentBlockNodeOfImplicitParagraphNode == null;

        implicitParagraphNode = new ParagraphNode ( startToken );
        parentBlockNodeOfImplicitParagraphNode = parentBlockNode;

        return implicitParagraphNode;
    }

    private void stopImplicitParagraphNode() {

        if ( implicitParagraphNode == null ) return;

        @Nullable List<PMLInlineNode> childNodes = implicitParagraphNode.getInlineChildNodes();
        sanitizeParagraphNode ( childNodes );

        if ( childNodes != null && ! childNodes.isEmpty() ) {
            assert parentBlockNodeOfImplicitParagraphNode != null;
            parentBlockNodeOfImplicitParagraphNode.addBlockChildNode ( implicitParagraphNode );
        }

        implicitParagraphNode = null;
        parentBlockNodeOfImplicitParagraphNode = null;
    }

    private void sanitizeParagraphNode ( @Nullable List<PMLInlineNode> childNodes ) {

        if ( childNodes == null || childNodes.isEmpty() ) return;

        // remove single leading space
        removeChildIfOneSpace ( childNodes, 0 );
        // remove single trailing space
        if ( childNodes.size() >= 1 ) {
            removeChildIfOneSpace ( childNodes, childNodes.size() - 1 );
        }
    }

    private void removeChildIfOneSpace ( @NotNull List<PMLInlineNode> childNodes, int index ) {

        PMLInlineNode childNode = childNodes.get ( index );
        if ( childNode instanceof TextNode textNode ) {
            if ( textNode.getText().equals ( " " ) ) {
                childNodes.remove ( index );
            }
        }
    }

    private void implicitParagraphBreak ( @NotNull TextToken startToken, @NotNull PMLBlockNode parentBlockNode ) {

        PMLBlockNode parent = parentBlockNodeOfImplicitParagraphNode;
        // assert parent != null;
        if ( parent == null ) parent = parentBlockNode;
        stopImplicitParagraphNode();
        createImplicitParagraphNode ( parent, startToken );
    }


    // Handle footnote nodes

    private void handleFootnoteReferenceNode ( @NotNull FootnoteReferenceNode footnoteReference ) {

        footnoteReference.setId ( footnoteReferenceAutoId () );

        String definitionId = footnoteReference.getDefinitionId();
        FootnoteDefinitionNode existingDefinition = footnoteDefinitions.get ( definitionId );
        if ( existingDefinition != null ) {
            footnoteReference.setDefinitionNode ( existingDefinition );
            existingDefinition.addReference ( footnoteReference );
        } else {
            List<FootnoteReferenceNode> unresolvedReferences = unresolvedFootnoteReferences.get ( definitionId );
            if ( unresolvedReferences == null ) {
                unresolvedReferences = new ArrayList<>();
                unresolvedFootnoteReferences.put ( definitionId, unresolvedReferences );
            }
            unresolvedReferences.add ( footnoteReference );
        }

        unrenderedFootnoteReferences.add ( footnoteReference );
    }

    private void handleFootnoteDefinitionNode ( @NotNull FootnoteDefinitionNode footnoteDefinition ) {

        String id = footnoteDefinition.getNodeId();
        assert id != null;
        /* Not necessary, because the id of every node must be unique, and this has been checked already
        if ( footnoteDefinitions.containsKey ( id ) ) {
            nonCancelingError (
                "DUPLICATE_FOOTNOTE_ID",
                "A footnote with id '" + id + "' has already been defined at the following location: " +
                    footnoteDefinitions.get ( id ).getStartToken(),
                footnoteDefinition.getStartToken() );
        }
         */
        footnoteDefinitions.put ( id, footnoteDefinition );

        List<FootnoteReferenceNode> unresolvedReferences = unresolvedFootnoteReferences.get ( id );
        if ( unresolvedReferences != null ) {
            for ( FootnoteReferenceNode reference : unresolvedReferences ) {
                reference.setDefinitionNode ( footnoteDefinition );
                footnoteDefinition.addReference ( reference );
            }
            unresolvedFootnoteReferences.remove ( id );
        }
    }

    private void handleFootnotesPlaceholderNode ( @NotNull FootnotesPlaceholderNode footnotesPlaceholder ) {

        if ( unrenderedFootnoteReferences.isEmpty() ) {
            warning (
                "NO_FOOTNOTES_DEFINED",
                "There are no footnotes to be rendered.",
                footnotesPlaceholder.getStartToken() );
            return;
        }

        nextRenderedFootnotePosition = 1;

        for ( FootnoteReferenceNode reference : unrenderedFootnoteReferences ) {
            String id = reference.getDefinitionId();
            FootnoteDefinitionNode definition = reference.getDefinitionNode();
            if ( definition == null ) {
                nonCancelingError (
                    "FOOTNOTE_NOT_DEFINED",
                    "Footnote with id '" + id + "' has not yet been defined, and can therefore not be rendered at position: " + footnotesPlaceholder.getStartToken(),
                    reference.getStartToken() );
                continue;
            }

            if ( ! renderedFootnoteDefinitionIds.contains ( id ) ) {
                definition.setRenderPosition ( nextRenderedFootnotePosition );
                nextRenderedFootnotePosition ++;
                footnotesPlaceholder.addDefinition ( definition );
                renderedFootnoteDefinitionIds.add ( id );
            }
        }

        unrenderedFootnoteReferences.clear();
    }

    private void handleInlineFootnoteNode ( @NotNull InlineFootnoteNode inlineFootnote ) {

        ParagraphNode paragraphNode = new ParagraphNode();
        paragraphNode.setInlineChildNodes ( inlineFootnote.getInlineChildNodes() );
        FootnoteDefinitionNode definitionNode = new FootnoteDefinitionNode();
        definitionNode.addBlockChildNode ( paragraphNode );
        String definitionId = footnoteDefinitionAutoId();
        definitionNode.setNodeId ( definitionId );
        footnoteDefinitions.put ( definitionId, definitionNode );

        FootnoteReferenceNode referenceNode = new FootnoteReferenceNode ();
        referenceNode.setStartToken ( inlineFootnote.getStartToken() );
        referenceNode.setId ( footnoteReferenceAutoId () );
        referenceNode.setDefinitionId ( definitionId );
        referenceNode.setDefinitionNode ( definitionNode );
        definitionNode.addReference ( referenceNode );
        unrenderedFootnoteReferences.add ( referenceNode );

        inlineFootnote.setReferenceNode ( referenceNode );
        // inlineFootnote.setDefinitionNode ( definitionNode );
    }

    private String footnoteReferenceAutoId () {

        String r = FOOTNOTE_REFERENCE_AUTO_ID_PREFIX + nextFootnoteReferenceAutoId;
        nextFootnoteReferenceAutoId++;
        return r;
    }

    private String footnoteDefinitionAutoId() {

        String r = FOOTNOTE_DEFINITION_AUTO_ID_PREFIX + nextFootnoteDefinitionAutoId;
        nextFootnoteDefinitionAutoId++;
        return r;
    }

    private void checkFootnotesAtRootNodeEnd() {

        boolean hasUnrenderedFootnote = false;
        for ( FootnoteReferenceNode reference : unrenderedFootnoteReferences ) {
            hasUnrenderedFootnote = true;
            warning (
                "UNRENDERED_FOOTNOTE",
                "Footnote is not rendered, because there is no '" +
                FormalFootnotesPlaceholderNode.NAME + "' node defined after it.",
                reference.getStartToken() );
        }
        if ( hasUnrenderedFootnote ) return;

        for ( FootnoteDefinitionNode definition : footnoteDefinitions.values() ) {
            String id = definition.getNodeId();
            if ( ! renderedFootnoteDefinitionIds.contains ( id ) ) {
                warning (
                    "UNUSED_FOOTNOTE",
                    "Footnote definition with id '" + id + "' is never used.",
                    definition.getStartToken() );
            }
        }
    }


    // Config

    private void handleConfigNode (
        @NotNull OptionsNode configNode,
        @Nullable TextToken startToken ) throws IOException, TextErrorException {

        // re-insert '[config ' that has been consumed already
        PDMLReader.insertStringToRead ( "[" + configNode.getName() + " " );

        TextTokenParameters textTokenParameters = TextTokenParametersPDMLParser.parseAndAdd (
            PDMLReader, new TextTokenParameters ( startToken ), true );

        // re-insert ] of node 'config' that has been consumed already
        PDMLReader.insertStringToRead ( "]" );

        configNode.setParameters ( textTokenParameters );

        // TODO (re?)create config
    }


    // Error handling

    private void cancelingError (
        @NotNull String id,
        @NotNull String message,
        @Nullable TextToken token ) throws DataValidatorException {

        throw new DataValidatorException ( id, message, token, null );
    }

    private void nonCancelingError (
        @NotNull String id,
        @NotNull String message,
        @Nullable TextToken token ) {

        errorHandler.handleAbortingError ( id, message, token );
    }

    private void warning (
        @NotNull String id,
        @NotNull String message,
        @Nullable TextToken token ) {

        errorHandler.handleNonAbortingWarning ( id, message, token );
    }
}
