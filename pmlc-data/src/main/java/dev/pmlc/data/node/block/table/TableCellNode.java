package dev.pmlc.data.node.block.table;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.table.TableCellNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class TableCellNode extends PMLBlockNode {

    public TableCellNode () { super(); }

    public @NotNull PMLNodeSpec<Void, TableCellNode> getNodeSpec () { return TableCellNodeSpec.NODE; }
}
