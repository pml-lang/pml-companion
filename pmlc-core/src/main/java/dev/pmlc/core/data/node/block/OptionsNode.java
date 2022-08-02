package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.FormalOptionsNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.textTokenParameter.TextTokenParameters;

public class OptionsNode extends PMLBlockNode {

    private @Nullable TextTokenParameters parameters;
    public @Nullable TextTokenParameters getParameters() { return parameters; }
    public void setParameters ( @Nullable TextTokenParameters parameters ) { this.parameters = parameters; }

    public OptionsNode () { super(); }

    public @NotNull FormalPMLNode<Void, OptionsNode> getFormalNode() { return FormalOptionsNode.NODE; }
}
