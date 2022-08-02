package dev.pmlc.core.data.node.inline;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.FormalSpanNode;
import dev.pp.basics.annotations.NotNull;

public class SpanNode extends PMLInlineNode {

    public SpanNode() { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, SpanNode> getFormalNode() { return FormalSpanNode.NODE; }
}
