package dev.pmlc.data.node.block.code;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.code.InputNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class InputNode extends PMLBlockNode {


    public InputNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<String, InputNode> getNodeSpec () { return InputNodeSpec.NODE; }
}
