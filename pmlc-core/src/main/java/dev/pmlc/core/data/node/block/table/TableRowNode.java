package dev.pmlc.core.data.node.block.table;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.table.FormalTableRowNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class TableRowNode extends PMLBlockNode {

    public TableRowNode () { super(); }

    public @NotNull FormalPMLNode<Void, TableRowNode> getFormalNode() { return FormalTableRowNode.NODE; }
}
