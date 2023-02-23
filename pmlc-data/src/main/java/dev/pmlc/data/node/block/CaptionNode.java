package dev.pmlc.data.node.block;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.CaptionNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class CaptionNode extends PMLBlockNode {

    public CaptionNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, CaptionNode> getNodeSpec () { return CaptionNodeSpec.NODE; }
}
