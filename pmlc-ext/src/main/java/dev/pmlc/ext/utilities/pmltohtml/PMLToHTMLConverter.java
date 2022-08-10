package dev.pmlc.ext.utilities.pmltohtml;

import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pmlc.core.data.node.validation.NodeValidator;
import dev.pmlc.core.parser.PMLParser;
import dev.pmlc.ext.utilities.pmltohtml.options.PMLToHTMLOptions;
import dev.pmlc.ext.utilities.pmltohtml.options.PMLToHTMLOptionsHelper;
import dev.pmlc.ext.utilities.pmltohtml.writer.HTMLPageWriter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.DebugUtils;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.basics.utilities.directory.DirectoryCopier;
import dev.pp.basics.utilities.directory.DirectoryCreator;
import dev.pp.basics.utilities.file.FilePathUtils;
import dev.pp.basics.utilities.file.TextFileIO;
import dev.pp.basics.utilities.os.OSDirectories;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.text.error.handler.TextErrorHandler;
import dev.pp.text.resource.File_TextResource;
import dev.pp.text.resource.TextResource;
import dev.pp.texttable.writer.pretty.utilities.TextErrorOrWarning_FormWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class PMLToHTMLConverter {

    public static @NotNull String CSS_DIRECTORY_NAME = "css";


    public static record Input (
        @NotNull Reader PMLInputReader,
        @Nullable Path PMLInputFile,
        @Nullable TextResource PMLInputTextResource ) {


        public static void closeIfFileReader ( @Nullable Input input ) {
            if ( input != null ) TextFileIO.closeIfFileReader ( input.PMLInputReader );
        }


        public Input ( @NotNull Path file ) throws IOException {
            this ( TextFileIO.getUTF8FileReader ( file ), file, new File_TextResource ( file ) );
        }

        public Input() {
            this ( OSIO.standardInputUTF8Reader(), null, null );
        }

        public static Input createForFileOrStdin ( @Nullable Path PMLInputFile) throws IOException {

            if ( PMLToHTMLOptionsHelper.isStdinFilePath ( PMLInputFile ) ) {
                return new Input();
            } else {
                assert PMLInputFile != null;
                return new Input ( PMLInputFile );
            }
        }

        public String toString() {

            if ( PMLInputFile != null ) {
                return "file " + FilePathUtils.makeAbsoluteAndNormalize ( PMLInputFile ).toString();
            } else if ( PMLInputReader == OSIO.standardInputUTF8Reader() ) {
                return "STDIN";
            } else {
                return PMLInputReader.toString();
            }
        }

/*
        public void closeIfFileReader() {
            TextFileIO.closeIfFileReader ( PMLInputReader );
        }
*/
    }


    public static record Output (
        @NotNull Writer HTMLOutputWriter,
        @Nullable Path HTMLOutputFile,
        @Nullable Path HTMLOutputDirectory ) {


        public static void closeIfFileWriter ( @Nullable Output output ) {
            if ( output != null ) TextFileIO.closeIfFileWriter ( output.HTMLOutputWriter );
        }


        /*
        public Output ( @NotNull Path file ) throws IOException {
            // this ( TextFileUtils.createDirAndGetUTF8FileWriter ( file ), file, file.getParent() );
            // DirectoryCreator.createWithParentsIfNotExists ( file.getParent() );
            this ( TextFileUtils.getUTF8FileWriter ( file ), file, file.getParent() );
        }

        public Output() {
            this ( OSIO.standardOutputUTF8Writer(), null, null );
        }

         */

        public static Output createForFile ( @NotNull Path file ) throws IOException {

            @Nullable Path directory = file.getParent();
            if ( directory != null )
                DirectoryCreator.createWithParentsIfNotExists ( directory );
            return new Output (
                TextFileIO.getUTF8FileWriter ( file ),
                file,
                directory != null ? directory : OSDirectories.currentWorkingDirectory() );
        }

        public static Output createForStdout() throws IOException {

            return new Output ( OSIO.standardOutputUTF8Writer(), null, null );
        }

        public static Output createForFileOrStdout (
            @Nullable Path HTMLOutputFile,
            @NotNull Input input ) throws IOException {

            if ( HTMLOutputFile == null ) {
                if ( input.PMLInputFile == null ) {
                    return createForStdout();
                } else {
                    HTMLOutputFile = PMLToHTMLOptionsHelper.HTMLOutputFileForPMLInputFile ( input.PMLInputFile );
                    return createForFile ( HTMLOutputFile );
                }
            } else if ( PMLToHTMLOptionsHelper.isStdoutFilePath ( HTMLOutputFile ) ) {
                return createForStdout();
            } else {
                return createForFile ( HTMLOutputFile );
            }
        }

        // public void closeIfFileWriter() { TextFileIO.closeIfFileWriter ( HTMLOutputWriter ); }
    }


    public static void convertFile ( @NotNull Path PMLInputFile ) throws Exception {

        convertFile ( PMLInputFile, PMLToHTMLOptionsHelper.HTMLOutputFileForPMLInputFile ( PMLInputFile ) );
    }

    public static void convertFile (
        @NotNull Path PMLInputFile,
        @NotNull Path HTMLOutputFile ) throws Exception {

        convertFile ( PMLInputFile, HTMLOutputFile,
            PMLToHTMLOptions.builder().build(), TextErrorOrWarning_FormWriter.createLogErrorHandler() );
    }

    public static void convertFile (
        @NotNull Path PMLInputFile,
        @NotNull Path HTMLOutputFile,
        @NotNull PMLToHTMLOptions options,
        @NotNull TextErrorHandler errorHandler ) throws Exception {

        @NotNull Path HTMLOutputDirectory = HTMLOutputFile.getParent();
        DirectoryCreator.createWithParentsIfNotExists ( HTMLOutputDirectory );

        Input input = null;
        Output output = null;
        try {
            input = new Input ( PMLInputFile );
            output = Output.createForFile ( HTMLOutputFile );
            convert ( input, output, options, errorHandler );
        } finally {
            Input.closeIfFileReader ( input );
            Output.closeIfFileWriter ( output );
        }
    }

    public static void convert (
        @NotNull Input input,
        @NotNull Output output,
        @NotNull PMLToHTMLOptions options,
        @NotNull TextErrorHandler errorHandler ) throws Exception {

        SimpleLogger.debug ( "Reading PML input from " + input );

        DocumentNode documentNode = PMLParser.parseReader (
            input.PMLInputReader(), input.PMLInputTextResource(), errorHandler );

        NodeValidator.validateTree ( documentNode, errorHandler );

        convertRootNode ( documentNode, output, options );
    }

    public static void convertRootNode (
        @NotNull DocumentNode documentNode,
        @NotNull Output output,
        @NotNull PMLToHTMLOptions options ) throws Exception {

        convertRootNode ( documentNode, output.HTMLOutputWriter, output.HTMLOutputDirectory, output.HTMLOutputFile, options );
    }

    private static void convertRootNode (
        @NotNull DocumentNode documentNode,
        @NotNull Writer HTMLOutputWriter,
        @Nullable Path HTMLOutputDirectory,
        @Nullable Path HTMLOutputFile,
        @NotNull PMLToHTMLOptions options ) throws Exception {

        @Nullable List<Path> resourcesDirectoriesAndFiles = options.resources();
        @Nullable List<Path> CSSDirectoriesAndFiles = options.CSSFiles();
        // TODO checkConsistency ( HTMLOutputDirectory, resourcesDirectoriesAndFiles, CSSDirectoriesAndFiles )

        if ( HTMLOutputDirectory != null ) {
            if ( ! Files.exists ( HTMLOutputDirectory ) ) {
                SimpleLogger.debug ( "Creating directory " + FilePathUtils.makeAbsoluteAndNormalize ( HTMLOutputDirectory ) );
                DirectoryCreator.createWithParents ( HTMLOutputDirectory );
            }
        }

        if ( HTMLOutputFile != null ) {
            SimpleLogger.info ( "Creating HTML file '" + HTMLOutputFile + "'." );
        }
        HTMLPageWriter.writePage ( documentNode, HTMLOutputWriter, options );
        HTMLOutputWriter.flush();

        if ( HTMLOutputDirectory != null ) {
            if ( HTMLOutputFile == null ) {
                SimpleLogger.info ( "Updating directory '" + HTMLOutputDirectory + "'." );
            }
            copyFiles ( HTMLOutputDirectory, resourcesDirectoriesAndFiles, CSSDirectoriesAndFiles );
        }

        // se_desktop_utilities.open_file_in_default_browser ( file = HTML_file ) on_error:return_error
        /// TD
        //     if i_config.open_HTML_file then
        //         se_desktop_utilities.open_file_in_default_browser ( file = HTML_file ) on_error:return_error
        //     .
    }

    private static void copyFiles (
        @NotNull Path HTMLOutputDirectory,
        @Nullable List<Path> resourcesDirectoriesAndFiles,
        @Nullable List<Path> CSSDirectoriesAndFiles ) throws IOException {

        if ( resourcesDirectoriesAndFiles != null ) {
            SimpleLogger.debug ( "Copying " + resourcesDirectoriesAndFiles + " to " + FilePathUtils.makeAbsoluteAndNormalize ( HTMLOutputDirectory ) );
            DirectoryCopier.copyDirectoriesAndFiles (
                resourcesDirectoriesAndFiles, HTMLOutputDirectory, StandardCopyOption.REPLACE_EXISTING );
        }

        if ( CSSDirectoriesAndFiles != null ) {
            // Path CSSTargetDirectory = Path.of ( HTMLOutputDirectory.toString(), CSS_DIRECTORY_NAME );
            Path CSSTargetDirectory = HTMLOutputDirectory.resolve ( CSS_DIRECTORY_NAME );

            if ( ! Files.exists ( CSSTargetDirectory ) ) {
                SimpleLogger.debug ( "Creating directory " + FilePathUtils.makeAbsoluteAndNormalize ( CSSTargetDirectory ) );
                DirectoryCreator.createWithParents ( CSSTargetDirectory );
            }

            SimpleLogger.debug ( "Copying " + CSSDirectoriesAndFiles + " to " + FilePathUtils.makeAbsoluteAndNormalize ( CSSTargetDirectory ) );
            DirectoryCopier.copyDirectoriesAndFiles (
                CSSDirectoriesAndFiles, CSSTargetDirectory, StandardCopyOption.REPLACE_EXISTING );
        }
    }
}
