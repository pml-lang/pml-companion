package dev.pmlc.data.node.block.admonition;

import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.admonition.AdmonitionLabelNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class AdmonitionLabelNode extends PMLBlockNode {


    public AdmonitionLabelNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, AdmonitionLabelNode> getNodeSpec() { return AdmonitionLabelNodeSpec.NODE; }
}
