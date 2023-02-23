package dev.pmlc.data.node.block.footnote;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.nodespec.block.footnote.FootnoteDefinitionNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.node.inline.footnote.FootnoteReferenceNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.text.inspection.TextErrorException;
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
    public @NotNull PMLNodeSpec<Void, FootnoteDefinitionNode> getNodeSpec () { return FootnoteDefinitionNodeSpec.NODE; }

    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        super.setAttributes ( parameters );

        if ( nodeId == null ) {
            TextToken token = parameters != null ? parameters.getStartToken() : null;
            if ( token == null ) token = getStartToken();

            throw new TextErrorException (
                "A footnote definition must have a unique identifier, defined by attribute '" +
                    SharedNodeSpecAttributes.ID_ATTRIBUTE_NAME + "'.",
                "ID_REQUIRED",
                token );
        }
    }
}
