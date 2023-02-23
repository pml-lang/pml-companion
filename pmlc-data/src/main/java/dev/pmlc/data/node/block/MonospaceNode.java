package dev.pmlc.data.node.block;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.MonospaceNodeSpec;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MonospaceNode extends PMLBlockNode {

    // A monospace node can only contain _inline_ child nodes

    public void setBlockChildNodes ( @Nullable List<PMLBlockNode> blockChildNodes ) {
        if ( blockChildNodes != null ) throwIfAddingBlockChildNode();
    }


    public MonospaceNode () { super(); }

    /*
    public MonospaceNode ( @Nullable List<PMLInlineNode> inlineChildNodes ) {

        super();
        this.inlineChildNodes = inlineChildNodes;
    }
    */

    public @NotNull PMLNodeSpec<Void, MonospaceNode> getNodeSpec () { return MonospaceNodeSpec.NODE; }

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
