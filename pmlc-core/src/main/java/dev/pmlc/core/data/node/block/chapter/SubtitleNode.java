package dev.pmlc.core.data.node.block.chapter;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalSubtitleNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class SubtitleNode extends PMLBlockNode {


    private int level = 1;
    public int getLevel() { return level; }
    public void setLevel ( int level ) { this.level = level; }


    public SubtitleNode () { super(); }


    public @NotNull FormalPMLNode<Void, SubtitleNode> getFormalNode() { return FormalSubtitleNode.NODE; }
}
