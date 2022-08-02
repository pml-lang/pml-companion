package dev.pmlc.core.data.node.inline.font;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.font.FormalStrikethroughNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class StrikethroughNode extends PMLInlineNode {

    public StrikethroughNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, StrikethroughNode> getFormalNode() { return FormalStrikethroughNode.NODE; }
}
