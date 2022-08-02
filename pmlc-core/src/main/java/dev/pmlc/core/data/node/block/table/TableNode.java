package dev.pmlc.core.data.node.block.table;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.table.FormalTableNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class TableNode extends PMLBlockNode {

    public TableNode () { super(); }

    public @NotNull FormalPMLNode<Void, TableNode> getFormalNode() { return FormalTableNode.NODE; }
}
