package dev.pmlc.core.data.node.block.code;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.code.FormalHTMLCodeNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class HTMLCodeNode extends PMLBlockNode {


    public HTMLCodeNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<String, HTMLCodeNode> getFormalNode() { return FormalHTMLCodeNode.NODE; }
}
