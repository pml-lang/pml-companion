package dev.pmlc.utils.nodehandlers.impls;

import dev.pmlc.data.node.PMLNode;
import dev.pmlc.data.node.block.*;
import dev.pmlc.data.node.block.admonition.AdmonitionLabelNode;
import dev.pmlc.data.node.block.admonition.AdmonitionNode;
import dev.pmlc.data.node.block.admonition.NoteNode;
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
import dev.pmlc.data.node.block.quote.QuoteNode;
import dev.pmlc.data.node.block.quote.QuoteSourceNode;
import dev.pmlc.data.node.block.table.*;
import dev.pmlc.data.node.inline.*;
import dev.pmlc.data.node.inline.font.*;
import dev.pmlc.data.node.inline.footnote.FootnoteReferenceNode;
import dev.pmlc.data.node.inline.footnote.InlineFootnoteNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;

public class ThrowingNodesHandler implements PMLNodesHandler {


    public ThrowingNodesHandler () {}


    public @NotNull String getName () { return "throw_error"; }

    public void start() throws Exception {}

    public void end() throws Exception {}


    // Block Nodes

    public void admonition ( @NotNull AdmonitionNode node ) throws Exception { handleNode ( node ); }

    public void admonitionLabel ( @NotNull AdmonitionLabelNode node ) throws Exception { handleNode ( node ); }

    public void audio ( @NotNull AudioNode node ) throws Exception { handleNode ( node ); }

    public void caption ( @NotNull CaptionNode node ) throws Exception { handleNode ( node ); }

    public void chapter ( @NotNull ChapterNode node ) throws Exception { handleNode ( node ); }

    public void chapterSubtitle ( @NotNull SubtitleNode node ) throws Exception { handleNode ( node ); }

    public void chapterTitle ( @NotNull TitleNode node ) throws Exception { handleNode ( node ); }

    public void division ( @NotNull DivisionNode node ) throws Exception { handleNode ( node ); }

    public void document ( @NotNull DocumentNode node ) throws Exception { handleNode ( node ); }

    public void footnoteDefinition ( @NotNull FootnoteDefinitionNode node ) throws Exception { handleNode ( node ); }

    public void footnotes ( @NotNull FootnotesPlaceholderNode node ) throws Exception { handleNode ( node ); }

    public void header ( @NotNull HeaderNode node ) throws Exception { handleNode ( node ); }

    public void HTMLCode ( @NotNull HTMLCodeNode node ) throws Exception { handleNode ( node ); }

    public void image ( @NotNull ImageNode node ) throws Exception { handleNode ( node ); }

    public void input ( @NotNull InputNode node ) throws Exception { handleNode ( node ); }

    public void insertSourceCode ( @NotNull InsertSourceCodeNode node ) throws Exception { handleNode ( node ); }

    public void list ( @NotNull ListNode node ) throws Exception { handleNode ( node ); }

    public void listElement ( @NotNull ListElementNode node ) throws Exception { handleNode ( node ); }

    public void monospace ( @NotNull MonospaceNode node ) throws Exception { handleNode ( node ); }

    public void note ( @NotNull NoteNode node ) throws Exception { handleNode ( node ); }

    public void output ( @NotNull OutputNode node ) throws Exception { handleNode ( node ); }

    public void paragraph ( @NotNull ParagraphNode node ) throws Exception { handleNode ( node ); }

    public void quote ( @NotNull QuoteNode node ) throws Exception { handleNode ( node ); }

    public void quoteSource ( @NotNull QuoteSourceNode node ) throws Exception { handleNode ( node ); }

    public void sourceCode ( @NotNull SourceCodeNode node ) throws Exception { handleNode ( node ); }

    public void table ( @NotNull TableNode node ) throws Exception { handleNode ( node ); }

    public void tableCell ( @NotNull TableCellNode node ) throws Exception { handleNode ( node ); }

    public void tableData ( @NotNull SimpleTableNode node ) throws Exception { handleNode ( node ); }

    public void tableFooter ( @NotNull TableFooterNode node ) throws Exception { handleNode ( node ); }

    public void tableHeader ( @NotNull TableHeaderNode node ) throws Exception { handleNode ( node ); }

    public void tableRow ( @NotNull TableRowNode node ) throws Exception { handleNode ( node ); }

    public void TOC ( @NotNull TOCNode node ) throws Exception { handleNode ( node ); }

    public void video ( @NotNull VideoNode node ) throws Exception { handleNode ( node ); }

    public void youtubeVideo ( @NotNull YoutubeVideoNode node ) throws Exception { handleNode ( node ); }


    // Inline Nodes

    public void bold ( @NotNull BoldNode node ) throws Exception { handleNode ( node ); }

    public void footnoteReference ( @NotNull FootnoteReferenceNode node ) throws Exception { handleNode ( node ); }

    public void inlineCode ( @NotNull InlineCodeNode node ) throws Exception { handleNode ( node ); }

    public void inlineFootnote ( @NotNull InlineFootnoteNode node ) throws Exception { handleNode ( node ); }

    public void inlineUDN ( @NotNull InlineUDN node ) throws Exception { handleNode ( node ); }

    public void italic ( @NotNull ItalicNode node ) throws Exception { handleNode ( node ); }

    public void link ( @NotNull LinkNode node ) throws Exception { handleNode ( node ); }

    public void newLine ( @NotNull NewLineNode node ) throws Exception { handleNode ( node ); }

    public void space ( @NotNull SpaceNode node ) throws Exception { handleNode ( node ); }

    public void span ( @NotNull SpanNode node ) throws Exception { handleNode ( node ); }

    public void strikethrough ( @NotNull StrikethroughNode node ) throws Exception { handleNode ( node ); }

    public void subscript ( @NotNull SubscriptNode node ) throws Exception { handleNode ( node ); }

    public void superscript ( @NotNull SuperscriptNode node ) throws Exception { handleNode ( node ); }

    public void text ( @NotNull TextNode node ) throws Exception { handleNode ( node ); }

    public void verbatimText ( @NotNull VerbatimTextNode node ) throws Exception { handleNode ( node ); }

    public void xref ( @NotNull XrefNode node ) throws Exception { handleNode ( node ); }


    private void handleNode ( PMLNode node ) {

        throw new RuntimeException (
            "Handling node '" + node.getNodeSpec ().getName() + "' is not implemented yet" );
    }
}
