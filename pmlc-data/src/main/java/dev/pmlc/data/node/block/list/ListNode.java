package dev.pmlc.data.node.block.list;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.list.ListElementNodeSpec;
import dev.pmlc.data.nodespec.block.list.ListNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.node.validator.NodeValidatorContext;
import dev.pp.basics.annotations.NotNull;

public class ListNode extends PMLBlockNode {

    public ListNode () { super(); }

    public @NotNull PMLNodeSpec<Void, ListNode> getNodeSpec () { return ListNodeSpec.NODE; }

    @Override
    public void validate ( @NotNull NodeValidatorContext context ) {

        context.checkHasChildren ( this );
        context.checkChildrenType ( this, ListElementNodeSpec.NODE );
    }
}
