package dev.pmlc.commands.pmltohtml;

import dev.pdml.ext.commands.SharedDefaultOptions;
import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pmlc.core.data.node.validation.NodeValidator;
import dev.pmlc.core.parser.PMLParser;
import dev.pmlc.ext.utilities.pmltohtml.PMLToHTMLConverter;
import dev.pmlc.ext.utilities.pmltohtml.options.PMLToHTMLOptions;
import dev.pmlc.ext.utilities.pmltohtml.options.PMLToHTMLOptionsHelper;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.commands.command.CommandExecutor;
import dev.pp.commands.command.FormalCommand;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.parameter.list.Parameters;
import dev.pp.parameters.utilities.TextErrorUtils2;
import dev.pp.text.error.handler.TextErrorHandler;
import dev.pp.text.error.TextErrorException;

import java.nio.file.Path;
import java.util.Map;

public class PMLToHTMLCommand {

    public static final @NotNull FormalCommand<Void> COMMAND = FormalCommand.builder (
            "PML_to_HTML", new Executor() )
        .alternativeName ( "p2h" )
        .inputParameters ( PMLToHTMLFormalCLIParameters.PARAMETERS )
        .documentation (
            "Convert PML to HTML",
            "Convert a PML document into an HTML document.",
            """
                pmlc p2h article.pml
                pmlc p2h --output ../website/docs/about/index.html about.pml""" )
        .build();


    private static class Executor implements CommandExecutor<Void> {

        Executor() {}

/*
        @Override
        public Void execute (
            @Nullable Map<String, String> CLIParameterStrings,
            @Nullable FormalParameters formalParameters ) throws Exception {

            // @Nullable String inputFileCLIParam =
            //     CLIParameters == null ? null : CLIParameters.get ( PMLToHTMLFormalParameters.PML_INPUT_FILE.getName() );
            // // TODO handle InvalidPathException
            // @Nullable Path PMLInputFile = inputFileCLIParam == null ? null : Path.of ( inputFileCLIParam );

            assert CLIParameterStrings != null; // TODO sure? create empty if null?
            @Nullable Parameters CLIParameters = ParametersCreator.createFromStringMap (
                CLIParameterStrings, null, PMLToHTMLFormalCLIParameters.PARAMETERS );
            assert CLIParameters != null;

            @NotNull SimpleLogger.LogLevel logLevel = CLIParameters.getNonNull ( PMLToHTMLFormalCLIParameters.VERBOSITY );
            SimpleLogger.setLevel ( logLevel );

            @Nullable Path PMLInputFile = CLIParameters.getNullable ( PMLToHTMLFormalCLIParameters.PML_INPUT_FILE );
            @Nullable PMLToHTMLConverter.Input input = null;
            @NotNull DocumentNode documentNode;
            try {
                TextErrorHandler errorHandler = SharedDefaultOptions.createErrorHandler ();
                try {
                    input = PMLToHTMLConverter.Input.createForFileOrStdin ( PMLInputFile );
                    documentNode = PMLParser.parseReader (
                        input.PMLInputReader (), input.PMLInputTextResource (), errorHandler );
                } finally {
                    if ( input != null ) input.closeIfFileReader ();
                }

                NodeValidator.validateTree ( documentNode, errorHandler );

                convertDocumentNode ( documentNode, CLIParameterStrings, CLIParameters, input );

            } catch ( TextErrorException e ) {
                TextErrorUtils2.showInEditor (
                    e.getTextError (), CLIParameters, PMLToHTMLFormalCLIParameters.OPEN_FILE_OS_COMMAND_TEMPLATE );
                throw e;
            }

            // if ( 1 == 1 ) throw new RuntimeException ( "QQQ" );
            // if ( 1 == 1 ) throw new Exception ( "EEE" );

            return null;
        }
*/

        public Void execute (
            @Nullable Map<String, String> stringParameters,
            @Nullable Parameters parameters ) throws Exception {

            assert parameters != null;
            // TODO
            // PMLToHTMLConverter.convertParameters ( parameters );

            @NotNull SimpleLogger.LogLevel logLevel = parameters.getNonNull ( PMLToHTMLFormalCLIParameters.VERBOSITY );
            SimpleLogger.setLevel ( logLevel );

            TextErrorHandler errorHandler = SharedDefaultOptions.createErrorHandler();

            @Nullable Path PMLInputFile = parameters.getNullable ( PMLToHTMLFormalCLIParameters.PML_INPUT_FILE );
            @Nullable PMLToHTMLConverter.Input input = null;
            @NotNull DocumentNode documentNode;
            try {
                try {
                    input = PMLToHTMLConverter.Input.createForFileOrStdin ( PMLInputFile );
                    documentNode = PMLParser.parseReader (
                        input.PMLInputReader(), input.PMLInputTextResource(), errorHandler );
                } finally {
                    PMLToHTMLConverter.Input.closeIfFileReader ( input );
                }

                NodeValidator.validateTree ( documentNode, errorHandler );

                convertDocumentNode ( documentNode, stringParameters, parameters, input );

            } catch ( TextErrorException e ) {
                TextErrorUtils2.showInEditor (
                    e.getTextError(), parameters, PMLToHTMLFormalCLIParameters.OPEN_FILE_OS_COMMAND_TEMPLATE );
                throw e;
            }

            return null;
        }
    }

    private static void convertDocumentNode  (
        @NotNull DocumentNode documentNode,
        @Nullable Map<String, String> CLIParameterStrings,
        @NotNull Parameters CLIParameters,
        @NotNull PMLToHTMLConverter.Input input ) throws Exception {

        @Nullable Path HTMLOutputFile = CLIParameters.getNullable ( PMLToHTMLFormalCLIParameters.HTML_OUTPUT_FILE );

        // remove all CLI specific parameters that are not PMLToHTML options.
        if ( CLIParameterStrings != null ) {
            for ( FormalParameter<?> formalParameter : PMLToHTMLFormalCLIParameters.CLI_SPECIFIC_PARAMETERS.getAll() ) {
                CLIParameterStrings.remove ( formalParameter.getName() );
            }
        }

        PMLToHTMLOptions options = PMLToHTMLOptionsHelper.createMergedOptions ( CLIParameterStrings, documentNode );

        @Nullable PMLToHTMLConverter.Output output = null;
        try {
            output = PMLToHTMLConverter.Output.createForFileOrStdout ( HTMLOutputFile, input );
            PMLToHTMLConverter.convertRootNode ( documentNode, output, options );
        } finally {
            PMLToHTMLConverter.Output.closeIfFileWriter ( output );
        }
    }
}
