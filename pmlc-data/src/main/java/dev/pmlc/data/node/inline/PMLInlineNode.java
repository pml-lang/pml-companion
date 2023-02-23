package dev.pmlc.data.node.inline;

import dev.pmlc.data.node.PMLNode;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.Parameters;

import java.util.List;
import java.util.Map;

public abstract class PMLInlineNode extends PMLNode {


    protected PMLInlineNode() { super(); }

    protected PMLInlineNode ( @Nullable Map<String, String> HTMLAttributes ) {
        super ( HTMLAttributes );
    }


    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {
        // do nothing
    }

    @Override
    public @Nullable List<? extends PMLNode> getChildNodes() { return inlineChildNodes; }
}

