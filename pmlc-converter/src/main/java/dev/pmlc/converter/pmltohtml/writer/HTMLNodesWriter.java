package dev.pmlc.converter.pmltohtml.writer;

import dev.pmlc.data.node.block.admonition.AdmonitionLabelNode;
import dev.pmlc.data.node.block.admonition.AdmonitionNode;
import dev.pmlc.data.node.block.admonition.NoteNode;
import dev.pmlc.data.node.block.quote.QuoteNode;
import dev.pmlc.data.node.block.quote.QuoteSourceNode;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.block.*;
import dev.pmlc.data.nodespec.block.chapter.ChapterNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.SubtitleNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.TitleNodeSpec;
import dev.pmlc.data.nodespec.block.code.HtmlCodeNodeSpec;
import dev.pmlc.data.nodespec.block.code.InputNodeSpec;
import dev.pmlc.data.nodespec.block.code.OutputNodeSpec;
import dev.pmlc.data.nodespec.block.footnote.FootnoteDefinitionNodeSpec;
import dev.pmlc.data.nodespec.block.footnote.FootnotesPlaceholderNodeSpec;
import dev.pmlc.data.nodespec.block.list.ListElementNodeSpec;
import dev.pmlc.data.nodespec.block.list.ListNodeSpec;
import dev.pmlc.data.nodespec.block.table.*;
import dev.pmlc.data.nodespec.inline.LinkNodeSpec;
import dev.pmlc.data.nodespec.inline.SpanNodeSpec;
import dev.pmlc.data.nodespec.inline.XrefNodeSpec;
import dev.pmlc.data.nodespec.inline.font.*;
import dev.pmlc.data.nodespec.inline.footnote.FootnoteReferenceNodeSpec;
import dev.pmlc.data.node.block.*;
import dev.pmlc.data.node.block.chapter.ChapterNode;
import dev.pmlc.data.node.block.chapter.SubtitleNode;
import dev.pmlc.data.node.block.chapter.TitleNode;
import dev.pmlc.data.node.block.code.*;
import dev.pmlc.data.node.block.footnote.FootnoteDefinitionNode;
import dev.pmlc.data.node.block.footnote.FootnotesPlaceholderNode;
import dev.pmlc.data.node.block.list.ListElementNode;
import dev.pmlc.data.node.block.list.ListNode;
import dev.pmlc.data.node.block.media.AudioNode;
import dev.pmlc.data.node.block.media.ImageNode;
import dev.pmlc.data.node.block.media.VideoNode;
import dev.pmlc.data.node.block.media.YoutubeVideoNode;
import dev.pmlc.data.node.block.table.*;
import dev.pmlc.data.node.inline.*;
import dev.pmlc.data.node.inline.font.*;
import dev.pmlc.data.node.inline.footnote.FootnoteReferenceNode;
import dev.pmlc.data.node.inline.footnote.InlineFootnoteNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pmlc.converter.pmltohtml.options.PMLToHTMLOptions;
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
        AdmonitionHTMLWriter.writeAdmonition ( node, node.getLabelNode(), this, helper );
    }

    public void admonitionLabel ( @NotNull AdmonitionLabelNode node ) throws Exception {
        // done already in 'admonition'
        AdmonitionHTMLWriter.writeAdmonitionLabel ( node, helper );
    }


    public void audio ( @NotNull AudioNode node ) throws Exception {
        MediaHTMLWriter.writeAudio ( node, helper );
    }

    public void caption ( @NotNull CaptionNode node ) throws Exception {
        helper.writeBlockNodeWithInlineChildren ( node, CaptionNodeSpec.HTML_TAG, CaptionNodeSpec.CSS_CLASS );
    }

    public void chapter ( @NotNull ChapterNode node ) throws Exception {
        helper.writeBlockNodeWithBlockChildren ( node, ChapterNodeSpec.HTML_TAG, ChapterNodeSpec.CSS_CLASS );
    }

    public void chapterSubtitle ( @NotNull SubtitleNode node ) throws Exception {
        writeChapterTitleNode ( node, node.getLevel(), SubtitleNodeSpec.CSS_CLASS );
    }

    public void chapterTitle ( @NotNull TitleNode node ) throws Exception {

        writeChapterTitleNode ( node, node.getLevel(), TitleNodeSpec.CSS_CLASS );

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
                ? PMLNodeSpecCreator.prefixedHTMLClassName ( "doc-title" )
                : CSSClass;

        helper.writeBlockNodeWithInlineChildren ( node, tag, CSSClass_ );
    }

    public void division ( @NotNull DivisionNode node ) throws Exception {
        helper.writeBlockNodeWithBlockChildren ( node, DivisionNodeSpec.HTML_TAG, DivisionNodeSpec.CSS_CLASS );
    }

    public void document ( @NotNull DocumentNode node ) throws Exception {

        String tag = DocumentNodeSpec.HTML_TAG;
        helper.writeBlockNodeStartLine ( node, tag, DocumentNodeSpec.CSS_CLASS, null, true );
        handleChildNodes ( node.getBlockChildNodes() );
        helper.decreaseIndent();
        helper.writeBlockEndLine ( tag );
    }

    public void footnoteDefinition ( @NotNull FootnoteDefinitionNode node ) throws Exception {
        // definitions are rendered in footnotes (node [fnotes])
    }

    private void footnoteDefinition_ ( @NotNull FootnoteDefinitionNode node ) throws Exception {

        helper.writeHTMLBlockStartTag ( FootnoteDefinitionNodeSpec.HTML_TAG, node.getNodeId(),
            FootnoteDefinitionNodeSpec.CSS_CLASS, node.getHTMLAttributes(), null );
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
        helper.writeHTMLStartTag ( "td", null, PMLNodeSpecCreator.prefixedHTMLClassName ( "footnote-backlink" ),
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
        helper.writeBlockEndLine ( FootnoteDefinitionNodeSpec.HTML_TAG );
    }

    public void footnotes ( @NotNull FootnotesPlaceholderNode node ) throws Exception {

        helper.writeBlockStartLine ( FootnotesPlaceholderNodeSpec.HTML_TAG, FootnotesPlaceholderNodeSpec.CSS_CLASS );
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
        helper.writeBlockEndLine ( FootnotesPlaceholderNodeSpec.HTML_TAG );
    }

    public void header ( @NotNull HeaderNode node ) throws Exception {
        helper.writeBlockNodeWithInlineChildren ( node, HeaderNodeSpec.HTML_TAG, HeaderNodeSpec.CSS_CLASS );
    }

    public void HTMLCode ( @NotNull HTMLCodeNode node ) throws Exception {

        helper.writeIndent();
        String tag = HtmlCodeNodeSpec.HTML_TAG;
        helper.writeHTMLStartTag ( tag, node.getNodeId(), HtmlCodeNodeSpec.CSS_CLASS, node.getHTMLAttributes(), null );
        @Nullable String code = node.getRawText();
        if ( code != null ) helper.write ( code );
        helper.writeHTMLEndTag ( tag );
        helper.writeNewLine();
    }

    public void image ( @NotNull ImageNode node ) throws Exception {
        MediaHTMLWriter.writeImage ( node, helper );
    }

    public void input ( @NotNull InputNode node ) throws Exception {
        RawTextHTMLWriter.writeInputOutput ( node, InputNodeSpec.CSS_CLASS, helper );
    }

    public void insertSourceCode ( @NotNull InsertSourceCodeNode node ) throws Exception {
        RawTextHTMLWriter.writeSourceCode ( node, node.getLanguage(), node.getUseHighlighter(), helper );
    }

    public void list ( @NotNull ListNode node ) throws Exception {
        helper.writeBlockNodeWithBlockChildren ( node, ListNodeSpec.HTML_TAG, ListNodeSpec.CSS_CLASS );
    }

    public void listElement ( @NotNull ListElementNode node ) throws Exception {
        helper.writeBlockNodeWithBlockChildren ( node, ListElementNodeSpec.HTML_TAG, ListElementNodeSpec.CSS_CLASS );
    }

    public void monospace ( @NotNull MonospaceNode node ) throws Exception {
        helper.writeBlockNodeWithInlineChildren ( node, MonospaceNodeSpec.HTML_TAG, MonospaceNodeSpec.CSS_CLASS );
    }

    public void note ( @NotNull NoteNode node ) throws Exception {
        AdmonitionHTMLWriter.writeAdmonition ( node, node.getLabelNode(), this, helper );
    }

    public void output ( @NotNull OutputNode node ) throws Exception {
        RawTextHTMLWriter.writeInputOutput ( node, OutputNodeSpec.CSS_CLASS, helper );
    }

    public void paragraph ( @NotNull ParagraphNode node ) throws Exception {
        helper.writeBlockNodeWithInlineChildren ( node, ParagraphNodeSpec.HTML_TAG, ParagraphNodeSpec.CSS_CLASS );
    }

    public void quote ( @NotNull QuoteNode node ) throws Exception {
        QuoteHTMLWriter.writeQuote ( node, this, helper );
    }

    public void quoteSource ( @NotNull QuoteSourceNode node ) throws Exception {
        QuoteHTMLWriter.writeQuoteSource ( node, this, helper );
    }

    public void sourceCode ( @NotNull SourceCodeNode node ) throws Exception {
        RawTextHTMLWriter.writeSourceCode ( node, node.getLanguage(), node.getUseHighlighter(), helper );
    }

    public void table ( @NotNull TableNode node ) throws Exception {
        helper.writeBlockNodeWithBlockChildren ( node, TableNodeSpec.HTML_TAG, TableNodeSpec.CSS_CLASS );
    }

    public void tableCell ( @NotNull TableCellNode node ) throws Exception {

        String tag;
        String CSSClass;
        if ( writingTableHeader ) {
            tag = TableCellNodeSpec.HTML_TAG_IN_HEADER;
            CSSClass = TableCellNodeSpec.CSS_CLASS_IN_HEADER;
        } else if ( writingTableFooter ) {
            tag = TableCellNodeSpec.HTML_TAG_IN_FOOTER;
            CSSClass = TableCellNodeSpec.CSS_CLASS_IN_FOOTER;
        } else {
            tag = TableCellNodeSpec.HTML_TAG;
            CSSClass = TableCellNodeSpec.CSS_CLASS;
        }
        helper.writeBlockNodeWithBlockChildren ( node, tag, CSSClass );
    }

    public void tableData ( @NotNull SimpleTableNode node ) throws Exception {
        TableDataHTMLWriter.writeTableDataNode ( node, helper );
    }

    public void tableFooter ( @NotNull TableFooterNode node ) throws Exception {

        writingTableFooter = true;
        helper.writeBlockNodeWithBlockChildren ( node, TableFooterNodeSpec.HTML_TAG, TableFooterNodeSpec.CSS_CLASS );
        writingTableFooter = false;
    }

    public void tableHeader ( @NotNull TableHeaderNode node ) throws Exception {

        writingTableHeader = true;
        helper.writeBlockNodeWithBlockChildren ( node, TableHeaderNodeSpec.HTML_TAG, TableHeaderNodeSpec.CSS_CLASS );
        writingTableHeader = false;
    }

    public void tableRow ( @NotNull TableRowNode node ) throws Exception {

        String CSSClass;
        if ( writingTableHeader ) {
            CSSClass = TableRowNodeSpec.CSS_CLASS_IN_HEADER;
        } else if ( writingTableFooter ) {
            CSSClass = TableRowNodeSpec.CSS_CLASS_IN_FOOTER;
        } else {
            CSSClass = TableRowNodeSpec.CSS_CLASS;
        }
        helper.writeBlockNodeWithBlockChildren ( node, TableRowNodeSpec.HTML_TAG, CSSClass );
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
        helper.writeInlineNode ( node, BoldNodeSpec.HTML_TAG, BoldNodeSpec.CSS_CLASS );
    }

    public void footnoteReference ( @NotNull FootnoteReferenceNode node ) throws Exception {

        helper.writeHTMLStartTag ( FootnoteReferenceNodeSpec.HTML_TAG, node.getId(),
            FootnoteReferenceNodeSpec.CSS_CLASS, node.getHTMLAttributes(), null );

        helper.writeHTMLATag ( "#" + node.getDefinitionId() );
        // helper.write ( "[" ); // done in CSS
        String inlineText = node.getInlineText();
        if ( inlineText == null ) inlineText = String.valueOf ( node.getListIndex() );
        helper.write ( inlineText );
        // helper.write ( "]" ); // done in CSS
        helper.writeHTMLEndTag ( "a" );

        helper.writeHTMLEndTag ( FootnoteReferenceNodeSpec.HTML_TAG );
    }

    public void inlineCode ( @NotNull InlineCodeNode node ) throws Exception {

        helper.writeHTMLStartTag ( node, InlineCodeNodeSpec.HTML_TAG, InlineCodeNodeSpec.CSS_CLASS, null );
        helper.escapeAndWriteNullableText ( node.getRawText() );
        helper.writeHTMLEndTag ( InlineCodeNodeSpec.HTML_TAG );
    }

    public void inlineFootnote ( @NotNull InlineFootnoteNode node ) throws Exception {

        FootnoteReferenceNode reference = node.getReferenceNode ();
        assert reference != null;
        footnoteReference ( reference );
    }

    public void italic ( @NotNull ItalicNode node ) throws Exception {
        helper.writeInlineNode ( node, ItalicNodeSpec.HTML_TAG, ItalicNodeSpec.CSS_CLASS );
    }

    public void link ( @NotNull LinkNode node ) throws Exception {

        helper.writeHTMLStartTag ( node, LinkNodeSpec.HTML_TAG, LinkNodeSpec.CSS_CLASS,
            Map.of ( "href", node.getURL() ) );
        handleChildNodes ( node.getInlineChildNodes() );
        helper.writeHTMLEndTag ( LinkNodeSpec.HTML_TAG );
    }

    public void newLine ( @NotNull NewLineNode node ) throws Exception {
        helper.write ( "<br />" );
    }

    public void space ( @NotNull SpaceNode node ) throws Exception {
        helper.write ( "&nbsp;" );
    }

    public void span ( @NotNull SpanNode node ) throws Exception {
        helper.writeInlineNode ( node, SpanNodeSpec.HTML_TAG, SpanNodeSpec.CSS_CLASS );
    }

    public void strikethrough ( @NotNull StrikethroughNode node ) throws Exception {
        helper.writeInlineNode ( node, StrikethroughNodeSpec.HTML_TAG, StrikethroughNodeSpec.CSS_CLASS );
    }

    public void subscript ( @NotNull SubscriptNode node ) throws Exception {
        helper.writeInlineNode ( node, SubscriptNodeSpec.HTML_TAG, SubscriptNodeSpec.CSS_CLASS );
    }

    public void superscript ( @NotNull SuperscriptNode node ) throws Exception {
        helper.writeInlineNode ( node, SuperscriptNodeSpec.HTML_TAG, SuperscriptNodeSpec.CSS_CLASS );
    }

    public void text ( @NotNull TextNode node ) throws Exception {
        helper.escapeAndWriteText ( node.getText() );
    }

    public void verbatimText ( @NotNull VerbatimTextNode node ) throws Exception {
        helper.writeNullable ( node.getRawText() );
    }

    public void xref ( @NotNull XrefNode node ) throws Exception {

        String tag = XrefNodeSpec.HTML_TAG;
        helper.writeHTMLStartTag ( node, tag, XrefNodeSpec.CSS_CLASS,
            Map.of ( "href", "#" + node.getReferencedNodeId() ) );
//        helper.escapeAndWriteText ( node.getText() );
        handleChildNodes ( node.getInlineChildNodes() );
        helper.writeHTMLEndTag ( tag );
    }
}
