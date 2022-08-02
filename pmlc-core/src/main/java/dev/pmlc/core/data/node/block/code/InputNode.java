package dev.pmlc.core.data.node.block.code;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.code.FormalInputNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class InputNode extends PMLBlockNode {


    public InputNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<String, InputNode> getFormalNode() { return FormalInputNode.NODE; }
}
