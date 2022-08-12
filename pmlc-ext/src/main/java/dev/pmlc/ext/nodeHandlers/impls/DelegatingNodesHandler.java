package dev.pmlc.ext.nodeHandlers.impls;

import dev.pmlc.core.data.node.PMLNode;
import dev.pmlc.core.data.node.block.*;
import dev.pmlc.core.data.node.block.chapter.ChapterNode;
import dev.pmlc.core.data.node.block.chapter.SubtitleNode;
import dev.pmlc.core.data.node.block.chapter.TitleNode;
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
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;

public class DelegatingNodesHandler implements PMLNodesHandler {

    private final @NotNull NodesHandlerDelegate delegate;


    public DelegatingNodesHandler ( @NotNull NodesHandlerDelegate delegate ) {
        this.delegate = delegate;
    }


    public @NotNull String getName () { return delegate.getName (); }

    public void start() throws Exception { delegate.start(); }

    public void end() throws Exception { delegate.end(); }


    // Block Nodes

    public void admonition ( @NotNull AdmonitionNode node ) throws Exception { delegateNode ( node ); }

    public void audio ( @NotNull AudioNode node ) throws Exception { delegateNode ( node ); }

    public void caption ( @NotNull CaptionNode node ) throws Exception { delegateNode ( node ); }

    public void chapter ( @NotNull ChapterNode node ) throws Exception { delegateNode ( node ); }

    public void chapterSubtitle ( @NotNull SubtitleNode node ) throws Exception { delegateNode ( node ); }

    public void chapterTitle ( @NotNull TitleNode node ) throws Exception { delegateNode ( node ); }

    public void division ( @NotNull DivisionNode node ) throws Exception { delegateNode ( node ); }

    public void document ( @NotNull DocumentNode node ) throws Exception { delegateNode ( node ); }

    public void header ( @NotNull HeaderNode node ) throws Exception { delegateNode ( node ); }

    public void HTMLCode ( @NotNull HTMLCodeNode node ) throws Exception { delegateNode ( node ); }

    public void image ( @NotNull ImageNode node ) throws Exception { delegateNode ( node ); }

    public void input ( @NotNull InputNode node ) throws Exception { delegateNode ( node ); }

    public void insertSourceCode ( @NotNull InsertSourceCodeNode node ) throws Exception { delegateNode ( node ); }

    public void list ( @NotNull ListNode node ) throws Exception { delegateNode ( node ); }

    public void listElement ( @NotNull ListElementNode node ) throws Exception { delegateNode ( node ); }

    public void monospace ( @NotNull MonospaceNode node ) throws Exception { delegateNode ( node ); }

    public void note ( @NotNull NoteNode node ) throws Exception { delegateNode ( node ); }

    public void options ( @NotNull OptionsNode node ) throws Exception { delegateNode ( node ); }

    public void output ( @NotNull OutputNode node ) throws Exception { delegateNode ( node ); }

    public void paragraph ( @NotNull ParagraphNode node ) throws Exception { delegateNode ( node ); }

    public void quote ( @NotNull QuoteNode node ) throws Exception { delegateNode ( node ); }

    public void sourceCode ( @NotNull SourceCodeNode node ) throws Exception { delegateNode ( node ); }

    public void table ( @NotNull TableNode node ) throws Exception { delegateNode ( node ); }

    public void tableCell ( @NotNull TableCellNode node ) throws Exception { delegateNode ( node ); }

    public void tableData ( @NotNull TableDataNode node ) throws Exception { delegateNode ( node ); }

    public void tableFooter ( @NotNull TableFooterNode node ) throws Exception { delegateNode ( node ); }

    public void tableHeader ( @NotNull TableHeaderNode node ) throws Exception { delegateNode ( node ); }

    public void tableRow ( @NotNull TableRowNode node ) throws Exception { delegateNode ( node ); }

    public void TOC ( @NotNull TOCNode node ) throws Exception { delegateNode ( node ); }

    public void video ( @NotNull VideoNode node ) throws Exception { delegateNode ( node ); }

    public void youtubeVideo ( @NotNull YoutubeVideoNode node ) throws Exception { delegateNode ( node ); }


    // Inline Nodes

    public void bold ( @NotNull BoldNode node ) throws Exception { delegateNode ( node ); }

    public void inlineCode ( @NotNull InlineCodeNode node ) throws Exception { delegateNode ( node ); }

    public void italic ( @NotNull ItalicNode node ) throws Exception { delegateNode ( node ); }

    public void link ( @NotNull LinkNode node ) throws Exception { delegateNode ( node ); }

    public void newLine ( @NotNull NewLineNode node ) throws Exception { delegateNode ( node ); }

    public void space ( @NotNull SpaceNode node ) throws Exception { delegateNode ( node ); }

    public void span ( @NotNull SpanNode node ) throws Exception { delegateNode ( node ); }

    public void strikethrough ( @NotNull StrikethroughNode node ) throws Exception { delegateNode ( node ); }

    public void subscript ( @NotNull SubscriptNode node ) throws Exception { delegateNode ( node ); }

    public void superscript ( @NotNull SuperscriptNode node ) throws Exception { delegateNode ( node ); }

    public void text ( @NotNull TextNode node ) throws Exception { delegateNode ( node ); }

    public void verbatimText ( @NotNull VerbatimTextNode node ) throws Exception { delegateNode ( node ); }

    public void xref ( @NotNull XrefNode node ) throws Exception { delegateNode ( node ); }


    private void delegateNode ( PMLNode node ) throws Exception {
        delegate.handleNode ( node );
    }
}
