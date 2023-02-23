package dev.pmlc.converter.pmltohtml.writer;

import dev.pmlc.data.node.block.admonition.AdmonitionLabelNode;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.block.admonition.AdmonitionLabelNodeSpec;
import dev.pmlc.data.nodespec.block.admonition.AdmonitionNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class AdmonitionHTMLWriter {

    static void writeAdmonition (
        @NotNull PMLBlockNode node,
        @Nullable AdmonitionLabelNode labelNode,
        @NotNull PMLNodesHandler nodesHandler,
        @NotNull HTMLNodesWriterHelper helper ) throws Exception {

        /*
        <div class="pml-admonition">
            <div class="pml-admonition-label">
                <p class="pml-paragraph">Tip</p>
            </div>
            <div class="pml-admonition-content">
                <p class="pml-paragraph">This is a tip</p>
            </div>
        </div>
        */

        helper.writeBlockNodeStartLine ( node, AdmonitionNodeSpec.HTML_TAG, AdmonitionNodeSpec.CSS_CLASS, null, true );

            if ( labelNode != null ){
                writeAdmonitionLabel ( labelNode, helper );
            }

            helper.writeHTMLBlockStartTag (
                "div", null, PMLNodeSpecCreator.prefixedHTMLClassName ( "admonition-content" ), null, null );

                helper.increaseIndent();
                nodesHandler.handleChildNodes ( node.getBlockChildNodes() );
                helper.decreaseIndent();

            helper.writeBlockEndLine ( "div" );

        helper.decreaseIndent();
        helper.writeBlockEndLine ( "div" );
    }

    static void writeAdmonitionLabel (
        @NotNull AdmonitionLabelNode labelNode,
        @NotNull HTMLNodesWriterHelper helper ) throws Exception {

        helper.writeBlockNodeWithBlockChildren (
            labelNode, AdmonitionLabelNodeSpec.HTML_TAG, AdmonitionLabelNodeSpec.CSS_CLASS );
    }
}
