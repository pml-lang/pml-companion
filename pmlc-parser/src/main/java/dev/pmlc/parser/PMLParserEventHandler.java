package dev.pmlc.parser;

import dev.pdml.data.attribute.MutableNodeAttributes;
import dev.pdml.parser.eventhandler.*;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecs;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.nodespec.block.DocumentNodeSpec;
import dev.pmlc.data.nodespec.block.footnote.FootnotesPlaceholderNodeSpec;
import dev.pmlc.data.nodespec.inline.XrefNodeSpec;
import dev.pmlc.data.node.PMLNode;
import dev.pmlc.data.node.block.*;
import dev.pmlc.data.node.block.chapter.ChapterNode;
import dev.pmlc.data.node.block.chapter.SubtitleNode;
import dev.pmlc.data.node.block.chapter.TitleNode;
import dev.pmlc.data.node.block.footnote.FootnoteDefinitionNode;
import dev.pmlc.data.node.block.footnote.FootnotesPlaceholderNode;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pmlc.data.node.inline.TextNode;
import dev.pmlc.data.node.inline.XrefNode;
import dev.pmlc.data.node.inline.footnote.FootnoteReferenceNode;
import dev.pmlc.data.node.inline.footnote.InlineFootnoteNode;
import dev.pmlc.parser.PMLWhitespaceHelper.TextOrWhitespaceSegment;
import dev.pmlc.parser.PMLWhitespaceHelper.TextSegment;
import dev.pmlc.parser.PMLWhitespaceHelper.WhitespaceSegment;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.parameters.parameter.Parameter;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.text.inspection.message.TextError;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.inspection.message.TextWarning;
import dev.pp.text.token.TextToken;


import java.util.*;

public class PMLParserEventHandler implements PdmlParserEventHandler<PMLNode, DocumentNode> {

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


    private final @NotNull TextInspectionMessageHandler errorHandler;

    private final @NotNull PMLNodeSpecs nodeSpecs;

    private @Nullable DocumentNode documentNode = null;
    private @Nullable NodeStartEvent currentNodeStartEvent = null;
    private @Nullable PMLNodeSpec<?, ?> currentNodeSpec = null;
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

    // private final @NotNull PdmlReader PDMLReader;


    public PMLParserEventHandler (
        @NotNull PMLNodeSpecs nodeSpecs,
        @NotNull TextInspectionMessageHandler errorHandler ) {

        this.nodeSpecs = nodeSpecs;
        this.errorHandler = errorHandler;
    }


    public void onStart() {}

    public void onEnd() {}

    public @NotNull PMLNode onRootNodeStart ( @NotNull NodeStartEvent event ) throws Exception {

        PMLNode rootNode = sharedNodeStart ( event );

        if ( rootNode instanceof DocumentNode docNode ) {
            documentNode = docNode;
        } else {
            cancelingError (
                "Root node '" + event.name() + "' is invalid. The root node of a PML document must be '" + DocumentNodeSpec.NAME + "'.",
                "INVALID_ROOT_NODE",
                event.nameToken() );
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
                    "A node with id '" + nodeId + "' doesn't exist.",
                    "INVALID_NODE_ID",
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
                if ( chapterNode.getNodeId() == null ) {
                    chapterNode.setNodeId ( CHAPTER_AUTO_ID_PREFIX + chapterCounter );
                }
                chapterCounter++;

            } else if ( blockNode instanceof TitleNode chapterTitleNode ) {
                chapterTitleNode.setLevel ( currentChapterLevel );

            } else if ( blockNode instanceof SubtitleNode chapterSubtitleNode ) {
                chapterSubtitleNode.setLevel ( currentChapterLevel );
            }
        }

        if ( node.getNodeSpec().keepWhitespaceInText() ) {
            keepWhitespaceInText = true;
        }


        @Nullable String errorReason = null;

        if ( node instanceof PMLBlockNode childBlockNode ) {

            stopImplicitParagraphNode();

            if ( parentNode instanceof PMLBlockNode parentBlockNode ) {
                if ( parentNode.getNodeSpec ().isBlockChildNodesAllowed() ) {
                    parentBlockNode.addBlockChildNode ( childBlockNode );
                } else {
                    errorReason = "'" + parentNode.getName() + "' cannot contain block nodes.";
                }
            } else {
                errorReason = "inline nodes cannot contain block nodes";
            }

        } else if ( node instanceof PMLInlineNode childInlineNode ) {
            if ( parentNode instanceof PMLBlockNode parentBlockNode ) {
                if ( parentNode.getNodeSpec ().isInlineChildNodesAllowed() ) {
                    parentNode.addInlineChildNode ( childInlineNode );
                } else {
                    if ( parentNode.getNodeSpec ().isBlockChildNodesAllowed() ) {
                        ParagraphNode paragraphNode = createImplicitParagraphNodeIfNotExists (
                            parentBlockNode, event.nameToken() );
                        paragraphNode.addInlineChildNode ( childInlineNode );
                    } else {
                        errorReason = "'" + parentNode.getName() + "' cannot contain inline child nodes.";
                    }
                }

            } else {
                if ( parentNode.getNodeSpec ().isInlineChildNodesAllowed() ) {
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
                "Node '" + node.getName() +
                    "' cannot be contained in node '" + parentNode.getName() +
                    "', because " + errorReason + ".",
                "INVALID_CHILD_NODE",
                event.nameToken() );
        }

        return node;
    }

    public void onNodeEnd ( @NotNull NodeEndEvent event, @NotNull PMLNode node ) throws Exception {

        node.onNodeParsed ();

        if ( node instanceof PMLBlockNode ) {
            stopImplicitParagraphNode();
        }

        if ( node.getNodeSpec().keepWhitespaceInText() ) {
            keepWhitespaceInText = false;
        }

        if ( node instanceof ChapterNode ) {
            currentChapterLevel --;
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

/*
    public void onAttributes ( @Nullable ASTNodeAttributes attributes, @NotNull PMLNode parentNode ) throws Exception {

        setAttributes ( parentNode, attributes );
    }
 */

    public void onText ( @NotNull NodeTextEvent event, @NotNull PMLNode parentNode ) {

        if ( parentNode.getNodeSpec ().isRawTextNode() ) {
            parentNode.setRawText ( event.text() );

        } else if ( parentNode.getNodeSpec ().isInlineChildNodesAllowed() ) {
            addTextToInlineChildren ( event, parentNode );

        } else if ( parentNode instanceof PMLBlockNode parentBlockNode
            && parentBlockNode.getNodeSpec ().isBlockChildNodesAllowed() ) {
            addTextToBlockNode ( event, parentBlockNode );

        } else {
            nonCancelingError (
                "Node '" + parentNode.getName() + "' cannot contain text.",
                "INVALID_TEXT",
                event.textToken() );
        }
    }

    public void onComment ( @NotNull NodeCommentEvent comment, @NotNull PMLNode parentNode ) {}

    public @NotNull DocumentNode getResult() {

        assert documentNode != null;
        return documentNode;
    }


    // Private helpers


    private @NotNull PMLNode sharedNodeStart ( @NotNull NodeStartEvent event ) throws Exception {

        currentNodeStartEvent = event;
        currentNodeSpec = requireNodeSpec ( event );

        PMLNode node = currentNodeSpec.createInitialNode();
        node.setStartToken ( event.nameToken() );
        setAttributes ( node, event.attributes() );
        return node;
    }


    // Node Spec

    private @NotNull PMLNodeSpec<?, ?> requireNodeSpec ( @NotNull NodeStartEvent event ) throws DataValidatorException {

        String nodeName = event.name().qualifiedName();
        PMLNodeSpec<?, ?> nodeSpec = nodeSpecs.getOrNull ( nodeName );
        if ( nodeSpec == null ) cancelingError (
            "Node '" + nodeName + "' does not exist.",
            "INVALID_NODE_NAME",
            event.nameToken() );

        return nodeSpec;
    }


    // Attributes

    private void setAttributes (
        @NotNull PMLNode node,
        @Nullable MutableNodeAttributes parsedAttributes ) throws Exception {

/*
        @Nullable MutableParameters<String> mutableStringParameters =
            parsedAttributes == null ? null : parsedAttributes.toMutableStringParameters();
*/

        Parameters<String> stringParameters = setHTMLAttributes ( node, parsedAttributes );

        TextToken attributesStartToken = parsedAttributes == null ? null : parsedAttributes.getStartToken();
        if ( attributesStartToken == null ) {
            assert currentNodeStartEvent != null;
            attributesStartToken = currentNodeStartEvent.nameToken();
        }

        assert currentNodeSpec != null;
        @Nullable TextError initialLastError = errorHandler.lastError();
        @Nullable Parameters<?> attributes = node.stringToTypedAttributes (
            stringParameters,
            attributesStartToken,
            errorHandler );
        errorHandler.throwIfNewErrors ( initialLastError );

        // if ( attributes != null ) node.createAttributes ( attributes );
        node.setAttributes ( attributes );

        checkNodeId ( node, stringParameters );
        checkXrefNodeId ( node, stringParameters );
    }

    private void checkNodeId (
        @NotNull PMLNode node,
        @Nullable Parameters<String> stringParameters ) {

        if ( ! ( node instanceof @NotNull PMLBlockNode blockNode ) ) return; // only block nodes can have ids

        @Nullable String nodeId = blockNode.getNodeId();
        if ( nodeId == null ) return;

        if ( nodeId.startsWith ( CHAPTER_AUTO_ID_PREFIX ) ||
            nodeId.equals ( DocumentNode.DOCUMENT_AUTO_ID ) ) return;

        assert stringParameters != null;
        @NotNull Parameter<String> parameter = stringParameters.parameter ( SharedNodeSpecAttributes.ID_ATTRIBUTE_NAME );
        @Nullable TextToken token = parameter.valueToken();

        if ( ! parsedNodesWithId.containsKey ( nodeId ) ) {
            parsedNodesWithId.put ( nodeId, new ParsedNodeWithId ( blockNode, token ) );
        } else {
            nonCancelingError (
                "A node with id '" + nodeId + "' has already been declared at the following location:" +
                    StringConstants.OS_NEW_LINE + parsedNodesWithId.get ( nodeId ).idToken,
                "DUPLICATE_ID",
                token );
        }
    }

    private void checkXrefNodeId (
        @NotNull PMLNode node,
        @Nullable Parameters<String> stringParameters ) {

        if ( ! ( node instanceof XrefNode xrefNode ) ) return;

        assert stringParameters != null;
        @NotNull Parameter<String> parameter =
            stringParameters.parameter ( XrefNodeSpec.REFERENCED_NODE_ID_ATTRIBUTE.getName() );
        @Nullable TextToken token = parameter.valueToken();
        pendingXrefNodesToCheck.add ( new PendingXrefNodeToCheck ( xrefNode, token ) );
    }

    private @Nullable Parameters<String> setHTMLAttributes (
        @NotNull PMLNode node,
        @Nullable MutableNodeAttributes stringParameters ) {

        if ( stringParameters == null ) return null ;
        List<Parameter<String>> list = stringParameters.list();
        if ( list == null ) return null ;

        @Nullable Map<String, String> HtmlAttributes = new LinkedHashMap<>();
        for ( Parameter<String> stringParameter : list ) {
            String name = stringParameter.getName();
            if ( PMLAttributeUtils.isHTMLAttributeName ( name ) ) {
                HtmlAttributes.put (
                    PMLAttributeUtils.removeHTMLAttributePrefix ( name ),
                    stringParameter.getValue() );
                stringParameters.remove ( name );
            }
        }

        node.setHTMLAttributes ( HtmlAttributes.isEmpty() ? null : HtmlAttributes );

        return stringParameters.makeImmutableOrNull();
    }


    // Text

    private void addTextToInlineChildren ( @NotNull NodeTextEvent nodeTextEvent, @NotNull PMLNode parentNode ) {

        TextToken textToken = nodeTextEvent.textToken();
        if ( keepWhitespaceInText ) {
            parentNode.addInlineChildNode ( new TextNode ( textToken ) );
        } else {
            addTextSegmentsToInlineChildren ( textToken, parentNode );
        }
    }

    private void addTextToBlockNode ( final @NotNull NodeTextEvent nodeTextEvent, final @NotNull PMLBlockNode parentBlockNode ) {

        TextToken textToken = nodeTextEvent.textToken();
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
                "A footnote with id '" + id + "' has already been defined at the following location: " +
                    footnoteDefinitions.get ( id ).getStartToken(),
                "DUPLICATE_FOOTNOTE_ID",
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
                "There are no footnotes to be rendered.",
                "NO_FOOTNOTES_DEFINED",
                footnotesPlaceholder.getStartToken() );
            return;
        }

        nextRenderedFootnotePosition = 1;

        for ( FootnoteReferenceNode reference : unrenderedFootnoteReferences ) {
            String id = reference.getDefinitionId();
            FootnoteDefinitionNode definition = reference.getDefinitionNode();
            if ( definition == null ) {
                nonCancelingError (
                    "Footnote with id '" + id + "' has not yet been defined, and can therefore not be rendered at position: " + footnotesPlaceholder.getStartToken(),
                    "FOOTNOTE_NOT_DEFINED",
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
                "Footnote is not rendered, because there is no '" +
                FootnotesPlaceholderNodeSpec.NAME + "' node defined after it.",
                "UNRENDERED_FOOTNOTE",
                reference.getStartToken() );
        }
        if ( hasUnrenderedFootnote ) return;

        for ( FootnoteDefinitionNode definition : footnoteDefinitions.values() ) {
            String id = definition.getNodeId();
            if ( ! renderedFootnoteDefinitionIds.contains ( id ) ) {
                warning (
                    "Footnote definition with id '" + id + "' is never used.",
                    "UNUSED_FOOTNOTE",
                    definition.getStartToken() );
            }
        }
    }


    // Error handling

    private void cancelingError (
        @NotNull String message,
        @NotNull String id,
        @Nullable TextToken token ) throws DataValidatorException {

        throw new DataValidatorException ( message, id, token, null );
    }

    private void nonCancelingError (
        @NotNull String message,
        @NotNull String id,
        @Nullable TextToken token ) {

        errorHandler.handleMessage ( new TextError ( message, id, token ) );
    }

    private void warning (
        @NotNull String message,
        @NotNull String id,
        @Nullable TextToken token ) {

        errorHandler.handleMessage ( new TextWarning ( message, id, token ) );
    }
}
