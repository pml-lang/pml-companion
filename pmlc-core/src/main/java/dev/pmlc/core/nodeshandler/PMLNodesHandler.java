package dev.pmlc.core.nodeshandler;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.node.PMLNode;
import dev.pmlc.core.data.node.block.*;
import dev.pmlc.core.data.node.block.chapter.ChapterNode;
import dev.pmlc.core.data.node.block.chapter.ChapterSubtitleNode;
import dev.pmlc.core.data.node.block.chapter.ChapterTitleNode;
import dev.pmlc.core.data.node.block.code.*;
import dev.pmlc.core.data.node.block.list.ListElementNode;
import dev.pmlc.core.data.node.block.list.ListNode;
import dev.pmlc.core.data.node.block.media.AudioNode;
import dev.pmlc.core.data.node.block.media.ImageNode;
import dev.pmlc.core.data.node.block.media.VideoNode;
import dev.pmlc.core.data.node.block.media.YoutubeVideoNode;
import dev.pmlc.core.data.node.block.table.*;
import dev.pmlc.core.data.node.inline.*;
import dev.pmlc.core.data.node.inline.font.*;
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

    void audio ( @NotNull AudioNode node ) throws Exception;

    void caption ( @NotNull CaptionNode node ) throws Exception;

    void chapter ( @NotNull ChapterNode node ) throws Exception;

    void chapterSubtitle ( @NotNull ChapterSubtitleNode node ) throws Exception;

    void chapterTitle ( @NotNull ChapterTitleNode node ) throws Exception;

    void division ( @NotNull DivisionNode node ) throws Exception;

    void document ( @NotNull DocumentNode node ) throws Exception;

    void header ( @NotNull HeaderNode node ) throws Exception;

    void HTMLCode ( @NotNull HTMLCodeNode node ) throws Exception;

    void image ( @NotNull ImageNode node ) throws Exception;

    void input ( @NotNull InputNode node ) throws Exception;

    void insertSourceCode ( @NotNull InsertSourceCodeNode node ) throws Exception;

    void list ( @NotNull ListNode node ) throws Exception;

    void listElement ( @NotNull ListElementNode node ) throws Exception;

    void monospace ( @NotNull MonospaceNode node ) throws Exception;

    void note ( @NotNull NoteNode node ) throws Exception;

    void options ( @NotNull OptionsNode node ) throws Exception;

    void output ( @NotNull OutputNode node ) throws Exception;

    void paragraph ( @NotNull ParagraphNode node ) throws Exception;

    void quote ( @NotNull QuoteNode node ) throws Exception;

    void sourceCode ( @NotNull SourceCodeNode node ) throws Exception;

    void table ( @NotNull TableNode node ) throws Exception;

    void tableCell ( @NotNull TableCellNode node ) throws Exception;

    void tableData ( @NotNull TableDataNode node ) throws Exception;

    void tableFooter ( @NotNull TableFooterNode node ) throws Exception;

    void tableHeader ( @NotNull TableHeaderNode node ) throws Exception;

    void tableRow ( @NotNull TableRowNode node ) throws Exception;

    void TOC ( @NotNull TOCNode node ) throws Exception;

    void video ( @NotNull VideoNode node ) throws Exception;

    void youtubeVideo ( @NotNull YoutubeVideoNode node ) throws Exception;


    // Inline Nodes

    void bold ( @NotNull BoldNode node ) throws Exception;

    void inlineCode ( @NotNull InlineCodeNode node ) throws Exception;

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

        FormalPMLNode<?, N> formalNode = childNode.getFormalNode();
        formalNode.handleNode ( this, childNode );
    }
}