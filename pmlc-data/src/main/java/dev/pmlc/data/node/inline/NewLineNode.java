package dev.pmlc.data.node.inline;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.NewLineNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class NewLineNode extends PMLInlineNode {

    public NewLineNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, NewLineNode> getNodeSpec () { return NewLineNodeSpec.NODE; }
}
