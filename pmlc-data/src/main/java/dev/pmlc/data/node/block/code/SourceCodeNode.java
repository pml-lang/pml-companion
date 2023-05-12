package dev.pmlc.data.node.block.code;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.code.SourceCodeNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class SourceCodeNode extends AbstractSourceCodeNode {

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<String, SourceCodeNode> getNodeSpec () { return SourceCodeNodeSpec.NODE; }
}
