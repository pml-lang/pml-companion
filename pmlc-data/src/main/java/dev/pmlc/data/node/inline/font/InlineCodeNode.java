package dev.pmlc.data.node.inline.font;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.font.InlineCodeNodeSpec;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;

public class InlineCodeNode extends PMLInlineNode {

    public InlineCodeNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<String, InlineCodeNode> getNodeSpec () { return InlineCodeNodeSpec.NODE; }
}
