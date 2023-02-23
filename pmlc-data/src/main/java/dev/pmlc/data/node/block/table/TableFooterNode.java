package dev.pmlc.data.node.block.table;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.table.TableFooterNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class TableFooterNode extends PMLBlockNode {

    public TableFooterNode () { super(); }

    public @NotNull PMLNodeSpec<Void, TableFooterNode> getNodeSpec () { return TableFooterNodeSpec.NODE; }
}
