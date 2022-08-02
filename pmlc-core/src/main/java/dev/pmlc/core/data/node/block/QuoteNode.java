package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.FormalQuoteNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.list.Parameters;

public class QuoteNode extends PMLBlockNode {

    private @Nullable String source = null;
    public @Nullable String getSource() { return source; }


    public QuoteNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, QuoteNode> getFormalNode() { return FormalQuoteNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        super.createAttributes ( parameters );

        if ( parameters != null )
            source = parameters.getNullable ( FormalQuoteNode.SOURCE_ATTRIBUTE );
    }
}
