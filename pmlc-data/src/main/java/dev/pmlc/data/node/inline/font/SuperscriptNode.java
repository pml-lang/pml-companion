package dev.pmlc.data.node.inline.font;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.font.SuperscriptNodeSpec;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class SuperscriptNode extends PMLInlineNode {

    public SuperscriptNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, SuperscriptNode> getNodeSpec () { return SuperscriptNodeSpec.NODE; }
}
