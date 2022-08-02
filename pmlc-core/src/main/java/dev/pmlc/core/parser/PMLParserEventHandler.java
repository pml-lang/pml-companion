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
import dev.pmlc.core.data.formalnode.inline.FormalXrefNode;
import dev.pmlc.core.data.node.PMLNode;
import dev.pmlc.core.data.node.block.*;
import dev.pmlc.core.data.node.block.chapter.ChapterNode;
import dev.pmlc.core.data.node.block.chapter.ChapterSubtitleNode;
import dev.pmlc.core.data.node.block.chapter.ChapterTitleNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pmlc.core.data.node.inline.TextNode;
import dev.pmlc.core.data.node.inline.XrefNode;
import dev.pmlc.core.parser.PMLParserEventHandlerHelper.TextOrWhitespaceSegment;
import dev.pmlc.core.parser.PMLParserEventHandlerHelper.TextSegment;
import dev.pmlc.core.parser.PMLParserEventHandlerHelper.WhitespaceSegment;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.parameters.parameter.list.Parameters;
import dev.pp.parameters.parameter.list.ParametersCreator;
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
    private final @NotNull List<PendingXrefNodeToCheck> pendingXrefNodesToCheck = new ArrayList<>();
    private final @NotNull Map<String, ParsedNodeWithId> parsedNodesWithId = new HashMap<>();

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
    }

    public @NotNull PMLNode onNodeStart ( @NotNull NodeStartEvent event, @NotNull PMLNode parentNode ) throws Exception {

        PMLNode node = sharedNodeStart ( event );
        node.setParentNode ( parentNode );

        if ( node instanceof PMLBlockNode blockNode ) {

            implicitParagraphNode = null;

            if ( blockNode instanceof ChapterNode chapterNode ) {
                chapterNode.setLevel ( currentChapterLevel );
                currentChapterLevel++;
                chapterNode.setNodeId ( CHAPTER_AUTO_ID_PREFIX + chapterCounter ); // might be overridden by attribute 'id'
                chapterCounter++;

            } else if ( blockNode instanceof ChapterTitleNode chapterTitleNode ) {
                chapterTitleNode.setLevel ( currentChapterLevel );

            } else if ( blockNode instanceof ChapterSubtitleNode chapterSubtitleNode ) {
                chapterSubtitleNode.setLevel ( currentChapterLevel );

            } else if ( blockNode instanceof MonospaceNode ) {
                keepWhitespaceInText = true;

            } else if ( blockNode instanceof OptionsNode configNode ) {
                handleConfigNode ( configNode, event.getToken() );
            }
        }

        FormalPMLNode<?, ?> formalParentNode = parentNode.getFormalNode();

        if ( parentNode instanceof PMLBlockNode parentBlockNode
            && node instanceof PMLBlockNode childBlockNode
            && formalParentNode.isBlockChildNodesAllowed() ) {

            parentBlockNode.addBlockChildNode ( childBlockNode );

        } else if ( node instanceof PMLInlineNode childInlineNode
            && formalParentNode.isInlineChildNodesAllowed() ) {

            parentNode.addInlineChildNode ( childInlineNode );

        } else if ( parentNode instanceof PMLBlockNode parentBlockNode
            && node instanceof PMLInlineNode childInlineNode
            && formalParentNode.isBlockChildNodesAllowed() ) {

            createImplicitParagraphNodeIfNotExists ( parentBlockNode );
            assert implicitParagraphNode != null;
            implicitParagraphNode.addInlineChildNode ( childInlineNode );

        } else {
            cancelingError (
                "INVALID_CHILD_NODE",
                "Node '" + event.getName() + "' cannot be contained in node '" + parentNode.getName() + "'.",
                event.getName().getToken() );
        }

        return node;
    }

    public void onNodeEnd ( @NotNull NodeEndEvent event, @NotNull PMLNode node ) throws Exception {

        node.createData();

        if ( node instanceof ChapterNode ) {
            currentChapterLevel --;

        } else if ( node instanceof MonospaceNode ) {
            keepWhitespaceInText = false;

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
            invalidTextError ( textToken, parentNode );
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

        String text = textToken.getText();
        if ( keepWhitespaceInText ) {
            parentNode.addInlineChildNode ( new TextNode ( text ) );
        } else {
            addTextSegmentsToInlineChildren ( text, parentNode );
        }
    }

    private void addTextToBlockNode ( final @NotNull TextToken textToken, final @NotNull PMLBlockNode parentBlockNode ) {

        String text = textToken.getText();
        if ( keepWhitespaceInText ) {
            addTextToNewOrExistingImplicitParagraphNode ( parentBlockNode, text );
        } else {
            addTextParagraphsToBlockNode ( text, parentBlockNode );
        }
    }

    private void addTextSegmentsToInlineChildren ( @NotNull String text, @NotNull PMLNode parentNode ) {

        for ( TextOrWhitespaceSegment segment : PMLParserEventHandlerHelper.createTextOrWhitespaceSegments ( text ) ) {

            String segmentString;
            if ( segment instanceof TextSegment textSegment ) {
                segmentString = textSegment.string;
            } else if ( segment instanceof WhitespaceSegment ) {
                segmentString = " "; // replace whitespace with a single space
            } else {
                throw new RuntimeException ( "Unexpected segment " + segment );
            }

            parentNode.addInlineChildNode ( new TextNode ( segmentString ) );
        }
    }

    private void addTextParagraphsToBlockNode ( final @NotNull String text, final @NotNull PMLBlockNode parentBlockNode ) {

        List<TextOrWhitespaceSegment> segments =
            PMLParserEventHandlerHelper.createTextOrWhitespaceSegments ( text );

        // skip if it's only whitespace
        if ( segments.size() == 1 ) {
            TextOrWhitespaceSegment singleSegment = segments.get ( 0 );
            if ( singleSegment instanceof WhitespaceSegment whitespaceSegment ) {
                if ( ! whitespaceSegment.isParagraphBreak() ) return;
            }
            if ( singleSegment instanceof TextSegment textSegment ) {
                if ( textSegment.string.isBlank() ) return;
            }
        }

        for ( int index = 0; index < segments.size(); index++ ) {
            TextOrWhitespaceSegment segment = segments.get ( index );

            if ( segment instanceof TextSegment textSegment ) {
                addTextToNewOrExistingImplicitParagraphNode ( parentBlockNode, textSegment.string );

            } else if ( segment instanceof WhitespaceSegment whitespaceSegment ) {
                if ( whitespaceSegment.isParagraphBreak() ) {
                    implicitParagraphNode = null;

                } else {
                    boolean isFirstItemOfParagraph =
                        index == 0
                        || implicitParagraphNode == null
                        || ! implicitParagraphNode.hasInlineChildNodes();
                    boolean isLastItemOfParagraph = index == segments.size() - 1;
                    if ( ! isFirstItemOfParagraph && ! isLastItemOfParagraph ) {
                        addTextToNewOrExistingImplicitParagraphNode ( parentBlockNode, " " );
                    }
                }

            } else {
                throw new RuntimeException ( "Unexpected segment " + segment );
            }
        }
    }

    private void addTextToNewOrExistingImplicitParagraphNode (
        @NotNull PMLBlockNode parentBlockNode,
        @NotNull String text ) {

        createImplicitParagraphNodeIfNotExists ( parentBlockNode );
        assert implicitParagraphNode != null;
        implicitParagraphNode.addInlineChildNode ( new TextNode ( text ) );
    }

    private void createImplicitParagraphNodeIfNotExists ( @NotNull PMLBlockNode parentBlockNode ) {

        if ( implicitParagraphNode == null ) createImplicitParagraphNode ( parentBlockNode );
    }

    private void createImplicitParagraphNode ( @NotNull PMLBlockNode parentBlockNode ) {

        implicitParagraphNode = new ParagraphNode();
        parentBlockNode.addBlockChildNode ( implicitParagraphNode );
    }

    private void invalidTextError ( @NotNull TextToken text, @NotNull PMLNode parentNode ) {

        nonCancelingError (
            "INVALID_TEXT",
            "Node '" + parentNode.getName() + "' cannot contain text.",
            text );
    }


    // Config

    private void handleConfigNode (
        @NotNull OptionsNode configNode,
        @Nullable TextToken startToken ) throws IOException, TextErrorException {

        // re-insert '[config ' that has been consumed already
        PDMLReader.insertStringToRead ( "[" + configNode.getName() + " " );

        TextTokenParameters textTokenParameters = TextTokenParametersPDMLParser.parseAndAdd (
            PDMLReader, new TextTokenParameters ( startToken ), true );
        // DebugUtils.writeNameValue ( "textTokenParameters", textTokenParameters );

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
}
