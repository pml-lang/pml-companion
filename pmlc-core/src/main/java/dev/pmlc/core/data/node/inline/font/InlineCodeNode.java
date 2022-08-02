package dev.pmlc.core.data.node.inline.font;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.font.FormalInlineCodeNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class InlineCodeNode extends PMLInlineNode {

    public InlineCodeNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<String, InlineCodeNode> getFormalNode() { return FormalInlineCodeNode.NODE; }
}
