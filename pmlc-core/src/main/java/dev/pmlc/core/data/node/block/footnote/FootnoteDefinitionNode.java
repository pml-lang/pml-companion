package dev.pmlc.core.data.node.block.footnote;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.formalnode.block.footnote.FormalFootnoteDefinitionNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pmlc.core.data.node.inline.footnote.FootnoteReferenceNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.Parameters;
import dev.pp.text.error.TextErrorException;
import dev.pp.text.token.TextToken;

import java.util.ArrayList;
import java.util.List;

public class FootnoteDefinitionNode extends PMLBlockNode {


    private final @NotNull List<FootnoteReferenceNode> references = new ArrayList<>();
    @NotNull public List<FootnoteReferenceNode> getReferences () { return references; }
    public void addReference ( FootnoteReferenceNode references_ ) {
        references.add ( references_ );
    }

    private int renderPosition = 0;
    public int getRenderPosition() { return renderPosition; }
    public void setRenderPosition ( int renderPosition ) { this.renderPosition = renderPosition; }


    public FootnoteDefinitionNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, FootnoteDefinitionNode> getFormalNode() { return FormalFootnoteDefinitionNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        super.createAttributes ( parameters );

        if ( nodeId == null ) {
            TextToken token = parameters != null ? parameters.getStartToken() : null;
            if ( token == null ) token = getStartToken();

            throw new TextErrorException (
                "ID_REQUIRED",
                "A footnote definition must have a unique identifier, defined by attribute '" +
                    SharedFormalNodeAttributes.ID_ATTRIBUTE_NAME + "'.",
                token );
        }
    }
}
