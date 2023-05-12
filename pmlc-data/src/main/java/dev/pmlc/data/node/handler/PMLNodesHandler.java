package dev.pmlc.data.node.handler;

import dev.pmlc.data.node.block.admonition.AdmonitionLabelNode;
import dev.pmlc.data.node.block.admonition.AdmonitionNode;
import dev.pmlc.data.node.block.admonition.NoteNode;
import dev.pmlc.data.node.block.quote.QuoteNode;
import dev.pmlc.data.node.block.quote.QuoteSourceNode;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.node.PMLNode;
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
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.List;

public interface PMLNodesHandler {


    @NotNull String getName();


    // Start/end

    void start() throws Exception;

    void end() throws Exception;


    // Block Nodes

    void admonition ( @NotNull AdmonitionNode node ) throws Exception;

    void admonitionLabel ( @NotNull AdmonitionLabelNode node ) throws Exception;

    void audio ( @NotNull AudioNode node ) throws Exception;

    void caption ( @NotNull CaptionNode node ) throws Exception;

    void chapter ( @NotNull ChapterNode node ) throws Exception;

    void chapterSubtitle ( @NotNull SubtitleNode node ) throws Exception;

    void chapterTitle ( @NotNull TitleNode node ) throws Exception;

    void division ( @NotNull DivisionNode node ) throws Exception;

    void document ( @NotNull DocumentNode node ) throws Exception;

    void footnoteDefinition ( @NotNull FootnoteDefinitionNode node ) throws Exception;

    void footnotes ( @NotNull FootnotesPlaceholderNode node ) throws Exception;

    void header ( @NotNull HeaderNode node ) throws Exception;

    void HTMLCode ( @NotNull HTMLCodeNode node ) throws Exception;

    void image ( @NotNull ImageNode node ) throws Exception;

    void input ( @NotNull InputNode node ) throws Exception;

    void insertSourceCode ( @NotNull InsertSourceCodeNode node ) throws Exception;

    void list ( @NotNull ListNode node ) throws Exception;

    void listElement ( @NotNull ListElementNode node ) throws Exception;

    void monospace ( @NotNull MonospaceNode node ) throws Exception;

    void note ( @NotNull NoteNode node ) throws Exception;

    void output ( @NotNull OutputNode node ) throws Exception;

    void paragraph ( @NotNull ParagraphNode node ) throws Exception;

    void quote ( @NotNull QuoteNode node ) throws Exception;

    void quoteSource ( @NotNull QuoteSourceNode node ) throws Exception;

    void sourceCode ( @NotNull SourceCodeNode node ) throws Exception;

    void styledSourceCode ( @NotNull StyledSourceCodeNode node ) throws Exception;

    void table ( @NotNull TableNode node ) throws Exception;

    void tableCell ( @NotNull TableCellNode node ) throws Exception;

    void tableData ( @NotNull SimpleTableNode node ) throws Exception;

    void tableFooter ( @NotNull TableFooterNode node ) throws Exception;

    void tableHeader ( @NotNull TableHeaderNode node ) throws Exception;

    void tableRow ( @NotNull TableRowNode node ) throws Exception;

    void TOC ( @NotNull TOCNode node ) throws Exception;

    void video ( @NotNull VideoNode node ) throws Exception;

    void youtubeVideo ( @NotNull YoutubeVideoNode node ) throws Exception;


    // Inline Nodes

    void bold ( @NotNull BoldNode node ) throws Exception;

    void footnoteReference ( @NotNull FootnoteReferenceNode node ) throws Exception;

    void inlineCode ( @NotNull InlineCodeNode node ) throws Exception;

    void inlineFootnote ( @NotNull InlineFootnoteNode node ) throws Exception;

    void inlineUDN ( @NotNull InlineUDN node ) throws Exception;

    void italic ( @NotNull ItalicNode node ) throws Exception;

    void link ( @NotNull LinkNode node ) throws Exception;

    void newLine ( @NotNull NewLineNode node ) throws Exception;

    void space ( @NotNull SpaceNode node ) throws Exception;

    void span ( @NotNull SpanNode node ) throws Exception;

    void strikethrough ( @NotNull StrikethroughNode node ) throws Exception;

    void subscript ( @NotNull SubscriptNode node ) throws Exception;

    void superscript ( @NotNull SuperscriptNode node ) throws Exception;

    void text ( @NotNull TextNode node ) throws Exception;

    void verbatimText ( @NotNull VerbatimTextNode node ) throws Exception;

    void xref ( @NotNull XrefNode node ) throws Exception;


    // Helpers

    default void handleChildNodes ( @Nullable List<? extends PMLNode> childNodes ) throws Exception {

        if ( childNodes == null ) return;

        for ( PMLNode childNode : childNodes ) {
            handleChildNode ( childNode );
        }
    }

    default <N extends PMLNode> void handleChildNode ( @NotNull N childNode ) throws Exception {

        PMLNodeSpec<?, N> nodeSpec = childNode.getNodeSpec();
        nodeSpec.handleNode ( this, childNode );
    }
}
