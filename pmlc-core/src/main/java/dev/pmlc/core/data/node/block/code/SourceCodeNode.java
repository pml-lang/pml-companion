package dev.pmlc.core.data.node.block.code;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.code.FormalSourceCodeNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.Parameters;

public class SourceCodeNode extends PMLBlockNode {

    private @Nullable String language = null;
    public @Nullable String getLanguage() {
        return language;
    }

    private boolean useHighlighter = true;
    public boolean getUseHighlighter() {
        return useHighlighter;
    }


    public SourceCodeNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<String, SourceCodeNode> getFormalNode() { return FormalSourceCodeNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        super.createAttributes ( parameters );

        if ( parameters == null ) return;

        language = parameters.getNullable ( FormalSourceCodeNode.LANGUAGE_ATTRIBUTE );
        useHighlighter = parameters.getNonNull ( FormalSourceCodeNode.USE_HIGHLIGHTER_ATTRIBUTE );
    }
}
