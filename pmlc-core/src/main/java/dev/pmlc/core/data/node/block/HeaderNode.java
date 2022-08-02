package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.FormalHeaderNode;
import dev.pp.basics.annotations.NotNull;

public class HeaderNode extends PMLBlockNode {

    /*
    private int level = 1;
    public int getLevel() { return level; }
    public void setLevel ( int level ) { this.level = level; }
    */


    public HeaderNode () { super(); }


    public @NotNull FormalPMLNode<Void, HeaderNode> getFormalNode() { return FormalHeaderNode.NODE; }
}
