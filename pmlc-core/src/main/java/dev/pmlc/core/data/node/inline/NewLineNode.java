package dev.pmlc.core.data.node.inline;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.FormalNewLineNode;
import dev.pp.basics.annotations.NotNull;

public class NewLineNode extends PMLInlineNode {

    public NewLineNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, NewLineNode> getFormalNode() { return FormalNewLineNode.NODE; }
}
