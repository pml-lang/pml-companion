package dev.pmlc.core.data.node.block.table;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.table.FormalTableFooterNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class TableFooterNode extends PMLBlockNode {

    public TableFooterNode () { super(); }

    public @NotNull FormalPMLNode<Void, TableFooterNode> getFormalNode() { return FormalTableFooterNode.NODE; }
}
