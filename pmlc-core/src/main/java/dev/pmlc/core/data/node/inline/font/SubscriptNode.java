package dev.pmlc.core.data.node.inline.font;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.font.FormalSubscriptNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class SubscriptNode extends PMLInlineNode {

    public SubscriptNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, SubscriptNode> getFormalNode() { return FormalSubscriptNode.NODE; }
}
