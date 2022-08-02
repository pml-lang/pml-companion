package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.FormalAdmonitionNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.list.Parameters;

public class AdmonitionNode extends PMLBlockNode {

    private @NotNull String label = "";
    public @NotNull String getLabel() { return label; }


    public AdmonitionNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, AdmonitionNode> getFormalNode() { return FormalAdmonitionNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        assert parameters != null;

        super.createAttributes ( parameters );

        label = parameters.getNonNull ( FormalAdmonitionNode.LABEL_ATTRIBUTE );
    }
}
