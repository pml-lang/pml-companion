package dev.pmlc.converter.pmltohtml.writer;

import dev.pmlc.data.node.block.quote.QuoteSourceNode;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.block.quote.QuoteNodeSpec;
import dev.pmlc.data.node.block.quote.QuoteNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pmlc.data.nodespec.block.quote.QuoteSourceNodeSpec;
import dev.pp.basics.annotations.NotNull;

public class QuoteHTMLWriter {

    static void writeQuote (
        @NotNull QuoteNode node,
        @NotNull PMLNodesHandler nodesHandler,
        @NotNull HTMLNodesWriterHelper helper ) throws Exception {

        /*
        <blockquote class="pml-quote">
            <div class="pml-quote-text-block">
                <div class="pml-quote-text-prefix"></div>
                <div class="pml-quote-text">
                    <p class="pml-paragraph">text</p>
                </div>
                <div class="pml-quote-text-suffix"></div>
            </div>
            <div class="pml-quote-source">
                <p class="pml-paragraph">source</p>
            </div>
        </blockquote>
        */

        // <blockquote class="pml-quote">
        helper.writeBlockNodeStartLine ( node, QuoteNodeSpec.HTML_TAG, QuoteNodeSpec.CSS_CLASS, null, true );

        // <div class="pml-quote-text-block">
        helper.writeHTMLBlockStartTag (
            "div", null, PMLNodeSpecCreator.prefixedHTMLClassName ( "quote-text-block" ), null, null );
        helper.increaseIndent();

        // <div class="pml-quote-text-prefix"></div>
        helper.writeIndent();
        helper.writeHTMLStartTag (
            "div", null, PMLNodeSpecCreator.prefixedHTMLClassName ( "quote-text-prefix" ), null, null );
        helper.writeHTMLEndTag ( "div" );
        helper.writeNewLine();

        // <div class="pml-quote-text">
        helper.writeHTMLBlockStartTag (
            "div", null, PMLNodeSpecCreator.prefixedHTMLClassName ( "quote-text" ), null, null );
        helper.increaseIndent();
        nodesHandler.handleChildNodes ( node.getChildNodes() );
        helper.decreaseIndent();
        helper.writeBlockEndLine ( "div" );

        // <div class="pml-quote-text-suffix"></div>
        helper.writeIndent();
        helper.writeHTMLStartTag (
            "div", null, PMLNodeSpecCreator.prefixedHTMLClassName ( "quote-text-suffix" ), null, null );
        helper.writeHTMLEndTag ( "div" );
        helper.writeNewLine();

        helper.decreaseIndent ();
        helper.writeBlockEndLine ( "div" );

        QuoteSourceNode sourceNode = node.getSourceNode();
        if ( sourceNode != null ) {
            writeQuoteSource ( sourceNode, nodesHandler, helper );
        }

        helper.decreaseIndent();
        helper.writeBlockEndLine ( QuoteNodeSpec.HTML_TAG );
    }

    static void writeQuoteSource (
        @NotNull QuoteSourceNode node,
        @NotNull PMLNodesHandler nodesHandler,
        @NotNull HTMLNodesWriterHelper helper ) throws Exception {

        helper.writeBlockNodeWithBlockChildren ( node, QuoteSourceNodeSpec.HTML_TAG, QuoteSourceNodeSpec.CSS_CLASS );
    }
}
