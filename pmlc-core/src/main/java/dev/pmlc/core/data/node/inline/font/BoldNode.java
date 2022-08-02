package dev.pmlc.core.data.node.inline.font;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.font.FormalBoldNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class BoldNode extends PMLInlineNode {

    public BoldNode() { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, BoldNode> getFormalNode() { return FormalBoldNode.NODE; }
}
