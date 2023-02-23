package dev.pmlc.data.node.inline;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.SpaceNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class SpaceNode extends PMLInlineNode {

    public SpaceNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, SpaceNode> getNodeSpec () { return SpaceNodeSpec.NODE; }
}
