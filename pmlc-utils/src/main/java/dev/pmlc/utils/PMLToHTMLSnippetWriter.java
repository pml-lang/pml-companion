package dev.pmlc.utils;

import dev.pmlc.data.node.block.DocumentNode;
import dev.pmlc.converter.pmltohtml.options.PMLToHTMLOptions;
import dev.pmlc.converter.pmltohtml.writer.HTMLNodesWriter;
import dev.pmlc.parser.PMLParser;
import dev.pp.basics.annotations.NotNull;

import java.io.*;

public class PMLToHTMLSnippetWriter {

    public static @NotNull String getHTMLSnippet ( @NotNull String PMLSnippet ) throws Exception {

        if ( ! PMLSnippet.startsWith ( "[doc " ) )
            PMLSnippet = "[doc " + PMLSnippet + "]";

        try ( Reader PMLDocumentReader = new StringReader ( PMLSnippet );
            Writer HTMLSnippetWriter = new StringWriter() ) {

            writeHTMLSnippet ( PMLDocumentReader, HTMLSnippetWriter );

            return HTMLSnippetWriter.toString();
        }
    }

    private static void writeHTMLSnippet (
        @NotNull Reader PMLDocumentReader,
        @NotNull Writer HTMLSnippetWriter ) throws Exception {

        DocumentNode documentNode = PMLParser.parseReader ( PMLDocumentReader, null );

        HTMLNodesWriter htmlNodesWriter = new HTMLNodesWriter (
            HTMLSnippetWriter, PMLToHTMLOptions.builder().build() );

        htmlNodesWriter.handleChildNodes ( documentNode.getChildNodes() );
    }
}
