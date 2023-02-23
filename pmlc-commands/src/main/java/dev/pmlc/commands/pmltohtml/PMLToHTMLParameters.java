package dev.pmlc.commands.pmltohtml;

import dev.pdml.commands.SharedParameterSpecs;
import dev.pmlc.converter.PMLCResources;
import dev.pmlc.converter.pmltohtml.options.PMLToHTMLDefaultOptions;
import dev.pmlc.converter.pmltohtml.options.PMLToHTMLResources;
import dev.pmlc.data.node.block.TOCPosition;
import dev.pmlc.data.node.block.code.SourceCodeHighlighter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.datatype.nonunion.scalar.impls.Enum.EnumDataType;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableParameterSpecs;
import dev.pp.parameters.parameterspecs.ParameterSpecs;

import java.nio.file.Path;
import java.util.List;

public class PMLToHTMLParameters {

    // TODO improve description (see PML\Converter\work\src\PPL\dev\pml\converter\PAIOM_commands\se_PML_convert_command.ppl)

    public static final @NotNull ParameterSpec<Path> PML_INPUT_FILE = SharedParameterSpecs.positionalInputFileOrNull (
        "PML Input File",
        """
            The path of the PML file to be converted to HTML.
            If this parameter is not specified, or if the value is explicitly set to '""" + PMLToHTMLParametersHelper.STDIN_FILE_NAME +
            "' then the PML input is read from the OS's standard input device.",
        "input/text/article.pml" );

    public static final @NotNull ParameterSpec<Path> HTML_OUTPUT_FILE = SharedParameterSpecs.outputFileOrNull (
        "HTML Output File",
        """
            The path of the HTML file to be created.

            The default value is defined as follows:
            - If the input is a file, then the output will be 'output/{input_file_name}.html' (where {input_file_name} is replaced by the input file's name, without its extension).
            - If the input is read from STDIN, then the output is written to STDOUT.

            If the value of this parameter is explicitly set to '""" + PMLToHTMLParametersHelper.STDOUT_FILE_NAME +
            "', then the output is written to STDOUT",
        "../website/blog/top10-brain-boosters/index.html" );

    public static final @NotNull ParameterSpec<List<Path>> CSS_FILES = new ParameterSpec.Builder<> (
        "CSS_files", CommonDataTypes.DIRECTORY_OR_FILE_PATHS_OR_NULL )
        .alternativeName ( "css" )
        .defaultValue ( PMLToHTMLDefaultOptions.CSS_FILES )
        .documentation ( "CSS Files",
            """
                    This parameter is used to explicitly specify one or more CSS files to be used in the final HTML document.

                    The value is a comma separated list of directories and/or files.
                    If a directory is specified, then all files in the directory (including files in sub-directories) are used.
                    All directory and file paths can be absolute or relative.
                    Each file must be a valid CSS file.

                    By default, file(s) in sub-directory 'config/PML_to_HTML/css' of PMLC's shared data directory are used.
                    If these default files are to be used together with other CSS files specified by this parameter, then the default files location must also explicitly be specified in the parameter""",
            "../../shared/css, css" )
        .build();

    public static final @NotNull ParameterSpec<List<Path>> RESOURCES = new ParameterSpec.Builder<> (
        "resources", CommonDataTypes.DIRECTORY_OR_FILE_PATHS_OR_NULL )
        .alternativeName ( "r" )
        .defaultValue ( PMLToHTMLDefaultOptions.resources() )
        .documentation ( "Resources",
            """
            A comma separated list of resources that will be copied into the output directory.

            Each resource can be a directory or file path. Each path can be absolute or relative.
            If a directory is specified then all files in the directory are used, including files in subdirectories.
            By default directory '"""
            + PMLToHTMLDefaultOptions.RESOURCES_PATH + "' in the current working directory is used.",
            "../../shared/images, resources" )
        .build();

    public static final @NotNull ParameterSpec<Path> HTML_PAGE_START_TEMPLATE_FILE = new ParameterSpec.Builder<> (
        "HTML_page_header", CommonDataTypes.FILE_PATH_OR_NULL )
        .alternativeName ( "ph" )
        .defaultValue ( PMLToHTMLResources.HTML_PAGE_START_TEMPLATE_FILE )
        .documentation ( "HTML Header Template File",
            """
                The path of the HTML header template file.
                The path can be absolute or relative""",
            "../../shared/HTML_header.txt" )
        .build();

    public static final @NotNull ParameterSpec<Path> HTML_PAGE_END_TEMPLATE_FILE = new ParameterSpec.Builder<> (
        "HTML_page_footer", CommonDataTypes.FILE_PATH_OR_NULL )
        .alternativeName ( "pf" )
        .defaultValue ( PMLToHTMLResources.HTML_PAGE_END_TEMPLATE_FILE )
        .documentation ( "HTML Footer Template File",
            """
                The path of the HTML footer template file.
                The path can be absolute or relative""",
            "../../shared/HTML_footer.txt" )
        .build();

    public static final @NotNull ParameterSpec<String> TOC_TITLE = new ParameterSpec.Builder<> (
        "TOC_title", CommonDataTypes.STRING_OR_NULL )
        .alternativeName ( "tt" )
        .defaultValue ( PMLToHTMLDefaultOptions.TOC_TITLE )
        .documentation ( "Table of Contents Title",
            "The title of the table of contents.",
            "Contents" )
        .build();

    public static final @NotNull ParameterSpec<Integer> MAX_TOC_CHAPTER_LEVEL = new ParameterSpec.Builder<> (
        "TOC_max_level", CommonDataTypes.INTEGER_32 )
        .alternativeName ( "tm" )
        .defaultValue ( PMLToHTMLDefaultOptions.MAX_TOC_CHAPTER_LEVEL )
        .documentation ( "Table of Contents Max Level",
            "The maximum chapter level that is included in the table of contents.",
            "4" )
        .build();

    public static final ParameterSpec<Boolean> OMIT_CSS = new ParameterSpec.Builder<> (
        "omit_CSS", CommonDataTypes.BOOLEAN )
        .alternativeName ( "oc" )
        .defaultValue ( PMLToHTMLDefaultOptions.OMIT_CSS )
        .documentation ( "Omit CSS",
            "If set to yes (true), CSS classes are omitted in the resulting HTML.",
            "yes" )
        .build();

    private static final @NotNull EnumDataType<TOCPosition> TOC_POSITION_TYPE = new EnumDataType<> ( TOCPosition.class );

    public static final @NotNull ParameterSpec<TOCPosition> TOC_POSITION = new ParameterSpec.Builder<> (
        "TOC_position", TOC_POSITION_TYPE )
        .alternativeName ( "tp" )
        .defaultValue ( PMLToHTMLDefaultOptions.TOC_POSITION )
        .documentation ( "Table of Contents Position",
            """
                The position of the table of contents.
                Valid values are: """ +
                TOC_POSITION_TYPE.validValuesAsString().toLowerCase(),
            TOCPosition.LEFT.toString().toLowerCase() )
        .build();

    private static final @NotNull EnumDataType<SourceCodeHighlighter> SOURCE_CODE_HIGHLIGHTER_TYPE =
        new EnumDataType<> ( SourceCodeHighlighter.class );

    public static final @NotNull ParameterSpec<SourceCodeHighlighter> SOURCE_CODE_HIGHLIGHTER = new ParameterSpec.Builder<> (
        "highlighter", SOURCE_CODE_HIGHLIGHTER_TYPE )
        .alternativeName ( "hi" )
        .defaultValue ( PMLToHTMLDefaultOptions.SOURCE_CODE_HIGHLIGHTER )
        .documentation ( "Source Code Highlighter",
            """
                The name of the highlighter used to highlight source code.
                Valid values are: """ +
                SOURCE_CODE_HIGHLIGHTER_TYPE.validValuesAsString().toLowerCase(),
            SourceCodeHighlighter.NONE.toString().toLowerCase() )
        .build();

    public static final @NotNull ParameterSpec<SimpleLogger.LogLevel> VERBOSITY = SharedParameterSpecs.VERBOSITY;

    public static final @NotNull ParameterSpec<String> OPEN_FILE_OS_COMMAND_TEMPLATE = SharedParameterSpecs.OPEN_FILE_OS_COMMAND_TEMPLATE;

    @SuppressWarnings ( "rawtypes" )
    public static final ParameterSpecs SPECS = create();

    @SuppressWarnings ( {"unchecked", "rawtypes"} )
    private static ParameterSpecs<?> create() {

        MutableParameterSpecs parameters = new MutableParameterSpecs();
        parameters
            .add ( PML_INPUT_FILE )
            .add ( HTML_OUTPUT_FILE )
            .add ( CSS_FILES )
            .add ( RESOURCES )
            .add ( HTML_PAGE_START_TEMPLATE_FILE )
            .add ( HTML_PAGE_END_TEMPLATE_FILE )
            .add ( TOC_POSITION )
            .add ( TOC_TITLE )
            .add ( MAX_TOC_CHAPTER_LEVEL )
            .add ( SOURCE_CODE_HIGHLIGHTER )
            .add ( OMIT_CSS )
            .add ( VERBOSITY )
            .add ( OPEN_FILE_OS_COMMAND_TEMPLATE );
        return parameters.makeImmutable();
    }
}
