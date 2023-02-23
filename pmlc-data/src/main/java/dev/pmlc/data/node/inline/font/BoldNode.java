package dev.pmlc.data.node.inline.font;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.font.BoldNodeSpec;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class BoldNode extends PMLInlineNode {

    public BoldNode() { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, BoldNode> getNodeSpec () { return BoldNodeSpec.NODE; }
}
