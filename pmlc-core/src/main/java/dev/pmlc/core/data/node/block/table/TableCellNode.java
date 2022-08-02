package dev.pmlc.core.data.node.block.table;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.table.FormalTableCellNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class TableCellNode extends PMLBlockNode {

    public TableCellNode () { super(); }

    public @NotNull FormalPMLNode<Void, TableCellNode> getFormalNode() { return FormalTableCellNode.NODE; }
}
