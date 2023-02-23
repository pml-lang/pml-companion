package dev.pmlc.data.node.block.table;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.table.TableRowNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class TableRowNode extends PMLBlockNode {

    public TableRowNode () { super(); }

    public @NotNull PMLNodeSpec<Void, TableRowNode> getNodeSpec () { return TableRowNodeSpec.NODE; }
}
