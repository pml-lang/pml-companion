package dev.pmlc.data.node.inline;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.SpanNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class SpanNode extends PMLInlineNode {

    public SpanNode() { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, SpanNode> getNodeSpec () { return SpanNodeSpec.NODE; }
}
