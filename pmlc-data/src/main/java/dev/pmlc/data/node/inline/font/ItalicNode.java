package dev.pmlc.data.node.inline.font;

import dev.pmlc.data.nodespec.inline.font.ItalicNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class ItalicNode extends PMLInlineNode {

    public ItalicNode() { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, ItalicNode> getNodeSpec () { return ItalicNodeSpec.NODE; }
}
