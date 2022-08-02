package dev.pmlc.ext.utilities.pmltohtml;

import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pmlc.core.parser.PMLParser;
import dev.pmlc.ext.utilities.pmltohtml.options.PMLToHTMLOptions;
import dev.pmlc.ext.utilities.pmltohtml.writer.HTMLNodesWriter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.error.handler.TextErrorHandler;
import dev.pp.text.resource.TextResource;
import dev.pp.text.utilities.html.HTMLWriter;
import dev.pp.texttable.writer.pretty.utilities.TextErrorOrWarning_FormWriter;

import java.io.*;

public class PMLToHTMLSnippetWriter {

    public static @NotNull String getHTMLSnippet ( @NotNull String PMLSnippet ) throws Exception {

        if ( ! PMLSnippet.startsWith ( "[doc " ) )
            PMLSnippet = "[doc " + PMLSnippet + "]";

        try ( Reader PMLDocumentReader = new StringReader ( PMLSnippet );
            Writer HTMLSnippetWriter = new StringWriter() ) {

            writeHTMLSnippet ( PMLDocumentReader, null, HTMLSnippetWriter,
                TextErrorOrWarning_FormWriter.createLogErrorHandler() );

            return HTMLSnippetWriter.toString();
        }
    }

    public static void writeHTMLSnippet (
        @NotNull Reader PMLDocumentReader,
        @Nullable TextResource inputTextResource,
        @NotNull Writer HTMLSnippetWriter,
        @NotNull TextErrorHandler errorHandler ) throws Exception {

        DocumentNode documentNode = PMLParser.parseReader ( PMLDocumentReader, inputTextResource, errorHandler );

        HTMLNodesWriter htmlNodesWriter = new HTMLNodesWriter (
            new HTMLWriter ( HTMLSnippetWriter ), PMLToHTMLOptions.builder().build() );

        htmlNodesWriter.handleChildNodes ( documentNode.getChildNodes() );
    }
}
