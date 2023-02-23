package dev.pmlc.data.node.block.code;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.code.OutputNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class OutputNode extends PMLBlockNode {


    public OutputNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<String, OutputNode> getNodeSpec () { return OutputNodeSpec.NODE; }
}
