package dev.pmlc.core.data.node.inline.footnote;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.footnote.FormalFootnoteReferenceNode;
import dev.pmlc.core.data.node.block.footnote.FootnoteDefinitionNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.Parameters;

public class FootnoteReferenceNode extends PMLInlineNode {

    private @NotNull String definitionId = "none";
    public @NotNull String getDefinitionId() { return definitionId; }
    public void setDefinitionId ( @NotNull String definitionId ) { this.definitionId = definitionId; }

    private @Nullable String inlineText = null;
    public @Nullable String getInlineText() { return inlineText; }
    // public void setInlineText ( @Nullable String inlineText ) { this.inlineText = inlineText; }

    // The id used in the back-reference, auto-assigned
    private @NotNull String id = "none";
    public @NotNull String getId() { return id; }
    public void setId ( @NotNull String id ) { this.id = id; }

    private @Nullable FootnoteDefinitionNode definitionNode = null;
    @Nullable public FootnoteDefinitionNode getDefinitionNode() { return definitionNode; }
    public void setDefinitionNode ( @NotNull FootnoteDefinitionNode definitionNode ) { this.definitionNode = definitionNode; }


    public int getListIndex() {
        assert definitionNode != null;
        return definitionNode.getRenderPosition();
    }


    public FootnoteReferenceNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, FootnoteReferenceNode> getFormalNode() { return FormalFootnoteReferenceNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        super.createAttributes ( parameters );

        assert parameters != null;
        definitionId = parameters.getNonNull ( FormalFootnoteReferenceNode.DEFINITION_ID_ATTRIBUTE );
        inlineText = parameters.getNullable ( FormalFootnoteReferenceNode.INLINE_TEXT_ATTRIBUTE );
    }
}
