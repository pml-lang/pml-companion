package dev.pmlc.ext.utilities.pmltohtml.writer;

import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.block.FormalQuoteNode;
import dev.pmlc.core.data.node.block.QuoteNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

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
                    <p class="pml-paragraph">Everything should be as simple as possible, but not simpler.</p>
                </div>
                <div class="pml-quote-text-suffix"></div>
            </div>
            <div class="pml-quote-source">Albert Einstein, physicist</div>
        </blockquote>
        */

        // <blockquote class="pml-quote">
        helper.writeBlockNodeStartLine ( node, FormalQuoteNode.HTML_TAG, FormalQuoteNode.CSS_CLASS, null, true );

        // <div class="pml-quote-text-block">
        helper.writeHTMLBlockStartTag (
            "div", null, FormalPMLNodeCreator.prefixedHTMLClassName ( "quote-text-block" ), null, null );
        helper.increaseIndent();

        // <div class="pml-quote-text-prefix"></div>
        helper.writeIndent();
        helper.writeHTMLStartTag (
            "div", null, FormalPMLNodeCreator.prefixedHTMLClassName ( "quote-text-prefix" ), null, null );
        helper.writeHTMLEndTag ( "div" );
        helper.writeNewLine();

        // <div class="pml-quote-text">
        helper.writeHTMLBlockStartTag (
            "div", null, FormalPMLNodeCreator.prefixedHTMLClassName ( "quote-text" ), null, null );
        helper.increaseIndent();
        nodesHandler.handleChildNodes ( node.getChildNodes() );
        helper.decreaseIndent();
        helper.writeBlockEndLine ( "div" );

        // <div class="pml-quote-text-suffix"></div>
        helper.writeIndent();
        helper.writeHTMLStartTag (
            "div", null, FormalPMLNodeCreator.prefixedHTMLClassName ( "quote-text-suffix" ), null, null );
        helper.writeHTMLEndTag ( "div" );
        helper.writeNewLine();

        helper.decreaseIndent ();
        helper.writeBlockEndLine ( "div" );

        // <div class="pml-quote-source">Albert Einstein, physicist</div>
        @Nullable String source = node.getSource();
        if ( source != null ) {
            helper.writeIndent ();
            helper.writeHTMLStartTag (
                "div", null, FormalPMLNodeCreator.prefixedHTMLClassName ( "quote-source" ), null, null );
            helper.escapeAndWriteText ( source );
            helper.writeHTMLEndTag ( "div" );
            helper.writeNewLine ();
        }

        helper.decreaseIndent();
        helper.writeBlockEndLine ( FormalQuoteNode.HTML_TAG );
    }
}
