package dev.pmlc.data.node.block.footnote;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.footnote.FootnotesPlaceholderNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
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
    public @NotNull PMLNodeSpec<Void, FootnotesPlaceholderNode> getNodeSpec () { return FootnotesPlaceholderNodeSpec.NODE; }
}
