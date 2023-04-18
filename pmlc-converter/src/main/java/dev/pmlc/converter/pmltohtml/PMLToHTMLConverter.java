package dev.pmlc.converter.pmltohtml;

import dev.pdml.utils.SharedDefaultOptions;
import dev.pmlc.converter.pmltohtml.options.PMLToHTMLOptions;
import dev.pmlc.converter.pmltohtml.writer.HTMLPageWriter;
import dev.pmlc.data.node.block.DocumentNode;
import dev.pmlc.data.node.validator.NodeValidator;
import dev.pmlc.data.nodespec.PMLNodeSpecs;
import dev.pmlc.parser.PMLParser;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.basics.utilities.directory.DirectoryCopier;
import dev.pp.basics.utilities.directory.DirectoryCreator;
import dev.pp.basics.utilities.file.FileCopier;
import dev.pp.basics.utilities.file.FileNameUtils;
import dev.pp.basics.utilities.file.FilePathUtils;
import dev.pp.basics.utilities.file.TextFileIO;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.inspection.message.TextWarning;
import dev.pp.text.reader.stack.CharReaderWithInserts;
import dev.pp.text.reader.stack.CharReaderWithInsertsImpl;
import dev.pp.text.resource.File_TextResource;
import dev.pp.text.resource.TextResource;
import dev.pp.text.resource.reader.TextResourceReader;
import dev.pp.text.resource.writer.TextResourceWriter;
import dev.pp.texttable.writer.pretty.utilities.TextInspectionMessage_FormWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class PMLToHTMLConverter {

    public static final @NotNull String CSS_DIRECTORY_NAME = "css";


    public static void convertFile ( @NotNull Path PMLInputFile ) throws Exception {

        convertFile ( PMLInputFile, HTMLOutputFileForPMLInputFile ( PMLInputFile ) );
    }

    public static void convertFile (
        @NotNull Path PMLInputFile,
        @NotNull Path HTMLOutputFile ) throws Exception {

        convertFile ( PMLInputFile, HTMLOutputFile,
            PMLToHTMLOptions.builder().build(), TextInspectionMessage_FormWriter.createLogMessageHandler() );
    }

    public static void convertFile (
        @NotNull Path PMLInputFile,
        @NotNull Path HTMLOutputFile,
        @NotNull PMLToHTMLOptions options,
        @NotNull TextInspectionMessageHandler errorHandler ) throws Exception {

        @NotNull Path HTMLOutputDirectory = HTMLOutputFile.getParent();
        createOutputDirectory ( HTMLOutputDirectory );

        try ( Reader reader = TextFileIO.getUTF8FileReader ( PMLInputFile );
            Writer writer = TextFileIO.getUTF8FileWriter ( HTMLOutputFile ) ) {
            convert (
                reader, new File_TextResource ( PMLInputFile ),
                writer, new File_TextResource ( HTMLOutputFile ),
                HTMLOutputDirectory, options, errorHandler );
        }
    }

    public static void convert (
        @NotNull TextResourceReader inputReader,
        @NotNull TextResourceWriter outputWriter,
        @Nullable Path outputDirectory,
        @NotNull PMLToHTMLOptions options,
        @NotNull TextInspectionMessageHandler errorHandler ) throws Exception {

        convert ( inputReader.getReader(), inputReader.getTextResource(),
            outputWriter.getWriter(), outputWriter.getTextResource(),
            outputDirectory, options, errorHandler );
    }

    public static void convert (
        @NotNull Reader inputReader,
        @Nullable TextResource inputTextResource,
        @NotNull Writer outputWriter,
        @Nullable TextResource outputTextResource,
        @Nullable Path outputDirectory,
        @NotNull PMLToHTMLOptions options,
        @NotNull TextInspectionMessageHandler errorHandler ) throws Exception {

        convert ( CharReaderWithInsertsImpl.createAndAdvance ( inputReader, inputTextResource, null, null ),
            outputWriter, outputTextResource, outputDirectory, options, errorHandler );
    }

    public static void convert (
        @NotNull CharReaderWithInserts inputReader,
        @NotNull Writer outputWriter,
        @Nullable TextResource outputTextResource,
        @Nullable Path outputDirectory,
        @NotNull PMLToHTMLOptions options,
        @NotNull TextInspectionMessageHandler errorHandler ) throws Exception {

        SimpleLogger.debug ( "Reading PML input from " + inputReader );

        DocumentNode documentNode = PMLParser.parseReader (
            inputReader, PMLNodeSpecs.createStandardNodeSpecs(), errorHandler );

        NodeValidator.validateTree ( documentNode, errorHandler );

        convertRootNode ( documentNode, outputWriter, outputTextResource, outputDirectory, options, errorHandler );
    }

    private static void convertRootNode (
        @NotNull DocumentNode documentNode,
        @NotNull Writer HTMLOutputWriter,
        @Nullable TextResource outputTextResource,
        @Nullable Path HTMLOutputDirectory,
        @NotNull PMLToHTMLOptions options,
        @NotNull TextInspectionMessageHandler errorHandler ) throws Exception {

        @Nullable List<Path> resourcesDirectoriesAndFiles = options.resources();
        @Nullable List<Path> CSSDirectoriesAndFiles = options.CSSFiles();
        // TODO checkConsistency ( HTMLOutputDirectory, resourcesDirectoriesAndFiles, CSSDirectoriesAndFiles )

        createOutputDirectory ( HTMLOutputDirectory );

        if ( outputTextResource != null ) {
            SimpleLogger.info ( "Writing HTML output to " + outputTextResource + "." );
        }
        HTMLPageWriter.writePage ( documentNode, HTMLOutputWriter, options );
        HTMLOutputWriter.flush();

        if ( HTMLOutputDirectory != null ) {
            if ( outputTextResource == null ) {
                SimpleLogger.info ( "Updating directory '" + HTMLOutputDirectory + "'." );
            }
            copyFiles ( HTMLOutputDirectory, resourcesDirectoriesAndFiles, CSSDirectoriesAndFiles, errorHandler );
        }

        // se_desktop_utilities.open_file_in_default_browser ( file = HTML_file ) on_error:return_error
        /// TD
        //     if i_config.open_HTML_file then
        //         se_desktop_utilities.open_file_in_default_browser ( file = HTML_file ) on_error:return_error
        //     .
    }

    private static void createOutputDirectory (
        @Nullable Path HTMLOutputDirectory ) throws Exception {

        if ( HTMLOutputDirectory != null ) {
            if ( ! Files.exists ( HTMLOutputDirectory ) ) {
                SimpleLogger.debug ( "Creating directory " + FilePathUtils.makeAbsoluteAndNormalize ( HTMLOutputDirectory ) );
                DirectoryCreator.createWithParents ( HTMLOutputDirectory );
            }
        }
    }

    private static void copyFiles (
        @NotNull Path HTMLOutputDirectory,
        @Nullable List<Path> resourcesDirectoriesAndFiles,
        @Nullable List<Path> CSSDirectoriesAndFiles,
        @NotNull TextInspectionMessageHandler errorHandler ) throws IOException {

        if ( resourcesDirectoriesAndFiles != null ) {
            SimpleLogger.debug ( "Copying " + resourcesDirectoriesAndFiles + " to " + FilePathUtils.makeAbsoluteAndNormalize ( HTMLOutputDirectory ) );
            DirectoryCopier.copyDirectoriesAndFiles (
                resourcesDirectoriesAndFiles, HTMLOutputDirectory, StandardCopyOption.REPLACE_EXISTING );
        }

        if ( CSSDirectoriesAndFiles != null ) {
            Path CSSTargetDirectory = HTMLOutputDirectory.resolve ( CSS_DIRECTORY_NAME );

            if ( ! Files.exists ( CSSTargetDirectory ) ) {
                SimpleLogger.debug ( "Creating directory " + FilePathUtils.makeAbsoluteAndNormalize ( CSSTargetDirectory ) );
                DirectoryCreator.createWithParents ( CSSTargetDirectory );
            }

            SimpleLogger.debug ( "Copying " + CSSDirectoriesAndFiles + " to " + FilePathUtils.makeAbsoluteAndNormalize ( CSSTargetDirectory ) );

            for ( Path directoryOrFile : CSSDirectoriesAndFiles ) {
                if ( Files.isDirectory ( directoryOrFile ) ) {
                    DirectoryCopier.copyDirectoryContent (
                        directoryOrFile, CSSTargetDirectory,
                        PMLToHTMLConverter::isCSSFile,
                        StandardCopyOption.REPLACE_EXISTING );
                } else {
                    if ( Files.exists ( directoryOrFile ) ) {
                        FileCopier.copyFileToExistingDirectory (
                            directoryOrFile, CSSTargetDirectory, StandardCopyOption.REPLACE_EXISTING );
                    } else {
                        errorHandler.handleMessage ( new TextWarning (
                            "File '" + directoryOrFile + "' does not exist.",
                            "FILE_DOES_NOT_EXIST",
                            null ) );
                    }
                }
            }
        }
    }

    public static boolean isCSSFile ( @NotNull Path filePath ) {
        return filePath.toString().toLowerCase().endsWith ( ".css" );
    }

    public static @NotNull Path HTMLOutputFileForPMLInputFile ( @NotNull Path PMLInputFile ) {

        @NotNull String HTMLOutputFileName = FileNameUtils.changeExtension (
            PMLInputFile.getFileName().toString(), "html" );

        return SharedDefaultOptions.OUTPUT_DIRECTORY.resolve ( Path.of ( HTMLOutputFileName) );
    }
}
