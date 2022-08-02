package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.FormalCaptionNode;
import dev.pp.basics.annotations.NotNull;

public class CaptionNode extends PMLBlockNode {

    public CaptionNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, CaptionNode> getFormalNode() { return FormalCaptionNode.NODE; }
}
