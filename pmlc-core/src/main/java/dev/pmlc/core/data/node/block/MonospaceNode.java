package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.FormalMonospaceNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MonospaceNode extends PMLBlockNode {

    // A monospace node can only contain _inline_ child nodes

    public void setBlockChildNodes ( @Nullable List<PMLBlockNode> blockChildNodes ) {
        if ( blockChildNodes != null ) throwIfAddingBlockChildNode();
    }

    private @Nullable List<PMLInlineNode> inlineChildNodes = null;
    public @Nullable List<PMLInlineNode> getInlineChildNodes () { return inlineChildNodes; }
    public void setInlineChildNodes ( @Nullable List<PMLInlineNode> inlineChildNodes ) {
        this.inlineChildNodes = inlineChildNodes;
    }
    public boolean hasInlineChildNodes() { return inlineChildNodes != null && ! inlineChildNodes.isEmpty(); }



    public MonospaceNode () { super(); }

    /*
    public MonospaceNode ( @Nullable List<PMLInlineNode> inlineChildNodes ) {

        super();
        this.inlineChildNodes = inlineChildNodes;
    }
    */

    public @NotNull FormalPMLNode<Void, MonospaceNode> getFormalNode() { return FormalMonospaceNode.NODE; }

    public void addBlockChildNode ( @NotNull PMLBlockNode node ) {
        throwIfAddingBlockChildNode();
    }

    public void addInlineChildNode ( @NotNull PMLInlineNode node ) {

        if ( inlineChildNodes == null ) inlineChildNodes = new ArrayList<>();
        inlineChildNodes.add ( node );
    }

    private void throwIfAddingBlockChildNode() {
        throw new RuntimeException ( "A monospace node can only contain inline child nodes. Block child nodes are not allowed." );
    }
}
