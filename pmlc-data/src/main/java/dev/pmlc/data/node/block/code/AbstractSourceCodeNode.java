package dev.pmlc.data.node.block.code;

import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.nodespec.block.code.SourceCodeNodeSpec;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.Parameters;

public abstract class AbstractSourceCodeNode extends PMLBlockNode {

    private @Nullable String language = null;
    public @Nullable String getLanguage() {
        return language;
    }

    private boolean useHighlighter = true;
    public boolean getUseHighlighter() {
        return useHighlighter;
    }


    public AbstractSourceCodeNode () { super(); }


    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        super.setAttributes ( parameters );

        if ( parameters == null ) return;

        language = parameters.castedValue ( SourceCodeNodeSpec.LANGUAGE_ATTRIBUTE );
        useHighlighter = parameters.nonNullCastedValue ( SourceCodeNodeSpec.USE_HIGHLIGHTER_ATTRIBUTE );
    }
}
