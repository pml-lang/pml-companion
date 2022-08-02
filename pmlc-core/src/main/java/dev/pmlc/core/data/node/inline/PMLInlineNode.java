package dev.pmlc.core.data.node.inline;

import dev.pmlc.core.data.node.PMLNode;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.list.Parameters;

import java.util.List;
import java.util.Map;

public abstract class PMLInlineNode extends PMLNode {

/*
    @Nullable List<PMLInlineNode> inlineChildNodes = null;
    public @Nullable List<PMLInlineNode> getInlineChildNodes () { return inlineChildNodes; }
    public void setInlineChildNodes ( @Nullable List<PMLInlineNode> inlineChildNodes ) { this.inlineChildNodes = inlineChildNodes; }
    public boolean hasInlineChildNodes() { return inlineChildNodes != null && ! inlineChildNodes.isEmpty(); }
*/


    protected PMLInlineNode() { super(); }

    protected PMLInlineNode ( @Nullable Map<String, String> HTMLAttributes ) {
        super ( HTMLAttributes );
    }

/*
    protected PMLInlineNode (
        @Nullable Map<String, String> HTMLAttributes,
        @Nullable List<PMLInlineNode> childNodes ) {

        this.HTMLAttributes = HTMLAttributes;
        this.childNodes = childNodes;
    }

 */


    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {
        // do nothing
    }

    public @Nullable List<? extends PMLNode> getChildNodes() {

        return inlineChildNodes;
    }

/*
    public void addInlineChildNode ( @NotNull PMLInlineNode node ) {

        if ( inlineChildNodes == null ) inlineChildNodes = new ArrayList<>();
        inlineChildNodes.add ( node );
    }
*/
}

