package dev.pmlc.commands.pmltohtml;

import dev.pdml.utils.SharedDefaultOptions;
import dev.pmlc.converter.pmltohtml.PMLToHTMLConverter;
import dev.pmlc.converter.pmltohtml.options.PMLToHTMLOptions;
import dev.pmlc.converter.pmltohtml.options.PMLToHTMLResources;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.basics.utilities.directory.DirectoryCreator;
import dev.pp.commands.command.CommandExecutor;
import dev.pp.commands.command.CommandSpec;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.parameters.parameter.Parameter;
import dev.pp.parameters.parameters.MutableOrImmutableParameters;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.parameters.utilities.TextErrorUtils2;
import dev.pp.text.inspection.TextErrorException;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.reader.stack.CharReaderWithInserts;
import dev.pp.text.reader.stack.CharReaderWithInsertsImpl;
import dev.pp.text.resource.reader.TextResourceReader;
import dev.pp.text.resource.writer.TextResourceWriter;

import java.io.IOException;
import java.nio.file.Path;

public class PMLToHTMLCommand {

    @SuppressWarnings ( "unchecked" )
    public static final @NotNull CommandSpec<?,Void> COMMAND_SPEC = CommandSpec.builder (
        "PML_to_HTML", new Executor() )
        .alternativeName ( "p2h" )
        .inputParameters ( PMLToHTMLParameters.SPECS )
        .documentation (
            "Convert PML to HTML",
            "Convert a PML document into an HTML document.",
            """
                pmlc p2h article.pml
                pmlc p2h --output ../website/docs/about/index.html about.pml""" )
        .build();


    public static @NotNull TextResourceWriter HtmlOutputForFile ( @NotNull Path file ) throws IOException {

        @Nullable Path directory = file.getParent();
        if ( directory != null ) DirectoryCreator.createWithParentsIfNotExists ( directory );
        return new TextResourceWriter ( file );
    }

    private static class Executor<I> implements CommandExecutor<I,Void> {

        Executor() {}

        @Override
        public Void execute (
            @Nullable MutableOrImmutableParameters<String> stringParameters,
            @Nullable ParameterSpecs<I> parameterSpecs ) throws Exception {

            @Nullable Parameters<?> mergedParameters = null;

            try ( TextResourceReader PMLInput = PMLInput ( stringParameters ) ) {

                PMLToHTMLResources.createResourceFilesIfNotExists();

                CharReaderWithInserts documentReader = CharReaderWithInsertsImpl.createAndAdvance (
                    PMLInput.getReader(), PMLInput.getTextResource(), null, null );
                mergedParameters = PMLToHTMLParametersMerger.createMergedParameters (
                    stringParameters, documentReader );
                PMLToHTMLOptions mergedOptions = PMLToHTMLParametersHelper.createOptions ( mergedParameters );

                try ( TextResourceWriter HtmlOutput = HtmlOutput ( stringParameters, PMLInput ) ) {

                    @NotNull SimpleLogger.LogLevel logLevel = mergedParameters.nonNullCastedValue (
                        PMLToHTMLParameters.VERBOSITY );
                    SimpleLogger.setLevel ( logLevel );

                    TextInspectionMessageHandler errorHandler = SharedDefaultOptions.createErrorHandler();

                    @Nullable Path outputFile = HtmlOutput.getResourceAsFilePath();
                    @Nullable Path outputDirectory = outputFile == null ? null : outputFile.getParent();

                    PMLToHTMLConverter.convert (
                        documentReader,
                        HtmlOutput.getWriter(), HtmlOutput.getTextResource(),
                        outputDirectory, mergedOptions, errorHandler );
                }
            } catch ( TextErrorException e ) {
                if ( mergedParameters != null ) {
                    TextErrorUtils2.showInEditor (
                        e.getTextError (), mergedParameters, PMLToHTMLParameters.OPEN_FILE_OS_COMMAND_TEMPLATE );
                }
                throw e;
            }

            return null;
        }

        @Override
        public Void execute ( @Nullable Parameters<I> parameters ) throws Exception {
            throw new UnsupportedOperationException ( "Should never be called." );
        }
    }


    private static @NotNull TextResourceReader PMLInput ( @Nullable MutableOrImmutableParameters<String> stringParameters )
        throws IOException, DataParserException, DataValidatorException {

        @Nullable Parameter<String> PMLInputFileParameter = stringParameters == null ? null :
            stringParameters.parameterOrNull ( PMLToHTMLParameters.PML_INPUT_FILE );

        @Nullable Path PMLInputFile = PMLInputFileParameter == null
            ? null
            : PMLToHTMLParameters.PML_INPUT_FILE.parse (
            PMLInputFileParameter.getValue(), PMLInputFileParameter.valueOrElseNameLocation() );

        return PMLInput ( PMLInputFile );
    }

    private static @NotNull TextResourceReader PMLInput ( @Nullable Path PMLInputFile ) throws IOException {

        if ( PMLToHTMLParametersHelper.isStdinFilePath ( PMLInputFile ) ) {
            return TextResourceReader.STDIN;
        } else {
            return new TextResourceReader ( PMLInputFile );
        }
    }

    private static @NotNull TextResourceWriter HtmlOutput (
        @Nullable MutableOrImmutableParameters<String> stringParameters,
        @NotNull TextResourceReader PMLInput ) throws IOException, DataParserException, DataValidatorException {

        @Nullable Parameter<String> HtmlOutputFileParameter = stringParameters == null ? null :
            stringParameters.parameterOrNull ( PMLToHTMLParameters.HTML_OUTPUT_FILE );

        @Nullable Path HtmlOutputFile = HtmlOutputFileParameter == null
            ? null
            : PMLToHTMLParameters.HTML_OUTPUT_FILE.parse (
            HtmlOutputFileParameter.getValue(), HtmlOutputFileParameter.valueOrElseNameLocation() );

        return HtmlOutput ( HtmlOutputFile, PMLInput );
    }

    private static @NotNull TextResourceWriter HtmlOutput (
        @Nullable Path HtmlOutputFile,
        @NotNull TextResourceReader PMLInput ) throws IOException {

        if ( HtmlOutputFile == null ) {
            @Nullable Path PMLInputFile = PMLInput.getResourceAsFilePath();
            if ( PMLInputFile == null ) {
                return TextResourceWriter.STDOUT;
            } else {
                HtmlOutputFile = PMLToHTMLConverter.HTMLOutputFileForPMLInputFile ( PMLInputFile );
                return HtmlOutputForFile ( HtmlOutputFile );
            }
        } else if ( PMLToHTMLParametersHelper.isStdoutFilePath ( HtmlOutputFile ) ) {
            return TextResourceWriter.STDOUT;
        } else {
            return HtmlOutputForFile ( HtmlOutputFile );
        }
    }
}
