package dev.pmlc.ext.utilities.pmltohtml.options;

import dev.pmlc.core.data.node.block.code.SourceCodeHighlighter;
import dev.pmlc.core.data.node.block.TOCPosition;
import dev.pmlc.ext.PMLCResources;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.TextFileIO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

public class PMLToHTMLDefaultOptions {

    public static final @NotNull Path DEFAULT_CSS_FILE = resourceFile ( PMLToHTMLResources.REL_DEFAULT_CSS_FILE );

    public static final @NotNull Path PRINT_DEFAULT_CSS_FILE = resourceFile ( PMLToHTMLResources.REL_PRINT_DEFAULT_CSS_FILE );

    public static final @NotNull List<Path> CSS_FILES = List.of (
        DEFAULT_CSS_FILE,
        PRINT_DEFAULT_CSS_FILE );

    public static final @NotNull Path RESOURCES_PATH = Path.of ( "resources" );

    public static @Nullable List<Path> resources () {

        if ( RESOURCES_PATH.toFile ().exists () ) {
            return List.of ( RESOURCES_PATH );
        } else {
            return null;
        }
    }

    public static final @NotNull Path HTML_PAGE_START_TEMPLATE_FILE =
        resourceFile ( PMLToHTMLResources.REL_HTML_PAGE_START_TEMPLATE_FILE );

    public static final @NotNull Supplier<String> HTML_PAGE_START_TEMPLATE =
        throwableStringSupplierForFile ( HTML_PAGE_START_TEMPLATE_FILE );

    public static final @NotNull Path HTML_PAGE_END_TEMPLATE_FILE =
        resourceFile ( PMLToHTMLResources.REL_HTML_PAGE_END_TEMPLATE_FILE );

    public static final @NotNull Supplier<String> HTML_PAGE_END_TEMPLATE =
        throwableStringSupplierForFile ( HTML_PAGE_END_TEMPLATE_FILE );

    public static final @NotNull TOCPosition TOC_POSITION = TOCPosition.LEFT;

    public static final @Nullable String TOC_TITLE = "Table of Contents";

    public static final int MAX_TOC_CHAPTER_LEVEL = 100;

    public static final @NotNull SourceCodeHighlighter SOURCE_CODE_HIGHLIGHTER = SourceCodeHighlighter.NONE;

    public static final boolean OMIT_CSS = false;


    private static @NotNull Path resourceFile ( @NotNull Path relativePath ) {

        return Path.of ( PMLCResources.ROOT_DIRECTORY.toString(), relativePath.toString() );
    }

    private static @NotNull Supplier<String> throwableStringSupplierForFile ( @NotNull Path path ) {
        try {
            return stringSupplierForFile ( path );
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }

    private static @NotNull Supplier<String> stringSupplierForFile ( @NotNull Path path ) throws IOException {

        @Nullable String string = TextFileIO.readTextFromUTF8File ( path );
        return () -> string;
    }
}
