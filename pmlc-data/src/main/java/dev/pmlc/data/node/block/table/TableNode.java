package dev.pmlc.data.node.block.table;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.table.TableNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class TableNode extends PMLBlockNode {

    public TableNode () { super(); }

    public @NotNull PMLNodeSpec<Void, TableNode> getNodeSpec () { return TableNodeSpec.NODE; }
}
