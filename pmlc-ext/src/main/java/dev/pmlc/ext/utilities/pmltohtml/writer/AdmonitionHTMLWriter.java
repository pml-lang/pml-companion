package dev.pmlc.ext.utilities.pmltohtml.writer;

import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.block.FormalAdmonitionNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;

public class AdmonitionHTMLWriter {

    static void writeAdmonition (
        @NotNull PMLBlockNode node,
        @NotNull String label,
        @NotNull PMLNodesHandler nodesHandler,
        @NotNull HTMLNodesWriterHelper helper ) throws Exception {

        /*
        <div class="pml-admonition">
            <div class="pml-admonition-label">Tip</div>
            <div class="pml-admonition-content">
                <p class="pml-paragraph">We will have a look at some <i class="pml-italic">striking</i> examples later in the book.</p>
            </div>
        </div>
        */

        helper.writeBlockNodeStartLine ( node, FormalAdmonitionNode.HTML_TAG, FormalAdmonitionNode.CSS_CLASS, null, true );

            helper.writeIndent();
            helper.writeHTMLStartTag (
                "div", null, FormalPMLNodeCreator.prefixedHTMLClassName ( "admonition-label" ), null, null );
            helper.escapeAndWriteText ( label );
            helper.writeHTMLEndTag ( "div" );
            helper.writeNewLine();

            helper.writeHTMLBlockStartTag (
                "div", null, FormalPMLNodeCreator.prefixedHTMLClassName ( "admonition-content" ), null, null );

                helper.increaseIndent();
                nodesHandler.handleChildNodes ( node.getBlockChildNodes() );
                helper.decreaseIndent();

            helper.writeBlockEndLine ( "div" );

        helper.decreaseIndent();
        helper.writeBlockEndLine ( "div" );
    }
}
