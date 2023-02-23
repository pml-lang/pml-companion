package dev.pmlc.data.node.inline.font;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.font.SubscriptNodeSpec;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class SubscriptNode extends PMLInlineNode {

    public SubscriptNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, SubscriptNode> getNodeSpec () { return SubscriptNodeSpec.NODE; }
}
