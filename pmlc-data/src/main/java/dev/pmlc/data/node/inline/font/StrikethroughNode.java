package dev.pmlc.data.node.inline.font;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.font.StrikethroughNodeSpec;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class StrikethroughNode extends PMLInlineNode {

    public StrikethroughNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, StrikethroughNode> getNodeSpec () { return StrikethroughNodeSpec.NODE; }
}
