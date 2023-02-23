package dev.pmlc.data.node.block.code;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.code.HtmlCodeNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class HTMLCodeNode extends PMLBlockNode {


    public HTMLCodeNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<String, HTMLCodeNode> getNodeSpec () { return HtmlCodeNodeSpec.NODE; }
}
