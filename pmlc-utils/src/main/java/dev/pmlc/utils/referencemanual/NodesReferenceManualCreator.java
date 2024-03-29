package dev.pmlc.utils.referencemanual;

import dev.pmlc.converter.pmltohtml.PMLToHTMLConverter;
import dev.pmlc.data.PmlcVersion;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.*;
import dev.pmlc.data.nodespec.block.admonition.AdmonitionNodeSpec;
import dev.pmlc.data.nodespec.block.admonition.NoteNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.ChapterNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.SubtitleNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.TitleNodeSpec;
import dev.pmlc.data.nodespec.block.code.*;
import dev.pmlc.data.nodespec.block.footnote.FootnoteDefinitionNodeSpec;
import dev.pmlc.data.nodespec.block.footnote.FootnotesPlaceholderNodeSpec;
import dev.pmlc.data.nodespec.block.list.ListElementNodeSpec;
import dev.pmlc.data.nodespec.block.list.ListNodeSpec;
import dev.pmlc.data.nodespec.block.media.AudioNodeSpec;
import dev.pmlc.data.nodespec.block.media.ImageNodeSpec;
import dev.pmlc.data.nodespec.block.media.VideoNodeSpec;
import dev.pmlc.data.nodespec.block.media.YoutubeVideoNodeSpec;
import dev.pmlc.data.nodespec.block.quote.QuoteNodeSpec;
import dev.pmlc.data.nodespec.block.table.*;
import dev.pmlc.data.nodespec.inline.*;
import dev.pmlc.data.nodespec.inline.font.*;
import dev.pmlc.data.nodespec.inline.footnote.FootnoteReferenceNodeSpec;
import dev.pmlc.data.nodespec.inline.footnote.InlineFootnoteNodeSpec;
import dev.pmlc.utils.PMLDocumentWriter;
import dev.pmlc.utils.PMLToHTMLSnippetWriter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.directory.DirectoryCreator;
import dev.pp.basics.utilities.file.TextFileIO;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.parameters.parameterspecs.ParameterSpecs;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class NodesReferenceManualCreator {

    public static Map<String,String> getMetaData() {

        Map<String,String> map = new LinkedHashMap<>();

        map.put ( "PML Version", PmlcVersion.VERSION_TEXT );
        map.put ( "License", "[link (url=https://creativecommons.org/licenses/by-nd/4.0/) CC BY-ND 4.0]" );
        map.put ( "Author and Copyright", "Christian Neumanns" );
        map.put ( "Website", "[link (url=https://www.pml-lang.dev/)]" );

        return map;
    }


    private @NotNull PMLDocumentWriter writer = new PMLDocumentWriter ( OSIO.standardOutputUTF8Writer() );


    public NodesReferenceManualCreator () {}


    public void createManual () throws Exception {

        createManual ( Path.of ( "input" ), Path.of ( "resources" ), Path.of ( "output/HTML" ) );
    }

    public void createManual (
        @NotNull Path inputDirectory,
        @NotNull Path resourcesDirectory,
        @NotNull Path outputDirectory ) throws Exception {

        copyResources ( resourcesDirectory );
        @NotNull Path PMLFile = createPMLFile ( inputDirectory );
        createHTMLFile ( PMLFile, outputDirectory );
    }

    private Path createPMLFile ( @NotNull Path inputDirectory ) throws Exception {

        // inputDirectory = inputDirectory != null ? inputDirectory : OSDirectories.TEMPORARY_FILES_DIRECTORY;
        DirectoryCreator.createWithParentsIfNotExists ( inputDirectory );

        Path PMLFile = inputDirectory.resolve ( "index.pml" );
        try ( Writer textWriter = TextFileIO.getUTF8FileWriter ( PMLFile ) ) {
            writer = new PMLDocumentWriter ( textWriter );
            writeDocument();
        }

        return PMLFile;
    }

    private void copyResources ( @NotNull Path resourcesDirectory ) throws IOException {

        DirectoryCreator.createWithParentsIfNotExists ( resourcesDirectory );
        ReferenceManualResources.copyResources ( resourcesDirectory );
    }

    private void createHTMLFile ( @NotNull Path PMLFile, @NotNull Path outputDirectory ) throws Exception {

        DirectoryCreator.createWithParentsIfNotExists ( outputDirectory );
        Path HTMLFile = outputDirectory.resolve ( "index.html" );

        PMLToHTMLConverter.convertFile ( PMLFile, HTMLFile );
    }

    private void writeDocument() throws Exception {

        writer.writeDocNodeStartLine ( "PML Nodes Reference Manual" );
        writeNewLine();

        writer.writeMap ( getMetaData() );

        writeChapterIntroduction();
        writeChapterBlockNodes();
        writeChapterInlineNodes();

        writer.writeBlockNodeEndLine();
    }

    private void writeChapterIntroduction () throws IOException {

        startChapter ( "Introduction", "introduction" );
        writeParagraph ( "This document describes all standard [link (url=https://www.pml-lang.dev/) PML] nodes. For each type of node, its tag and attributes are listed, and an example shows how to use it." );
        endChapter();
    }

    private void writeChapterBlockNodes() throws Exception {

        startChapter ( "Block Nodes", "block_nodes" );

        writeParagraph ( "A [i block] node is a section that starts at a given line in the document and ends at a subsequent line." );
        writeParagraph ( "For example, a [i chapter], a [i paragraph], and a [i list] are all [i block] nodes." );

        startChapter ( "Fundamental Nodes", "fundamental_block_nodes" );
            writeNode ( DocumentNodeSpec.NODE );
            writeNode ( ParagraphNodeSpec.NODE );
            writeNode ( ChapterNodeSpec.NODE );
            writeNode ( TitleNodeSpec.NODE );
            writeNode ( SubtitleNodeSpec.NODE );
        endChapter();

        startChapter ( "Common Nodes", "common_nodes" );

            startChapter ( "List", "list_nodes" );
                writeNode ( ListNodeSpec.NODE );
                writeNode ( ListElementNodeSpec.NODE );
            endChapter();

            startChapter ( "Table", "table_nodes" );
                writeNode ( SimpleTableNodeSpec.NODE );
                writeNode ( TableNodeSpec.NODE );
                writeNode ( TableHeaderNodeSpec.NODE );
                writeNode ( TableFooterNodeSpec.NODE );
                writeNode ( TableRowNodeSpec.NODE );
                writeNode ( TableCellNodeSpec.NODE );
            endChapter();

            writeNode ( HeaderNodeSpec.NODE );
            writeNode ( CaptionNodeSpec.NODE );
            writeNode ( AdmonitionNodeSpec.NODE );
            writeNode ( NoteNodeSpec.NODE );
            writeNode ( QuoteNodeSpec.NODE );
            writeNode ( MonospaceNodeSpec.NODE );
            writeNode ( DivisionNodeSpec.NODE );
            writeNode ( HtmlCodeNodeSpec.NODE );
        endChapter();

        startChapter ( "Media Nodes", "media_nodes" );
            writeNode ( ImageNodeSpec.NODE );
            writeNode ( AudioNodeSpec.NODE );
            writeNode ( VideoNodeSpec.NODE );
            writeNode ( YoutubeVideoNodeSpec.NODE );
        endChapter();

        startChapter ( "Footnotes", "footnotes_block" );
            writeNode ( FootnotesPlaceholderNodeSpec.NODE );
            writeNode ( FootnoteDefinitionNodeSpec.NODE );
        endChapter();

        startChapter ( "Software Development", "software_development_nodes" );
            writeNode ( SourceCodeNodeSpec.NODE );
            writeNode ( InsertSourceCodeNodeSpec.NODE );
            writeNode ( InputNodeSpec.NODE );
            writeNode ( OutputNodeSpec.NODE );
        endChapter();

        endChapter();
    }

    private void writeChapterInlineNodes() throws Exception {

        startChapter ( "Inline Nodes", "inline_nodes" );

        writeParagraph ( "An [i inline] node represents a part of a paragraph (a span of text). All nodes contained in a paragraph are inline nodes." );
        writeParagraph ( "For example, the [c italic] node is an [i inline] node. All text contained in it is written in [i italics]." );

        startChapter ( "Font", "font_nodes" );
            writeNode ( BoldNodeSpec.NODE );
            writeNode ( ItalicNodeSpec.NODE );
            writeNode ( SubscriptNodeSpec.NODE );
            writeNode ( SuperscriptNodeSpec.NODE );
            writeNode ( StrikethroughNodeSpec.NODE );
            writeNode ( InlineCodeNodeSpec.NODE );
        endChapter();

        writeNode ( LinkNodeSpec.NODE );
        writeNode ( VerbatimTextNodeSpec.NODE );
        writeNode ( XrefNodeSpec.NODE );
        writeNode ( SpanNodeSpec.NODE );
        writeNode ( NewLineNodeSpec.NODE );
        writeNode ( SpaceNodeSpec.NODE );
        writeNode ( TextNodeSpec.NODE );

        startChapter ( "Footnotes", "footnotes_inline" );
            writeNode ( InlineFootnoteNodeSpec.NODE );
            writeNode ( FootnoteReferenceNodeSpec.NODE );
        endChapter();

        endChapter();
    }

    private void writeNode ( @NotNull PMLNodeSpec<?,?> node ) throws Exception {

        String title = node.getDocumentationTitle();
        assert title != null;
        startChapter ( title, "node_" + node.qualifiedName() );
        writeNewLine();

        writer.writeName ( node.getName().toString() );
        writeNodeKind ( node );
        writer.writeDocDescription ( node.getDocumentation() );
        writeNodeAttributes ( node );
        writeNodeHTMLAttributes ( node );
        writeNodeExamples ( node );

        endChapter();
    }

    private void writeNodeKind ( @NotNull PMLNodeSpec<?,?> node ) throws IOException {

        writeIndent ();
        write ( "[b Kind:] " );

        if ( node.isBlockChildNodesAllowed () || node.isInlineChildNodesAllowed () ) {
            write ( "Node with child nodes" );
        } else if ( node.isRawTextNode() ) {
            write ( "Node with raw text content" );
        } else  {
            write ( "Node without child nodes" );
        }

        writeNewLine ();
        writeNewLine ();
    }

    private void writeNodeAttributes ( @NotNull PMLNodeSpec<?,?> node ) throws IOException {

        writeHeader ( "Attributes" );

        ParameterSpecs<?> attributes = node.getAttributeSpecs();
        if ( attributes == null ) {
            writeParagraph ( "This node doesn't have attributes." );
        } else {
            writer.writeParameterSpecs ( attributes.listSortedByName() );
        }

        writeNewLine();
    }

    private void writeNodeHTMLAttributes ( @NotNull PMLNodeSpec<?,?> node ) throws IOException {

        writeIndent();
        write ( "[b HTML Attributes:] " );
        write ( node.isHTMLAttributesAllowed() ? "allowed" : "not allowed" );
        writeNewLine();
        writeNewLine();
    }

    private void writeNodeExamples ( @NotNull PMLNodeSpec<?,?> node ) throws Exception {

        @Nullable String example = node.getDocumentationExamples();
        if ( example == null ) return;

        /*
        [header Example]
        [list
            [el
                [p PML code:]
                [code
                ...
                ]
            ]
            [el
                [p Result:]
                [html
                ...
                ]
            ]
            [el
                [p HTML code generated:]
                [code
                ...
                ]
            ]
        ]
        */

        writeHeader ( "Example" );

        writeBlockNodeStartLine ( "list" );

        writeBlockNodeStartLine ( "el" );
        writeParagraph ( "PML code:" );
        writeRawTextBlock ( "code", example );
        writeBlockNodeEndLine();

        @NotNull String HTMLCode = PMLToHTMLSnippetWriter.getHTMLSnippet ( example );

        writeBlockNodeStartLine ( "el" );
        writeParagraph ( "Result:" );
        String HTMLText =
            "<div style=\"border: 1px dotted grey; padding-left: 1em; padding-right: 1em;border-radius: 7px;\">"
            + StringConstants.OS_NEW_LINE
            + HTMLCode + StringConstants.OS_NEW_LINE
            + "</div>" + StringConstants.OS_NEW_LINE;
        writeRawTextBlock ( "html", HTMLText );
        writeBlockNodeEndLine();

        writeBlockNodeStartLine ( "el" );
        writeParagraph ( "HTML code generated:" );
        writeRawTextBlock ( "code", HTMLCode );
        writeBlockNodeEndLine();

        writeBlockNodeEndLine();
        writeNewLine();
    }


    // Helpers

    private void startChapter ( @NotNull String title, @NotNull String id ) throws IOException {
        writer.writeChapterNodeStartLine ( title, id );
    }

    private void endChapter() throws IOException {
        writer.writeBlockNodeEndLine();
        writer.writeNewLine();
    }

    private void writeHeader ( @NotNull String header ) throws IOException {
        writer.writeBlockNodeWithTextContent ( "header", header );
    }

    private void writeParagraph ( @NotNull String text ) throws IOException {
        writer.writeParagraph ( text );
    }

    private void writeRawTextBlock ( @NotNull String nodeName, @NotNull String text ) throws IOException {

        writer.writeBlockNodeWithRawTextContent ( nodeName, null, text );
    }

    private void writeBlockNodeStartLine (
        @NotNull String nodeName ) throws IOException {

        writer.writeBlockNodeStartLine ( nodeName, null );
    }

    private void writeBlockNodeEndLine() throws IOException {
        writer.writeBlockNodeEndLine();
    }

    private void write ( @NotNull String text ) throws IOException {
        writer.write ( text );
    }

    private void writeNewLine() throws IOException {
        writer.writeNewLine();
    }

    private void writeIndent() throws IOException {
        writer.writeIndent();
    }
}
