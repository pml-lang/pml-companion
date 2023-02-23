package dev.pmlc.data.node.block.table;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.table.TableHeaderNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class TableHeaderNode extends PMLBlockNode {

    public TableHeaderNode () { super(); }

    public @NotNull PMLNodeSpec<Void, TableHeaderNode> getNodeSpec () { return TableHeaderNodeSpec.NODE; }
}
