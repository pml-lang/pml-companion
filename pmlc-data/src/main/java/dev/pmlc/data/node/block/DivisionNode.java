package dev.pmlc.data.node.block;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.DivisionNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class DivisionNode extends PMLBlockNode {

    public DivisionNode () { super(); }

    public @NotNull PMLNodeSpec<Void, DivisionNode> getNodeSpec () { return DivisionNodeSpec.NODE; }
}
