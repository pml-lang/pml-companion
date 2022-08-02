package dev.pmlc.core.data.node.inline.font;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.font.FormalSuperscriptNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class SuperscriptNode extends PMLInlineNode {

    public SuperscriptNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, SuperscriptNode> getFormalNode() { return FormalSuperscriptNode.NODE; }
}
