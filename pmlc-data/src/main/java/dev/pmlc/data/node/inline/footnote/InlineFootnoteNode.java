package dev.pmlc.data.node.inline.footnote;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.footnote.InlineFootnoteNodeSpec;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class InlineFootnoteNode extends PMLInlineNode {

    private @Nullable FootnoteReferenceNode referenceNode = null;
    @Nullable public FootnoteReferenceNode getReferenceNode () { return referenceNode; }
    public void setReferenceNode ( @NotNull FootnoteReferenceNode referenceNode ) { this.referenceNode = referenceNode; }

    /*
    private @Nullable FootnoteDefinitionNode definitionNode = null;
    @Nullable public FootnoteDefinitionNode getDefinitionNode() { return definitionNode; }
    public void setDefinitionNode ( @NotNull FootnoteDefinitionNode definitionNode ) { this.definitionNode = definitionNode; }
     */

    public InlineFootnoteNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, InlineFootnoteNode> getNodeSpec () { return InlineFootnoteNodeSpec.NODE; }
}
