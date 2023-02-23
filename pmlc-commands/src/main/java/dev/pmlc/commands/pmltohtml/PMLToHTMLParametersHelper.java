package dev.pmlc.commands.pmltohtml;

import dev.pmlc.converter.pmltohtml.options.PMLToHTMLOptions;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.ParameterValueGetter;
import dev.pp.parameters.parameters.Parameters;

import java.io.IOException;
import java.nio.file.Path;

public class PMLToHTMLParametersHelper {

    public static final @NotNull String STDIN_FILE_NAME = "stdin";
    public static final @NotNull String STDOUT_FILE_NAME = "stdout";

    public static boolean isStdinFilePath ( @Nullable Path filePath ) {
        return filePath == null || filePath.toString().equalsIgnoreCase ( STDIN_FILE_NAME );
    }

    public static boolean isStdoutFilePath ( @Nullable Path filePath ) {
        return filePath == null || filePath.toString().equalsIgnoreCase ( STDOUT_FILE_NAME );
    }

    public static @NotNull PMLToHTMLOptions createOptions (
        @NotNull Parameters<?> parameters ) throws IOException {

        ParameterValueGetter vg = parameters.valueGetter();
        return PMLToHTMLOptions.builder()
            .CSSFiles ( vg.nullable ( PMLToHTMLParameters.CSS_FILES ) )
            .resources ( vg.nullable ( PMLToHTMLParameters.RESOURCES ) )
            .HTMLPageStartTemplate ( vg.nullable ( PMLToHTMLParameters.HTML_PAGE_START_TEMPLATE_FILE ) )
            .HTMLPageEndTemplate ( vg.nullable ( PMLToHTMLParameters.HTML_PAGE_END_TEMPLATE_FILE ) )
            .TOCPosition ( vg.nonNull ( PMLToHTMLParameters.TOC_POSITION ) )
            .TOCTitle ( vg.nullable ( PMLToHTMLParameters.TOC_TITLE ) )
            .maxTOCChapterLevel ( vg.nonNull ( PMLToHTMLParameters.MAX_TOC_CHAPTER_LEVEL ) )
            .sourceCodeHighlighter ( vg.nonNull ( PMLToHTMLParameters.SOURCE_CODE_HIGHLIGHTER ) )
            .omitCSS ( vg.nonNull ( PMLToHTMLParameters.OMIT_CSS ) )
            .build();
    }
}
