package dev.pmlc.core.data.node.block.list;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.list.FormalListElementNode;
import dev.pmlc.core.data.formalnode.block.list.FormalListNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pmlc.core.data.node.validation.NodeValidationContext;
import dev.pp.basics.annotations.NotNull;

public class ListNode extends PMLBlockNode {

    public ListNode () { super(); }

    public @NotNull FormalPMLNode<Void, ListNode> getFormalNode() { return FormalListNode.NODE; }

    @Override
    public void validate ( @NotNull NodeValidationContext context ) {

        context.checkHasChildren ( this );
        context.checkChildrenType ( this, FormalListElementNode.NODE );
    }
}
