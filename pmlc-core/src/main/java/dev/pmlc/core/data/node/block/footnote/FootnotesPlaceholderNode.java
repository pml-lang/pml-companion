package dev.pmlc.core.data.node.block.footnote;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.footnote.FormalFootnotesPlaceholderNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FootnotesPlaceholderNode extends PMLBlockNode {


    private final @NotNull List<FootnoteDefinitionNode> definitions = new ArrayList<>();
    @Nullable public List<FootnoteDefinitionNode> getDefinitions() { return definitions; }

    public void addDefinition ( FootnoteDefinitionNode definition ) {
        definitions.add ( definition );
    }


    public FootnotesPlaceholderNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, FootnotesPlaceholderNode> getFormalNode() { return FormalFootnotesPlaceholderNode.NODE; }
}
