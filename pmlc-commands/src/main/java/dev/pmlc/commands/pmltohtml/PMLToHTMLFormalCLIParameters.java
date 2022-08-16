package dev.pmlc.commands.pmltohtml;

import dev.pdml.commands.SharedFormalParameters;
import dev.pmlc.ext.utilities.pmltohtml.options.PMLToHTMLOptionsFormalParameters;
import dev.pmlc.ext.utilities.pmltohtml.options.PMLToHTMLOptionsHelper;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.FormalParameters;

import java.nio.file.Path;

public class PMLToHTMLFormalCLIParameters {

    // TODO improve description (see PML\Converter\work\src\PPL\dev\pml\converter\PAIOM_commands\se_PML_convert_command.ppl)

    public static final @NotNull FormalParameter<Path> PML_INPUT_FILE = SharedFormalParameters.positionalInputFileOrNull (
        "PML Input File",
        """
            The path of the PML file to be converted to HTML.
            If this parameter is not specified, or if the value is explicitly set to '""" + PMLToHTMLOptionsHelper.STDIN_FILE_NAME + """
            ' then the PML input is read from the OS's standard input device.""",
        "input/text/article.pml" );

    public static final @NotNull FormalParameter<Path> HTML_OUTPUT_FILE = SharedFormalParameters.outputFileOrNull (
        "HTML Output File",
        """
            The path of the HTML file to be created.

            The default value is defined as follows:
            - If the input is a file, then the output will be 'output/{input_file_name}.html' (where {input_file_name} is replaced by the input file's name, without its extension).
            - If the input is read from STDIN, then the output is written to STDOUT.

            If the value of this parameter is explicitly set to '""" + PMLToHTMLOptionsHelper.STDOUT_FILE_NAME + """
            ', then the output is written to STDOUT""",
        "../website/blog/top10-brain-boosters/index.html" );

    public static final @NotNull FormalParameter<SimpleLogger.LogLevel> VERBOSITY = SharedFormalParameters.VERBOSITY;

    public static final @NotNull FormalParameter<String> OPEN_FILE_OS_COMMAND_TEMPLATE = SharedFormalParameters.OPEN_FILE_OS_COMMAND_TEMPLATE;

    public static final FormalParameters PARAMETERS = create();

    private static FormalParameters create() {

        FormalParameters parameters = new FormalParameters();
        addCLISpecific ( parameters );
        PMLToHTMLOptionsFormalParameters.addAll ( parameters );
        return parameters;
    }

    public static final FormalParameters CLI_SPECIFIC_PARAMETERS = createCLISpecific ();

    private static FormalParameters createCLISpecific () {

        FormalParameters parameters = new FormalParameters();
        addCLISpecific ( parameters );
        return parameters;
    }

    private static void addCLISpecific ( FormalParameters formalParameters ) {

        formalParameters
            .add ( PML_INPUT_FILE )
            .add ( HTML_OUTPUT_FILE )
            .add ( VERBOSITY )
            .add ( OPEN_FILE_OS_COMMAND_TEMPLATE );
    }
}
