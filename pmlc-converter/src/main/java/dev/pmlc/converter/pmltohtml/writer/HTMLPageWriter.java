package dev.pmlc.converter.pmltohtml.writer;

import dev.pmlc.converter.pmltohtml.options.PMLToHTMLOptions;
import dev.pmlc.data.PmlcVersion;
import dev.pmlc.data.node.block.DocumentNode;
import dev.pmlc.data.node.block.TOCNode;
import dev.pmlc.data.node.block.TOCPosition;
import dev.pmlc.converter.pmltohtml.PMLToHTMLConverter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.HTMLUtils;
import dev.pp.basics.utilities.directory.DirectoryContentUtils;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.text.utilities.html.HTMLWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

public class HTMLPageWriter {

    public static void writePage (
        @NotNull DocumentNode documentNode,
        @NotNull Writer HTMLOutputWriter,
        @NotNull PMLToHTMLOptions options ) throws Exception {

        @NotNull HTMLWriter HTMLWriter = new HTMLWriter ( HTMLOutputWriter );
        @NotNull HTMLNodesWriter HTMLNodesWriter = new HTMLNodesWriter ( HTMLWriter, options );

        writePageStart ( HTMLWriter, options, documentNode );
        writeBodyStart ( HTMLWriter, HTMLNodesWriter, options, documentNode );
        HTMLNodesWriter.document ( documentNode );
        writeBodyEnd ( HTMLWriter );
        writePageEnd ( HTMLWriter, options );
    }

    private static void writePageStart (
        @NotNull HTMLWriter HTMLWriter,
        @NotNull PMLToHTMLOptions options,
        @NotNull DocumentNode documentNode ) throws Exception {

        @Nullable Supplier<String> supplier = options.HTMLPageStartTemplate();
        if ( supplier != null ) {
            @Nullable String template = supplier.get();
            if ( template != null ) {
                @NotNull String header = createPageStartText ( template, options, documentNode );
                HTMLWriter.write ( header );
            }
        }

        HTMLWriter
            .increaseIndent()
            .increaseIndent();
    }

    private static @NotNull String createPageStartText (
        final @NotNull String template,
        @NotNull PMLToHTMLOptions options,
        @NotNull DocumentNode documentNode ) throws IOException {

        @NotNull String result = template;

        // version
        result = result.replace ( "{{PML_version}}", PmlcVersion.VERSION );

        // title
        @Nullable String docTitle = documentNode.getTitleText();
        result = replaceVariable ( result, "title",
            docTitle != null ? HTMLUtils.escapeHTMLText ( docTitle ) : null );

        // Stylesheets
        result = replaceVariable ( result, "stylesheets", stylesheetsCode ( options ) );

        // JS code
        result = replaceVariable ( result, "Javascript", JSCode ( options ) );

        return result;
    }

    private static @NotNull String replaceVariable (
        @NotNull String template,
        @NotNull String variable,
        @Nullable String value ) {

        if ( value == null ) value = "";
        return template.replace ( "{{" + variable + "}}", value );
    }

    private static @Nullable String stylesheetsCode (
        @NotNull PMLToHTMLOptions options ) throws IOException {

        if ( options.omitCSS() ) return null;

        @NotNull StringBuilder code = new StringBuilder();

        appendStylesheets ( options.CSSFiles(), code );

        // Highlighters
        @Nullable String highlighterURL = switch ( options.sourceCodeHighlighter() ) {
            case HIGHLIGHTJS -> "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.0.1/styles/default.min.css";
            case PRISM       -> "https://cdnjs.cloudflare.com/ajax/libs/prism/1.23.0/themes/prism.min.css";
            default -> null;
        };
        if ( highlighterURL != null ) {
            appendStylesheet ( highlighterURL, code );
        }

        return code.isEmpty() ? null : code.toString();
    }

    private static void appendStylesheets (
        @Nullable List<Path> CSSDirectoriesAndFiles,
        @NotNull StringBuilder code ) throws IOException {

        if ( CSSDirectoriesAndFiles == null ) return;

        for ( Path CSSDirectoryOrFile : CSSDirectoriesAndFiles ) {
            if ( Files.isDirectory ( CSSDirectoryOrFile ) ) {
                DirectoryContentUtils.forEachFileInTree ( CSSDirectoryOrFile, filePath -> {
                    if ( PMLToHTMLConverter.isCSSFile ( filePath ) ) {
                        Path relativeFilePath = CSSDirectoryOrFile.relativize ( filePath );
                        appendStylesheet ( relativeFilePath.toString (), code );
                    }
                });
            } else {
                if ( Files.exists ( CSSDirectoryOrFile ) ) {
                    appendStylesheet ( CSSDirectoryOrFile.getFileName().toString(), code );
                }
            }
        }
    }

    private static void appendStylesheet ( @NotNull String stylesheetURL, @NotNull StringBuilder code ) {

        code.append ( "        <link rel=\"stylesheet\" href=\"" );
        code.append ( PMLToHTMLConverter.CSS_DIRECTORY_NAME );
        code.append ( "/" );
        code.append ( stylesheetURL.replace ( '\\', '/' ) );
        if ( stylesheetURL.contains ( "print" ) ) {
            // TODO should find a more reliable way to decide if it's a stylesheet for media="print"
            code.append ( "\" media=\"print\">" );
        } else {
            code.append ( "\">" );
        }
        code.append ( StringConstants.OS_NEW_LINE );
    }

    private static @Nullable String JSCode (
        @NotNull PMLToHTMLOptions options ) {

        @NotNull StringBuilder code = new StringBuilder();

        // Highlighters
        switch ( options.sourceCodeHighlighter() ) {
            case HIGHLIGHTJS:
                appendJSLib (
                    "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.0.1/highlight.min.js", code );
                code.append ( "        <script>hljs.highlightAll();</script>" );
                code.append ( StringConstants.OS_NEW_LINE );
                break;
            case PRISM:
                appendJSLib (
                    "https://cdnjs.cloudflare.com/ajax/libs/prism/1.23.0/prism.min.js", code );
                appendJSLib (
                    "https://cdnjs.cloudflare.com/ajax/libs/prism/1.23.0/plugins/autoloader/prism-autoloader.min.js",
                    code );
                break;
            default:
        }

        return code.isEmpty() ? null : code.toString();
    }

    private static void appendJSLib ( @NotNull String libURL, @NotNull StringBuilder code ) {

        code.append ( "        <script src=\"" );
        code.append ( libURL );
        code.append ( "\"></script>" );
        code.append ( StringConstants.OS_NEW_LINE );
    }

    private static void writeBodyStart (
        @NotNull HTMLWriter writer,
        @NotNull HTMLNodesWriter HTMLNodesWriter,
        @NotNull PMLToHTMLOptions options,
        @NotNull DocumentNode documentNode ) throws Exception {

        writer
            .writeBlockStartLine ( "div", "pml-doc-wrapper" )
            .increaseIndent()

            .writeBlockStartLine ( "header", "pml-doc-header" )
            .writeBlockEndLine ( "header" )

            .writeBlockStartLine ( "div", "pml-doc-content" )
            .increaseIndent()

            .writeBlockStartLine ( "aside", "pml-doc-left" )
            .increaseIndent();

        if ( options.TOCPosition() == TOCPosition.LEFT ) {
            @NotNull TOCNode rootTOCNode = documentNode.createTOC ( options.maxTOCChapterLevel() );
            HTMLNodesWriter.TOC ( rootTOCNode );
        }

        writer
            .decreaseIndent()
            .writeBlockEndLine ( "aside" );
    }

    private static void writeBodyEnd (
        @NotNull HTMLWriter writer ) throws IOException {

        writer
            .writeBlockStartLine ( "aside", "pml-doc-right" )
            .writeBlockEndLine ( "aside" )

            .decreaseIndent()
            .writeBlockEndLine ( "div" )

            .writeBlockStartLine ( "footer", "pml-doc-footer" )
            .writeBlockEndLine ( "footer" )

            .decreaseIndent()
            .writeBlockEndLine ( "div" );
    }

    private static void writePageEnd (
        @NotNull HTMLWriter writer,
        @NotNull PMLToHTMLOptions options ) throws IOException {

        writer
            .decreaseIndent()
            .decreaseIndent();

        @Nullable Supplier<String> endTemplate = options.HTMLPageEndTemplate();
        if ( endTemplate != null ) {
            writer.write ( endTemplate.get() );
        }
    }
}
