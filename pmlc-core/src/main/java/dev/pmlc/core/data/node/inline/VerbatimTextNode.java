package dev.pmlc.core.data.node.inline;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.FormalVerbatimTextNode;
import dev.pp.basics.annotations.NotNull;

public class VerbatimTextNode extends PMLInlineNode {

    public VerbatimTextNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<String, VerbatimTextNode> getFormalNode() { return FormalVerbatimTextNode.NODE; }
}
