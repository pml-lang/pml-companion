package dev.pmlc.data.node.block.code;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.code.StyledSourceCodeNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class StyledSourceCodeNode extends AbstractSourceCodeNode {

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<String, StyledSourceCodeNode> getNodeSpec () { return StyledSourceCodeNodeSpec.NODE; }
}
