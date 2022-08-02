package dev.pmlc.ext.utilities.pmltohtml.options;

import dev.pmlc.ext.PMLCResources;
import dev.pp.basics.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

public class PMLToHTMLResources {

    private static final @NotNull Path REL_RESOURCES_DIR = Path.of ("config/PML_to_HTML" );

    public static final @NotNull Path REL_OPTIONS_FILE =
        createPath ("options.pdml" );

    public static final @NotNull Path REL_DEFAULT_CSS_FILE =
        createPath ("css/pml-default.css" );

    public static final @NotNull Path REL_PRINT_DEFAULT_CSS_FILE =
        createPath ("css/pml-print-default.css" );

    public static final @NotNull Path REL_HTML_PAGE_START_TEMPLATE_FILE =
        createPath ("html/default_html_header_template.txt" );

    public static final @NotNull Path REL_HTML_PAGE_END_TEMPLATE_FILE =
        createPath ("html/default_html_footer_template.txt" );

    public static void addResources ( List<PMLCResources.ResourceFile> resources ) {

        PMLCResources.addTextResourceFile ( resources, REL_OPTIONS_FILE );
        PMLCResources.addTextResourceFile ( resources, REL_DEFAULT_CSS_FILE );
        PMLCResources.addTextResourceFile ( resources, REL_PRINT_DEFAULT_CSS_FILE );
        PMLCResources.addTextResourceFile ( resources, REL_HTML_PAGE_START_TEMPLATE_FILE );
        PMLCResources.addTextResourceFile ( resources, REL_HTML_PAGE_END_TEMPLATE_FILE );
    }

    private static @NotNull Path createPath ( @NotNull String childPath ) {

        return REL_RESOURCES_DIR.resolve ( childPath );
    }
}
