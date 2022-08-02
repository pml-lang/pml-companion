package dev.pmlc.core.data.node.block.table;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.table.FormalTableHeaderNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class TableHeaderNode extends PMLBlockNode {

    public TableHeaderNode () { super(); }

    public @NotNull FormalPMLNode<Void, TableHeaderNode> getFormalNode() { return FormalTableHeaderNode.NODE; }
}
