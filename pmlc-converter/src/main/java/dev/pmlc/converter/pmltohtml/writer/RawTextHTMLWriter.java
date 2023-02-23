package dev.pmlc.converter.pmltohtml.writer;

import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.block.code.InputNodeSpec;
import dev.pmlc.data.nodespec.block.code.SourceCodeNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.node.block.code.SourceCodeHighlighter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.IOException;

public class RawTextHTMLWriter {

    static void writeSourceCode (
        @NotNull PMLBlockNode node,
        @Nullable String language,
        boolean useHighlighterInNode,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        @NotNull SourceCodeHighlighter configHighlighter = helper.getOptions().sourceCodeHighlighter();

        @Nullable String preClassName = PMLNodeSpecCreator.prefixedHTMLClassName ( "code" );
        @Nullable String codeClassName = null;

        if ( ! useHighlighterInNode ) {
            if ( configHighlighter == SourceCodeHighlighter.HIGHLIGHTJS ) {
                codeClassName = "nohighlight";
            }

        } else {

            switch ( configHighlighter ) {

                case HIGHLIGHTJS:
                    preClassName = null;
                    if ( language == null ) {
                        codeClassName = null;
                    } else {
                        codeClassName = switch ( language ) {
                            case "auto" -> null;
                            case "text" -> "plaintext";
                            case "none" -> "nohighlight";
                            default -> "language-" + language;
                        };
                    }
                    break;

                case PRISM:
                    preClassName = null;
                    codeClassName = "language-" + language;
                    break;

                case NONE:
            }
        }

        helper.writeIndent();
        helper.writeHTMLStartTag ( node, SourceCodeNodeSpec.HTML_TAG, preClassName, null );

        String codeTag = "code";
        helper.writeHTMLStartTag ( codeTag, null, codeClassName, null, null );

        writeRawText ( node, helper );

        helper.writeHTMLEndTag ( codeTag );
        helper.writeHTMLEndTag ( SourceCodeNodeSpec.HTML_TAG );
        helper.writeNewLine();
    }

    static void writeInputOutput (
        @NotNull PMLBlockNode node,
        @NotNull String CSSClassName,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        helper.writeIndent();
        String tag = InputNodeSpec.HTML_TAG;
        helper.writeHTMLStartTag ( tag, node.getNodeId(), CSSClassName, node.getHTMLAttributes(), null );
        writeRawText ( node, helper );
        helper.writeHTMLEndTag ( tag );
        helper.writeNewLine();
    }

    private static void writeRawText (
        @NotNull PMLBlockNode node,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        @Nullable String code = node.getRawText();
        if ( code != null ) helper.escapeAndWriteText ( code );
    }
}
