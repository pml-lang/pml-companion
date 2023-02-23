package dev.pmlc.data.node.block;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.ParagraphNodeSpec;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.token.TextToken;

import java.util.ArrayList;
import java.util.List;

public class ParagraphNode extends PMLBlockNode {

    // A paragraph node can only contain _inline_ child nodes

    public void setBlockChildNodes ( @Nullable List<PMLBlockNode> blockChildNodes ) {
        if ( blockChildNodes != null ) throwIfAddingBlockChildNode();
    }


    public ParagraphNode() { super(); }

    public ParagraphNode ( @NotNull TextToken startToken ) {

        super();
        this.startToken = startToken;
    }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, ParagraphNode> getNodeSpec () { return ParagraphNodeSpec.NODE; }

    public void addBlockChildNode ( @NotNull PMLBlockNode node ) {
        throwIfAddingBlockChildNode();
    }

    public void addInlineChildNode ( @NotNull PMLInlineNode node ) {

        if ( inlineChildNodes == null ) inlineChildNodes = new ArrayList<>();
        inlineChildNodes.add ( node );
    }

    private void throwIfAddingBlockChildNode() {
        throw new RuntimeException ( "A paragraph node can only contain inline child nodes. Block child nodes are not allowed." );
    }
}
