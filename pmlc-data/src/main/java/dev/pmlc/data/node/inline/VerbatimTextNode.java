package dev.pmlc.data.node.inline;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.VerbatimTextNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class VerbatimTextNode extends PMLInlineNode {

    public VerbatimTextNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<String, VerbatimTextNode> getNodeSpec () { return VerbatimTextNodeSpec.NODE; }
}
