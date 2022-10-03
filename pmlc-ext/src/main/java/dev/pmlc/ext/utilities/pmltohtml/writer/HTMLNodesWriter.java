package dev.pmlc.ext.utilities.pmltohtml.writer;

import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.block.*;
import dev.pmlc.core.data.formalnode.block.chapter.FormalChapterNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalSubtitleNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalTitleNode;
import dev.pmlc.core.data.formalnode.block.code.FormalHTMLCodeNode;
import dev.pmlc.core.data.formalnode.block.code.FormalInputNode;
import dev.pmlc.core.data.formalnode.block.code.FormalOutputNode;
import dev.pmlc.core.data.formalnode.block.footnote.FormalFootnoteDefinitionNode;
import dev.pmlc.core.data.formalnode.block.footnote.FormalFootnotesPlaceholderNode;
import dev.pmlc.core.data.formalnode.block.list.FormalListElementNode;
import dev.pmlc.core.data.formalnode.block.list.FormalListNode;
import dev.pmlc.core.data.formalnode.block.table.*;
import dev.pmlc.core.data.formalnode.inline.FormalLinkNode;
import dev.pmlc.core.data.formalnode.inline.FormalSpanNode;
import dev.pmlc.core.data.formalnode.inline.FormalXrefNode;
import dev.pmlc.core.data.formalnode.inline.font.*;
import dev.pmlc.core.data.formalnode.inline.footnote.FormalFootnoteReferenceNode;
import dev.pmlc.core.data.node.block.*;
import dev.pmlc.core.data.node.block.chapter.ChapterNode;
import dev.pmlc.core.data.node.block.chapter.SubtitleNode;
import dev.pmlc.core.data.node.block.chapter.TitleNode;
import dev.pmlc.core.data.node.block.code.*;
import dev.pmlc.core.data.node.block.footnote.FootnoteDefinitionNode;
import dev.pmlc.core.data.node.block.footnote.FootnotesPlaceholderNode;
import dev.pmlc.core.data.node.block.list.ListElementNode;
import dev.pmlc.core.data.node.block.list.ListNode;
import dev.pmlc.core.data.node.block.media.AudioNode;
import dev.pmlc.core.data.node.block.media.ImageNode;
import dev.pmlc.core.data.node.block.media.VideoNode;
import dev.pmlc.core.data.node.block.media.YoutubeVideoNode;
import dev.pmlc.core.data.node.block.table.*;
import dev.pmlc.core.data.node.inline.*;
import dev.pmlc.core.data.node.inline.font.*;
import dev.pmlc.core.data.node.inline.footnote.FootnoteReferenceNode;
import dev.pmlc.core.data.node.inline.footnote.InlineFootnoteNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pmlc.ext.utilities.pmltohtml.options.PMLToHTMLOptions;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.utilities.html.HTMLWriter;

import java.util.List;
import java.util.Map;

public class HTMLNodesWriter implements PMLNodesHandler {


    // private static final @NotNull String CSS_CLASS_ATTRIBUTE_NAME = "class";


    private final @NotNull PMLToHTMLOptions options;
    private final @NotNull HTMLNodesWriterHelper helper;

    private boolean writingTableHeader = false;
    private boolean writingTableFooter = false;


    public HTMLNodesWriter (
        @NotNull HTMLWriter writer,
        @NotNull PMLToHTMLOptions options ) {

        this.options = options;
        this.helper = new HTMLNodesWriterHelper ( writer, options, this );
    }


    public @NotNull String getName () { return "HTML"; }


    public void start() {}

    public void end() {}


    // Block Nodes

    public void admonition ( @NotNull AdmonitionNode node ) throws Exception {

        AdmonitionHTMLWriter.writeAdmonition ( node, node.getLabel(), this, helper );
    }

    public void audio ( @NotNull AudioNode node ) throws Exception {

        MediaHTMLWriter.writeAudio ( node, helper );
    }

    public void caption ( @NotNull CaptionNode node ) throws Exception {

        helper.writeBlockNodeWithInlineChildren ( node, FormalCaptionNode.HTML_TAG, FormalCaptionNode.CSS_CLASS );
    }

    public void chapter ( @NotNull ChapterNode node ) throws Exception {

        helper.writeBlockNodeWithBlockChildren ( node, FormalChapterNode.HTML_TAG, FormalChapterNode.CSS_CLASS );
    }

    public void chapterSubtitle ( @NotNull SubtitleNode node ) throws Exception {

        writeChapterTitleNode ( node, node.getLevel(), FormalSubtitleNode.CSS_CLASS );
    }

    public void chapterTitle ( @NotNull TitleNode node ) throws Exception {

        writeChapterTitleNode ( node, node.getLevel(), FormalTitleNode.CSS_CLASS );

        if ( node.isDocumentTitle() && options.TOCPosition() == TOCPosition.TOP ) {
            DocumentNode documentNode = (DocumentNode) node.getParentNode();
            assert documentNode != null;
            @NotNull TOCNode rootTOCNode = documentNode.createTOC ( options.maxTOCChapterLevel() );
            TOC ( rootTOCNode );
        }
    }

    private void writeChapterTitleNode ( @NotNull PMLBlockNode node, int level, @NotNull String CSSClass ) throws Exception {

        String tag = "h" + ( Math.min ( level, 6 ) );
        String CSSClass_ =
            level == 1
                ? FormalPMLNodeCreator.prefixedHTMLClassName ( "doc-title" )
                : CSSClass;

        helper.writeBlockNodeWithInlineChildren ( node, tag, CSSClass_ );
    }

    public void division ( @NotNull DivisionNode node ) throws Exception {

        helper.writeBlockNodeWithBlockChildren ( node, FormalDivisionNode.HTML_TAG, FormalDivisionNode.CSS_CLASS );
    }

    public void document ( @NotNull DocumentNode node ) throws Exception {

        String tag = FormalDocumentNode.HTML_TAG;

        @Nullable String nodeId = node.getNodeId();
        helper.writeBlockNodeStartLine ( node, tag, FormalDocumentNode.CSS_CLASS,
            nodeId == null ? null : Map.of ( "id", nodeId ), true );

        /*
        if ( options.TOCPosition() == TOCPosition.TOP ) {
            @NotNull TOCNode rootTOCNode = node.createTOC ( options.maxTOCChapterLevel() );
            TOC ( rootTOCNode );
        }
        */

        handleChildNodes ( node.getBlockChildNodes() );

        helper.decreaseIndent();
        helper.writeBlockEndLine ( tag );
    }

    public void footnoteDefinition ( @NotNull FootnoteDefinitionNode node ) throws Exception {

        // definitions are rendered in footnotes (node [fnotes])
    }

    private void footnoteDefinition_ ( @NotNull FootnoteDefinitionNode node ) throws Exception {

        helper.writeHTMLBlockStartTag ( FormalFootnoteDefinitionNode.HTML_TAG, node.getNodeId(),
            FormalFootnoteDefinitionNode.CSS_CLASS, node.getHTMLAttributes(), null );
        helper.increaseIndent();

        // <td>1.</td>
        helper.writeIndent();
        Map<String, String> tdAttributes = Map.of ( "style", "vertical-align: top;" );
        helper.writeHTMLStartTag ( "td", null, null, null, tdAttributes );
        helper.write ( String.valueOf ( node.getRenderPosition() ) );
        helper.write ( "." );
        helper.writeHTMLEndTag ( "td" );
        helper.writeNewLine();

        // <td>^^</td>
        helper.writeIndent();
        helper.writeHTMLStartTag ( "td", null, FormalPMLNodeCreator.prefixedHTMLClassName ( "footnote-backlink" ),
            null, tdAttributes );
        for ( FootnoteReferenceNode reference : node.getReferences () ) {
            helper.writeHTMLATag ( "#" + reference.getId() );
            helper.write ( "^" );
            helper.writeHTMLEndTag ( "a" );
        }
        helper.writeHTMLEndTag ( "td" );
        helper.writeNewLine();

        // <td>
        //     content
        // </td>
        helper.writeHTMLBlockStartTag ( "td", null, null, null, null );
        helper.increaseIndent();
        handleChildNodes ( node.getChildNodes() );
        helper.decreaseIndent();
        helper.writeBlockEndLine ( "td" );

        helper.decreaseIndent();
        helper.writeBlockEndLine ( FormalFootnoteDefinitionNode.HTML_TAG );
    }

    public void footnotes ( @NotNull FootnotesPlaceholderNode node ) throws Exception {

        helper.writeBlockStartLine ( FormalFootnotesPlaceholderNode.HTML_TAG, FormalFootnotesPlaceholderNode.CSS_CLASS );
        helper.increaseIndent();

        helper.writeHTMLBlockStartTag ( "table", null, null, null, null );
        helper.increaseIndent();

        List<FootnoteDefinitionNode> definitions = node.getDefinitions();
        if ( definitions != null ) {
            for ( FootnoteDefinitionNode definition : definitions ) {
                footnoteDefinition_ ( definition );
            }
        }

        helper.decreaseIndent();
        helper.writeBlockEndLine ( "table" );

        helper.decreaseIndent();
        helper.writeBlockEndLine ( FormalFootnotesPlaceholderNode.HTML_TAG );
    }

    public void header ( @NotNull HeaderNode node ) throws Exception {

        helper.writeBlockNodeWithInlineChildren ( node, FormalHeaderNode.HTML_TAG, FormalHeaderNode.CSS_CLASS );
    }

    public void HTMLCode ( @NotNull HTMLCodeNode node ) throws Exception {

        helper.writeIndent();
        String tag = FormalHTMLCodeNode.HTML_TAG;
        helper.writeHTMLStartTag ( tag, node.getNodeId(), FormalHTMLCodeNode.CSS_CLASS, node.getHTMLAttributes(), null );
        @Nullable String code = node.getRawText();
        if ( code != null ) helper.write ( code );
        helper.writeHTMLEndTag ( tag );
        helper.writeNewLine();
    }

    public void image ( @NotNull ImageNode node ) throws Exception {

        MediaHTMLWriter.writeImage ( node, helper );
    }

    public void input ( @NotNull InputNode node ) throws Exception {

        RawTextHTMLWriter.writeInputOutput ( node, FormalInputNode.CSS_CLASS, helper );
    }

    public void insertSourceCode ( @NotNull InsertSourceCodeNode node ) throws Exception {

        RawTextHTMLWriter.writeSourceCode ( node, node.getLanguage(), node.getUseHighlighter(), helper );
    }

    public void list ( @NotNull ListNode node ) throws Exception {

        helper.writeBlockNodeWithBlockChildren ( node, FormalListNode.HTML_TAG, FormalListNode.CSS_CLASS );
    }

    public void listElement ( @NotNull ListElementNode node ) throws Exception {

        helper.writeBlockNodeWithBlockChildren ( node, FormalListElementNode.HTML_TAG, FormalListElementNode.CSS_CLASS );
    }

    public void monospace ( @NotNull MonospaceNode node ) throws Exception {

        helper.writeBlockNodeWithInlineChildren ( node, FormalMonospaceNode.HTML_TAG, FormalMonospaceNode.CSS_CLASS );
    }

    public void note ( @NotNull NoteNode node ) throws Exception {

        AdmonitionHTMLWriter.writeAdmonition ( node, node.getLabel(), this, helper );
    }

    public void options ( @NotNull OptionsNode node ) throws Exception {
        // do nothing
    }

    public void output ( @NotNull OutputNode node ) throws Exception {

        RawTextHTMLWriter.writeInputOutput ( node, FormalOutputNode.CSS_CLASS, helper );
    }

    public void paragraph ( @NotNull ParagraphNode node ) throws Exception {

        helper.writeBlockNodeWithInlineChildren ( node, FormalParagraphNode.HTML_TAG, FormalParagraphNode.CSS_CLASS );
    }

    public void quote ( @NotNull QuoteNode node ) throws Exception {

        QuoteHTMLWriter.writeQuote ( node, this, helper );
    }

    public void sourceCode ( @NotNull SourceCodeNode node ) throws Exception {

        RawTextHTMLWriter.writeSourceCode ( node, node.getLanguage(), node.getUseHighlighter(), helper );
    }

    public void table ( @NotNull TableNode node ) throws Exception {

        helper.writeBlockNodeWithBlockChildren ( node, FormalTableNode.HTML_TAG, FormalTableNode.CSS_CLASS );
    }

    public void tableCell ( @NotNull TableCellNode node ) throws Exception {

        String tag;
        String CSSClass;
        if ( writingTableHeader ) {
            tag = FormalTableCellNode.HTML_TAG_IN_HEADER;
            CSSClass = FormalTableCellNode.CSS_CLASS_IN_HEADER;
        } else if ( writingTableFooter ) {
            tag = FormalTableCellNode.HTML_TAG_IN_FOOTER;
            CSSClass = FormalTableCellNode.CSS_CLASS_IN_FOOTER;
        } else {
            tag = FormalTableCellNode.HTML_TAG;
            CSSClass = FormalTableCellNode.CSS_CLASS;
        }
        helper.writeBlockNodeWithBlockChildren ( node, tag, CSSClass );
    }

    public void tableData ( @NotNull TableDataNode node ) throws Exception {

        TableDataHTMLWriter.writeTableDataNode ( node, helper );
    }

    public void tableFooter ( @NotNull TableFooterNode node ) throws Exception {

        writingTableFooter = true;
        helper.writeBlockNodeWithBlockChildren ( node, FormalTableFooterNode.HTML_TAG, FormalTableFooterNode.CSS_CLASS );
        writingTableFooter = false;
    }

    public void tableHeader ( @NotNull TableHeaderNode node ) throws Exception {

        writingTableHeader = true;
        helper.writeBlockNodeWithBlockChildren ( node, FormalTableHeaderNode.HTML_TAG, FormalTableHeaderNode.CSS_CLASS );
        writingTableHeader = false;
    }

    public void tableRow ( @NotNull TableRowNode node ) throws Exception {

        String CSSClass;
        if ( writingTableHeader ) {
            CSSClass = FormalTableRowNode.CSS_CLASS_IN_HEADER;
        } else if ( writingTableFooter ) {
            CSSClass = FormalTableRowNode.CSS_CLASS_IN_FOOTER;
        } else {
            CSSClass = FormalTableRowNode.CSS_CLASS;
        }
        helper.writeBlockNodeWithBlockChildren ( node, FormalTableRowNode.HTML_TAG, CSSClass );
    }

    public void TOC ( @NotNull TOCNode TOCNode ) throws Exception {

        TOCHTMLWriter.writeTOCNode ( TOCNode, helper, options.TOCTitle() );
    }

    public void video ( @NotNull VideoNode node ) throws Exception {

        MediaHTMLWriter.writeVideo ( node, helper );
    }

    public void youtubeVideo ( @NotNull YoutubeVideoNode node ) throws Exception {

        MediaHTMLWriter.writeYoutubeVideo ( node, helper );
    }


    // Inline Nodes

    public void bold ( @NotNull BoldNode node ) throws Exception {

        helper.writeInlineNode ( node, FormalBoldNode.HTML_TAG, FormalBoldNode.CSS_CLASS );
    }

    public void footnoteReference ( @NotNull FootnoteReferenceNode node ) throws Exception {

        helper.writeHTMLStartTag ( FormalFootnoteReferenceNode.HTML_TAG, node.getId(),
            FormalFootnoteReferenceNode.CSS_CLASS, node.getHTMLAttributes(), null );

        helper.writeHTMLATag ( "#" + node.getDefinitionId() );
        // helper.write ( "[" ); // done in CSS
        String inlineText = node.getInlineText();
        if ( inlineText == null ) inlineText = String.valueOf ( node.getListIndex() );
        helper.write ( inlineText );
        // helper.write ( "]" ); // done in CSS
        helper.writeHTMLEndTag ( "a" );

        helper.writeHTMLEndTag ( FormalFootnoteReferenceNode.HTML_TAG );
    }

    public void inlineCode ( @NotNull InlineCodeNode node ) throws Exception {

        helper.writeHTMLStartTag ( node, FormalInlineCodeNode.HTML_TAG, FormalInlineCodeNode.CSS_CLASS, null );
        helper.escapeAndWriteNullableText ( node.getRawText() );
        helper.writeHTMLEndTag ( FormalInlineCodeNode.HTML_TAG );
    }

    public void inlineFootnote ( @NotNull InlineFootnoteNode node ) throws Exception {

        FootnoteReferenceNode reference = node.getReferenceNode ();
        assert reference != null;
        footnoteReference ( reference );
    }

    public void italic ( @NotNull ItalicNode node ) throws Exception {

        helper.writeInlineNode ( node, FormalItalicNode.HTML_TAG, FormalItalicNode.CSS_CLASS );
    }

    public void link ( @NotNull LinkNode node ) throws Exception {

        helper.writeHTMLStartTag ( node, FormalLinkNode.HTML_TAG, FormalLinkNode.CSS_CLASS,
            Map.of ( "href", node.getURL() ) );
        helper.escapeAndWriteText ( node.getTextOrElseURL() );
        helper.writeHTMLEndTag ( FormalLinkNode.HTML_TAG );
    }

    public void newLine ( @NotNull NewLineNode node ) throws Exception {

        helper.write ( "<br />" );
    }

    public void space ( @NotNull SpaceNode node ) throws Exception {

        helper.write ( "&nbsp;" );
    }

    public void span ( @NotNull SpanNode node ) throws Exception {

        helper.writeInlineNode ( node, FormalSpanNode.HTML_TAG, FormalSpanNode.CSS_CLASS );
    }

    public void strikethrough ( @NotNull StrikethroughNode node ) throws Exception {

        helper.writeInlineNode ( node, FormalStrikethroughNode.HTML_TAG, FormalStrikethroughNode.CSS_CLASS );
    }

    public void subscript ( @NotNull SubscriptNode node ) throws Exception {

        helper.writeInlineNode ( node, FormalSubscriptNode.HTML_TAG, FormalSubscriptNode.CSS_CLASS );
    }

    public void superscript ( @NotNull SuperscriptNode node ) throws Exception {

        helper.writeInlineNode ( node, FormalSuperscriptNode.HTML_TAG, FormalSuperscriptNode.CSS_CLASS );
    }

    public void text ( @NotNull TextNode node ) throws Exception {

        helper.escapeAndWriteText ( node.getText() );
    }

    public void verbatimText ( @NotNull VerbatimTextNode node ) throws Exception {

        helper.writeNullable ( node.getRawText() );
    }

    public void xref ( @NotNull XrefNode node ) throws Exception {

        String tag = FormalXrefNode.HTML_TAG;
        helper.writeHTMLStartTag ( node, tag, FormalXrefNode.CSS_CLASS,
            Map.of ( "href", "#" + node.getReferencedNodeId() ) );
        helper.escapeAndWriteText ( node.getText() );
        helper.writeHTMLEndTag ( tag );
    }
}
