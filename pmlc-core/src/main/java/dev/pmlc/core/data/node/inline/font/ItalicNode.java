package dev.pmlc.core.data.node.inline.font;

import dev.pmlc.core.data.formalnode.inline.font.FormalItalicNode;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class ItalicNode extends PMLInlineNode {

    public ItalicNode() { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, ItalicNode> getFormalNode() { return FormalItalicNode.NODE; }
}
