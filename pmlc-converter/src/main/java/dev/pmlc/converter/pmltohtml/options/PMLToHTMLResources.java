package dev.pmlc.converter.pmltohtml.options;

import dev.pmlc.converter.PMLCResources;
import dev.pp.basics.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PMLToHTMLResources {

    private static final @NotNull Path REL_RESOURCES_DIR = Path.of ("config/PML_to_HTML" );

    private static final @NotNull Path REL_OPTIONS_PATH = Path.of ( "options.pdml" );

    private static final @NotNull Path REL_DEFAULT_CSS_PATH = Path.of ( "css/pml-default.css" );
    private static final @NotNull Path REL_PRINT_DEFAULT_CSS_PATH = Path.of ( "css/pml-print-default.css" );

    private static final @NotNull Path REL_HTML_PAGE_START_TEMPLATE_PATH = Path.of (
        "html/default_html_header_template.txt" );
    private static final @NotNull Path REL_HTML_PAGE_END_TEMPLATE_PATH = Path.of (
        "html/default_html_footer_template.txt" );


    public static final @NotNull Path OPTIONS_FILE = absoluteResourcePath ( REL_OPTIONS_PATH );
    public static final @NotNull Path DEFAULT_CSS_FILE = absoluteResourcePath ( REL_DEFAULT_CSS_PATH );
    public static final @NotNull Path PRINT_DEFAULT_CSS_FILE = absoluteResourcePath ( REL_PRINT_DEFAULT_CSS_PATH );
    public static final @NotNull Path HTML_PAGE_START_TEMPLATE_FILE = absoluteResourcePath (
        REL_HTML_PAGE_START_TEMPLATE_PATH );
    public static final @NotNull Path HTML_PAGE_END_TEMPLATE_FILE = absoluteResourcePath (
        REL_HTML_PAGE_END_TEMPLATE_PATH );

    private static @NotNull Path absoluteResourcePath ( @NotNull Path relativePath ) {
        return PMLCResources.absoluteResourcePath ( relativeResourcePath ( relativePath ) );
    }

    private static @NotNull Path relativeResourcePath ( @NotNull Path relativePath ) {
        return REL_RESOURCES_DIR.resolve ( relativePath );
    }


    public static void createResourceFilesIfNotExists() throws IOException {

        if ( Files.exists ( OPTIONS_FILE ) ) return;

        createResourceFiles();
    }

    private static void createResourceFiles() throws IOException {

        createResourceFile ( REL_OPTIONS_PATH, true );
        createResourceFile ( REL_DEFAULT_CSS_PATH, true );
        createResourceFile ( REL_PRINT_DEFAULT_CSS_PATH, true );
        createResourceFile ( REL_HTML_PAGE_START_TEMPLATE_PATH, true );
        createResourceFile ( REL_HTML_PAGE_END_TEMPLATE_PATH, true );
    }

    private static void createResourceFile ( @NotNull Path resourcePath, boolean isTextFile ) throws IOException {

        PMLCResources.createResourceFile (
            PMLToHTMLResources.class, relativeResourcePath ( resourcePath ), isTextFile );
    }
}
