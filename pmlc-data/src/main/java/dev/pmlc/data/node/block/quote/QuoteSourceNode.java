package dev.pmlc.data.node.block.quote;

import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.quote.QuoteSourceNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class QuoteSourceNode extends PMLBlockNode {


    public QuoteSourceNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, QuoteSourceNode> getNodeSpec() { return QuoteSourceNodeSpec.NODE; }
}
