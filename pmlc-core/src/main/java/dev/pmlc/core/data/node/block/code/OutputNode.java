package dev.pmlc.core.data.node.block.code;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.code.FormalOutputNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class OutputNode extends PMLBlockNode {


    public OutputNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<String, OutputNode> getFormalNode() { return FormalOutputNode.NODE; }
}
