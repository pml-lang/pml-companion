package dev.pmlc.utils;

import dev.pmlc.data.node.block.DocumentNode;
import dev.pmlc.converter.pmltohtml.options.PMLToHTMLOptions;
import dev.pmlc.converter.pmltohtml.writer.HTMLNodesWriter;
import dev.pmlc.parser.PMLParser;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.resource.TextResource;
import dev.pp.text.utilities.html.HTMLWriter;
import dev.pp.texttable.writer.pretty.utilities.TextInspectionMessage_FormWriter;

import java.io.*;

public class PMLToHTMLSnippetWriter {

    public static @NotNull String getHTMLSnippet ( @NotNull String PMLSnippet ) throws Exception {

        if ( ! PMLSnippet.startsWith ( "[doc " ) )
            PMLSnippet = "[doc " + PMLSnippet + "]";

        try ( Reader PMLDocumentReader = new StringReader ( PMLSnippet );
            Writer HTMLSnippetWriter = new StringWriter() ) {

            writeHTMLSnippet ( PMLDocumentReader, null, HTMLSnippetWriter,
                TextInspectionMessage_FormWriter.createLogMessageHandler() );

            return HTMLSnippetWriter.toString();
        }
    }

    public static void writeHTMLSnippet (
        @NotNull Reader PMLDocumentReader,
        @Nullable TextResource inputTextResource,
        @NotNull Writer HTMLSnippetWriter,
        @NotNull TextInspectionMessageHandler errorHandler ) throws Exception {

        DocumentNode documentNode = PMLParser.parseReader ( PMLDocumentReader, inputTextResource, null, null, errorHandler );

        HTMLNodesWriter htmlNodesWriter = new HTMLNodesWriter (
            new HTMLWriter ( HTMLSnippetWriter ), PMLToHTMLOptions.builder().build() );

        htmlNodesWriter.handleChildNodes ( documentNode.getChildNodes() );
    }
}
