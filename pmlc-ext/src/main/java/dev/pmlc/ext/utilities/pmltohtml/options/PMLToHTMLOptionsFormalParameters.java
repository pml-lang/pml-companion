package dev.pmlc.ext.utilities.pmltohtml.options;

import dev.pmlc.core.data.node.block.code.SourceCodeHighlighter;
import dev.pmlc.core.data.node.block.TOCPosition;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.datatype.nonUnion.scalar.impls.Enum.EnumDataType;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.list.FormalParameters;

import java.nio.file.Path;
import java.util.List;

public class PMLToHTMLOptionsFormalParameters {

    // TODO improve description (see PML\Converter\work\src\PPL\dev\pml\converter\PAIOM_commands\se_PML_convert_command.ppl)

    public static final @NotNull FormalParameter<List<Path>> CSS_FILES = new FormalParameter.Builder<> (
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

    public static final @NotNull FormalParameter<List<Path>> RESOURCES = new FormalParameter.Builder<> (
        "resources", CommonDataTypes.DIRECTORY_OR_FILE_PATHS_OR_NULL )
        .alternativeName ( "r" )
        .defaultValue ( PMLToHTMLDefaultOptions.resources() )
        .documentation ( "Resources",
            """
            A comma separated list of resources that will be copied into the output directory.

            Each resource can be a directory or file path. Each path can be absolute or relative.
            If a directory is specified then all files in the directory are used, including files in subdirectories.
            By default directory '""" + PMLToHTMLDefaultOptions.RESOURCES_PATH + """
                ' in the current working directory is used.""",
            "../../shared/images, resources" )
        .build();

    public static final @NotNull FormalParameter<Path> HTML_PAGE_START_TEMPLATE_FILE = new FormalParameter.Builder<> (
        "HTML_page_header", CommonDataTypes.FILE_PATH_OR_NULL )
        .alternativeName ( "ph" )
        .defaultValue ( PMLToHTMLDefaultOptions.HTML_PAGE_START_TEMPLATE_FILE )
        .documentation ( "HTML Header Template File",
            """
                The path of the HTML header template file.
                The path can be absolute or relative""",
            "../../shared/HTML_header.txt" )
        .build();

    public static final @NotNull FormalParameter<Path> HTML_PAGE_END_TEMPLATE_FILE = new FormalParameter.Builder<> (
        "HTML_page_footer", CommonDataTypes.FILE_PATH_OR_NULL )
        .alternativeName ( "pf" )
        .defaultValue ( PMLToHTMLDefaultOptions.HTML_PAGE_END_TEMPLATE_FILE )
        .documentation ( "HTML Footer Template File",
            """
                The path of the HTML footer template file.
                The path can be absolute or relative""",
            "../../shared/HTML_footer.txt" )
        .build();

    private static final @NotNull EnumDataType<TOCPosition> TOC_POSITION_TYPE = new EnumDataType<> ( TOCPosition.class );

    public static final @NotNull FormalParameter<TOCPosition> TOC_POSITION = new FormalParameter.Builder<> (
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

    public static final @NotNull FormalParameter<String> TOC_TITLE = new FormalParameter.Builder<> (
        "TOC_title", CommonDataTypes.STRING_OR_NULL )
        .alternativeName ( "tt" )
        .defaultValue ( PMLToHTMLDefaultOptions.TOC_TITLE )
        .documentation ( "Table of Contents Title",
            "The title of the table of contents.",
            "Contents" )
        .build();

    public static final @NotNull FormalParameter<Integer> MAX_TOC_CHAPTER_LEVEL = new FormalParameter.Builder<> (
        "TOC_max_level", CommonDataTypes.INTEGER_32 )
        .alternativeName ( "tm" )
        .defaultValue ( PMLToHTMLDefaultOptions.MAX_TOC_CHAPTER_LEVEL )
        .documentation ( "Table of Contents Max Level",
            "The maximum chapter level that is included in the table of contents.",
            "4" )
        .build();


    private static final @NotNull EnumDataType<SourceCodeHighlighter> SOURCE_CODE_HIGHLIGHTER_TYPE =
        new EnumDataType<> ( SourceCodeHighlighter.class );

    public static final @NotNull FormalParameter<SourceCodeHighlighter> SOURCE_CODE_HIGHLIGHTER = new FormalParameter.Builder<> (
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

    public static final FormalParameter<Boolean> OMIT_CSS = new FormalParameter.Builder<> (
        "omit_CSS", CommonDataTypes.BOOLEAN )
        .alternativeName ( "oc" )
        .defaultValue ( PMLToHTMLDefaultOptions.OMIT_CSS )
        .documentation ( "Omit CSS",
            "If set to yes (true), CSS classes are omitted in the resulting HTML.",
            "yes" )
        .build();

    public static final FormalParameters PARAMETERS = create();

    private static FormalParameters create() {

        FormalParameters parameters = new FormalParameters();
        addAll ( parameters );
        return parameters;
    }

    public static void addAll ( FormalParameters formalParameters ) {

        formalParameters
            .add ( CSS_FILES )
            .add ( RESOURCES )
            .add ( HTML_PAGE_START_TEMPLATE_FILE )
            .add ( HTML_PAGE_END_TEMPLATE_FILE )
            .add ( TOC_POSITION )
            .add ( TOC_TITLE )
            .add ( MAX_TOC_CHAPTER_LEVEL )
            .add ( SOURCE_CODE_HIGHLIGHTER )
            .add ( OMIT_CSS );
    }
}
