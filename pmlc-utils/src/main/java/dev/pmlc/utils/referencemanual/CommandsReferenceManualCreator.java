package dev.pmlc.utils.referencemanual;

import dev.pmlc.converter.pmltohtml.PMLToHTMLConverter;
import dev.pmlc.utils.PMLDocumentWriter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.directory.DirectoryCreator;
import dev.pp.basics.utilities.file.TextFileIO;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.commands.command.CommandSpecs;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

public class CommandsReferenceManualCreator {


    private @NotNull PMLDocumentWriter writer = new PMLDocumentWriter ( OSIO.standardOutputUTF8Writer() );


    public CommandsReferenceManualCreator () {}


    public void createManual ( @NotNull CommandSpecs commands ) throws Exception {

        // createCLIManual ( commands, Path.of ( "input" ), Path.of ( "resources" ), Path.of ( "output/HTML" ) );
        createManual ( commands, Path.of ( "input" ), Path.of ( "output/HTML" ) );
    }

    public void createManual (
        @NotNull CommandSpecs commands,
        @NotNull Path inputDirectory,
        // @NotNull Path resourcesDirectory,
        @NotNull Path outputDirectory ) throws Exception {

        // copyResources ( resourcesDirectory );
        @NotNull Path PMLFile = createPMLFile ( commands, inputDirectory );
        createHTMLFile ( PMLFile, outputDirectory );
    }

    private Path createPMLFile ( @NotNull CommandSpecs commands, @NotNull Path inputDirectory ) throws Exception {

        // inputDirectory = inputDirectory != null ? inputDirectory : OSDirectories.TEMPORARY_FILES_DIRECTORY;
        DirectoryCreator.createWithParentsIfNotExists ( inputDirectory );

        Path PMLFile = inputDirectory.resolve ( "index.pml" );
        try ( Writer textWriter = TextFileIO.getUTF8FileWriter ( PMLFile ) ) {
            writer = new PMLDocumentWriter ( textWriter );
            writeDocument ( commands );
        }

        return PMLFile;
    }

    private void createHTMLFile ( @NotNull Path PMLFile, @NotNull Path outputDirectory ) throws Exception {

        DirectoryCreator.createWithParentsIfNotExists ( outputDirectory );
        Path HTMLFile = outputDirectory.resolve ( "index.html" );

        PMLToHTMLConverter.convertFile ( PMLFile, HTMLFile );
    }

    private void writeDocument ( @NotNull CommandSpecs commands ) throws Exception {

        writer.writeDocNodeStartLine ( "PMLC Commands Reference Manual" );
        writer.writeNewLine();

        writer.writeMap ( NodesReferenceManualCreator.getMetaData() );

        writeChapterIntroduction();
        writeChapterCommands ( commands );

        writer.writeBlockNodeEndLine();
    }

    private void writeChapterIntroduction () throws IOException {

        writer.writeChapterNodeStartLine ( "Introduction", "introduction" );

            writer.writeParagraph ( "This document describes the commands supported by PMLC's command line interface (CLI)." );

            writer.writeBlockNodeStartLine ( "note", null );
            writer.writeParagraph ( "All command names and options are case-insensitive. For example, instead of writing [c PML_to_HTML], you can also write [c pml_to_html]." );
            writer.writeBlockNodeEndLine();

        endChapter();
    }

    private void writeChapterCommands ( @NotNull CommandSpecs commands ) throws Exception {

        writer.writeChapterNodeStartLine ( "Commands", "commands" );
        writer.writeCommands ( commands.listSortedByIndex () );
        endChapter();
    }

    // Helpers

    private void endChapter() throws IOException {
        writer.writeBlockNodeEndLine();
        writer.writeNewLine();
    }
}
