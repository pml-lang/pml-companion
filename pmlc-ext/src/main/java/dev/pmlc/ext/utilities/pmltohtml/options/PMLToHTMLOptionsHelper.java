package dev.pmlc.ext.utilities.pmltohtml.options;

import dev.pdml.ext.commands.SharedDefaultOptions;
import dev.pdml.ext.utilities.parser.TextTokenParametersPDMLParser;
import dev.pmlc.core.data.node.block.OptionsNode;
import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pmlc.ext.PMLCResources;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.FileNameUtils;
import dev.pp.parameters.parameter.Parameters;
import dev.pp.parameters.parameter.ParametersCreator;
import dev.pp.parameters.textTokenParameter.TextTokenParameters;
import dev.pp.text.error.TextErrorException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PMLToHTMLOptionsHelper {

    public static final @NotNull String STDIN_FILE_NAME = "stdin";
    public static final @NotNull String STDOUT_FILE_NAME = "stdout";

    public static boolean isStdinFilePath ( @Nullable String filePath ) {

        return filePath == null || filePath.equalsIgnoreCase ( STDIN_FILE_NAME );
    }

    public static boolean isStdinFilePath ( @Nullable Path filePath ) {

        return isStdinFilePath ( filePath == null ? null : filePath.toString() );
    }

    public static boolean isStdoutFilePath ( @Nullable String filePath ) {

        return filePath == null || filePath.equalsIgnoreCase ( STDOUT_FILE_NAME );
    }

    public static boolean isStdoutFilePath ( @Nullable Path filePath ) {

        return isStdoutFilePath ( filePath == null ? null : filePath.toString() );
    }

    public static @NotNull PMLToHTMLOptions createMergedOptions (
        @Nullable Map<String, String> CLIParameters,
        @NotNull DocumentNode documentNode ) throws TextErrorException, IOException {

        List<TextTokenParameters> parametersList = new ArrayList<>();

        @Nullable TextTokenParameters sharedParameters = readSharedOptionsFile();
        if ( sharedParameters != null ) {
            parametersList.add ( sharedParameters );
        }

        @Nullable List<OptionsNode> documentOptionsNodes = documentNode.getOptionsNodes ();
        if ( documentOptionsNodes != null ) {
            for ( OptionsNode configNode : documentOptionsNodes ) {
                parametersList.add ( configNode.getParameters() );
            }
        }

        if ( CLIParameters != null ) {
            parametersList.add ( new TextTokenParameters ( CLIParameters, null ) );
        }

        TextTokenParameters mergedTextTokenParameters =
            TextTokenParameters.merge ( parametersList, PMLToHTMLOptionsFormalParameters.PARAMETERS );

        Parameters parameters = ParametersCreator.createFromTextParameters (
            mergedTextTokenParameters, null, PMLToHTMLOptionsFormalParameters.PARAMETERS );
        assert parameters != null;

        return createOptions ( parameters );
    }

    private static @Nullable TextTokenParameters readSharedOptionsFile () throws TextErrorException, IOException {

        Path path = PMLCResources.ROOT_DIRECTORY.resolve ( PMLToHTMLResources.REL_OPTIONS_FILE );
        if ( ! Files.exists ( path) ) return null;

        return TextTokenParametersPDMLParser.parseFile ( path, true );
    }

    public static @NotNull PMLToHTMLOptions createOptions (
        @NotNull Parameters parameters ) throws IOException {

        return PMLToHTMLOptions.builder()
            .CSSFiles ( parameters.getNullable ( PMLToHTMLOptionsFormalParameters.CSS_FILES ) )
            .resources ( parameters.getNullable ( PMLToHTMLOptionsFormalParameters.RESOURCES ) )
            .HTMLPageStartTemplate ( (Path) parameters.getNullable ( PMLToHTMLOptionsFormalParameters.HTML_PAGE_START_TEMPLATE_FILE ) )
            .HTMLPageEndTemplate ( (Path) parameters.getNullable ( PMLToHTMLOptionsFormalParameters.HTML_PAGE_END_TEMPLATE_FILE ) )
            .TOCPosition ( parameters.getNonNull ( PMLToHTMLOptionsFormalParameters.TOC_POSITION ) )
            .TOCTitle ( parameters.getNullable ( PMLToHTMLOptionsFormalParameters.TOC_TITLE ) )
            .maxTOCChapterLevel ( parameters.getNonNull ( PMLToHTMLOptionsFormalParameters.MAX_TOC_CHAPTER_LEVEL ) )
            .sourceCodeHighlighter ( parameters.getNonNull ( PMLToHTMLOptionsFormalParameters.SOURCE_CODE_HIGHLIGHTER ) )
            .omitCSS ( parameters.getNonNull ( PMLToHTMLOptionsFormalParameters.OMIT_CSS ) )
            .build();
    }

    public static @NotNull Path HTMLOutputFileForPMLInputFile ( @NotNull Path PMLInputFile ) {

        @NotNull String HTMLOutputFileName = FileNameUtils.changeExtension (
            PMLInputFile.getFileName().toString(), "html" );

        return SharedDefaultOptions.OUTPUT_DIRECTORY.resolve ( Path.of ( HTMLOutputFileName) );
    }
}
