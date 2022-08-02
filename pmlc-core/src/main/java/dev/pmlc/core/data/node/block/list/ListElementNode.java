package dev.pmlc.core.data.node.block.list;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.list.FormalListElementNode;
import dev.pmlc.core.data.formalnode.block.list.FormalListNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pmlc.core.data.node.validation.NodeValidationContext;
import dev.pp.basics.annotations.NotNull;

public class ListElementNode extends PMLBlockNode {

    public ListElementNode () { super(); }

    public @NotNull FormalPMLNode<Void, ListElementNode> getFormalNode() { return FormalListElementNode.NODE; }

    @Override
    public void validate ( @NotNull NodeValidationContext context ) {

        context.checkHasChildren ( this );
        context.checkParentType ( this, FormalListNode.NODE );
    }
}
