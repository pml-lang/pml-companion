package dev.pmlc.data.node.block.chapter;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.SubtitleNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class SubtitleNode extends PMLBlockNode {


    private int level = 1;
    public int getLevel() { return level; }
    public void setLevel ( int level ) { this.level = level; }


    public SubtitleNode () { super(); }


    public @NotNull PMLNodeSpec<Void, SubtitleNode> getNodeSpec () { return SubtitleNodeSpec.NODE; }
}
