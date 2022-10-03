package dev.pmlc.core.data.node.inline.footnote;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.footnote.FormalInlineFootnoteNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
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
    public @NotNull FormalPMLNode<Void, InlineFootnoteNode> getFormalNode() { return FormalInlineFootnoteNode.NODE; }
}
