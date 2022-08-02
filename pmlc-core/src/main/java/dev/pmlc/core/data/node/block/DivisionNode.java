package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.FormalDivisionNode;
import dev.pp.basics.annotations.NotNull;

public class DivisionNode extends PMLBlockNode {

    public DivisionNode () { super(); }

    public @NotNull FormalPMLNode<Void, DivisionNode> getFormalNode() { return FormalDivisionNode.NODE; }
}
