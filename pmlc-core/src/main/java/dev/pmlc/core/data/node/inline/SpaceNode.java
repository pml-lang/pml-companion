package dev.pmlc.core.data.node.inline;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.FormalSpaceNode;
import dev.pp.basics.annotations.NotNull;

public class SpaceNode extends PMLInlineNode {

    public SpaceNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, SpaceNode> getFormalNode() { return FormalSpaceNode.NODE; }
}
