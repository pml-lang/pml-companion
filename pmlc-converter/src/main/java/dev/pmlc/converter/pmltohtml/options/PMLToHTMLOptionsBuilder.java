package dev.pmlc.converter.pmltohtml.options;

import dev.pmlc.data.node.block.TOCPosition;
import dev.pmlc.data.node.block.code.SourceCodeHighlighter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.TextFileIO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

public class PMLToHTMLOptionsBuilder {

    private @Nullable List<Path> CSSFiles = PMLToHTMLDefaultOptions.CSS_FILES;

    private @Nullable Supplier<String> HTMLPageStartTemplate = PMLToHTMLDefaultOptions.HTML_PAGE_START_TEMPLATE;

    private @Nullable Supplier<String> HTMLPageEndTemplate = PMLToHTMLDefaultOptions.HTML_PAGE_END_TEMPLATE;

    private @Nullable List<Path> resources = PMLToHTMLDefaultOptions.resources();

    private @NotNull TOCPosition TOCPosition = PMLToHTMLDefaultOptions.TOC_POSITION;

    private @Nullable String TOCTitle = PMLToHTMLDefaultOptions.TOC_TITLE;

    private int maxTOCChapterLevel = PMLToHTMLDefaultOptions.MAX_TOC_CHAPTER_LEVEL;

    private @NotNull SourceCodeHighlighter sourceCodeHighlighter = PMLToHTMLDefaultOptions.SOURCE_CODE_HIGHLIGHTER;

    private boolean omitCSS = PMLToHTMLDefaultOptions.OMIT_CSS;


    public PMLToHTMLOptionsBuilder() {}


    public PMLToHTMLOptionsBuilder CSSFiles ( @Nullable List<Path> CSSFiles ) {

        this.CSSFiles = CSSFiles;
        return this;
    }

    public PMLToHTMLOptionsBuilder HTMLPageStartTemplate ( @Nullable Supplier<String> supplier ) {

        this.HTMLPageStartTemplate = supplier;
        return this;
    }

    public PMLToHTMLOptionsBuilder HTMLPageStartTemplate ( @Nullable Path path ) throws IOException {

        if ( path == null ) {
            this.HTMLPageStartTemplate = null;
        } else {
            this.HTMLPageStartTemplate = stringSupplierForFile ( path );
        }
        return this;
    }

    public PMLToHTMLOptionsBuilder HTMLPageEndTemplate ( @Nullable Supplier<String> supplier ) {

        this.HTMLPageEndTemplate = supplier;
        return this;
    }

    public PMLToHTMLOptionsBuilder HTMLPageEndTemplate ( @Nullable Path path ) throws IOException {

        if ( path == null ) {
            this.HTMLPageEndTemplate = null;
        } else {
            this.HTMLPageEndTemplate = stringSupplierForFile ( path );
        }
        return this;
    }

    public PMLToHTMLOptionsBuilder resources ( @Nullable List<Path> paths ) {

        this.resources = paths;
        return this;
    }

    public PMLToHTMLOptionsBuilder TOCPosition ( @NotNull TOCPosition TOCPosition ) {

        this.TOCPosition = TOCPosition;
        return this;
    }

    public PMLToHTMLOptionsBuilder TOCTitle ( @Nullable String TOCTitle ) {

        this.TOCTitle = TOCTitle;
        return this;
    }

    public PMLToHTMLOptionsBuilder maxTOCChapterLevel ( int maxTOCChapterLevel ) {

        this.maxTOCChapterLevel = maxTOCChapterLevel;
        return this;
    }

    public PMLToHTMLOptionsBuilder sourceCodeHighlighter ( @NotNull SourceCodeHighlighter sourceCodeHighlighter ) {

        this.sourceCodeHighlighter = sourceCodeHighlighter;
        return this;
    }

    public PMLToHTMLOptionsBuilder omitCSS ( boolean omitCSS ) {

        this.omitCSS = omitCSS;
        return this;
    }


    public @NotNull PMLToHTMLOptions build() {

        return new PMLToHTMLOptions (
            CSSFiles,
            resources,
            HTMLPageStartTemplate,
            HTMLPageEndTemplate,
            TOCPosition,
            TOCTitle,
            maxTOCChapterLevel,
            sourceCodeHighlighter,
            omitCSS );
    }

    private static @NotNull Supplier<String> stringSupplierForFile ( @NotNull Path path ) throws IOException {

        @Nullable String string = TextFileIO.readTextFromUTF8File ( path );
        return () -> string;
    }
}

