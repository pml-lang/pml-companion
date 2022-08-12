package dev.pmlc.core.data.node.block.chapter;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalChapterSubtitleNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class ChapterSubtitleNode extends PMLBlockNode {


    private int level = 1;
    public int getLevel() { return level; }
    public void setLevel ( int level ) { this.level = level; }


    public ChapterSubtitleNode () { super(); }


    public @NotNull FormalPMLNode<Void, ChapterSubtitleNode> getFormalNode() { return FormalChapterSubtitleNode.NODE; }
}
