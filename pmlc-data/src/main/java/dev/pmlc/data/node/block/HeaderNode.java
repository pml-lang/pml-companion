package dev.pmlc.data.node.block;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.HeaderNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class HeaderNode extends PMLBlockNode {

    /*
    private int level = 1;
    public int getLevel() { return level; }
    public void setLevel ( int level ) { this.level = level; }
    */


    public HeaderNode () { super(); }


    public @NotNull PMLNodeSpec<Void, HeaderNode> getNodeSpec () { return HeaderNodeSpec.NODE; }
}
