package dev.pmlc.ext.utilities.pmltohtml.options;

import dev.pmlc.core.data.node.block.code.SourceCodeHighlighter;
import dev.pmlc.core.data.node.block.TOCPosition;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.TextFileIO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

public record PMLToHTMLOptions(
    @Nullable List<Path> CSSFiles,
    @Nullable List<Path> resources,
    @Nullable Supplier<String> HTMLPageStartTemplate,
    @Nullable Supplier<String> HTMLPageEndTemplate,
    @NotNull TOCPosition TOCPosition,
    @Nullable String TOCTitle,
    int maxTOCChapterLevel,
    @NotNull SourceCodeHighlighter sourceCodeHighlighter,
    boolean omitCSS ) {


    public static @NotNull Builder builder() { return new Builder(); }


    public static class Builder {

        private @Nullable List<Path> CSSFiles = PMLToHTMLDefaultOptions.CSS_FILES;

        private @Nullable Supplier<String> HTMLPageStartTemplate = PMLToHTMLDefaultOptions.HTML_PAGE_START_TEMPLATE;

        private @Nullable Supplier<String> HTMLPageEndTemplate = PMLToHTMLDefaultOptions.HTML_PAGE_END_TEMPLATE;

        private @Nullable List<Path> resources = PMLToHTMLDefaultOptions.resources();

        private @NotNull TOCPosition TOCPosition = PMLToHTMLDefaultOptions.TOC_POSITION;

        private @Nullable String TOCTitle = PMLToHTMLDefaultOptions.TOC_TITLE;

        private int maxTOCChapterLevel = PMLToHTMLDefaultOptions.MAX_TOC_CHAPTER_LEVEL;

        private @NotNull SourceCodeHighlighter sourceCodeHighlighter = PMLToHTMLDefaultOptions.SOURCE_CODE_HIGHLIGHTER;

        private boolean omitCSS = PMLToHTMLDefaultOptions.OMIT_CSS;


        public Builder() {}


        public Builder CSSFiles ( @Nullable List<Path> CSSFiles ) {

            this.CSSFiles = CSSFiles;
            return this;
        }

        public Builder HTMLPageStartTemplate ( @Nullable Supplier<String> supplier ) {

            this.HTMLPageStartTemplate = supplier;
            return this;
        }

        public Builder HTMLPageStartTemplate ( @Nullable Path path ) throws IOException {

            if ( path == null ) {
                this.HTMLPageStartTemplate = null;
            } else {
                this.HTMLPageStartTemplate = stringSupplierForFile ( path );
            }
            return this;
        }

        public Builder HTMLPageEndTemplate ( @Nullable Supplier<String> supplier ) {

            this.HTMLPageEndTemplate = supplier;
            return this;
        }

        public Builder HTMLPageEndTemplate ( @Nullable Path path ) throws IOException {

            if ( path == null ) {
                this.HTMLPageEndTemplate = null;
            } else {
                this.HTMLPageEndTemplate = stringSupplierForFile ( path );
            }
            return this;
        }

        public Builder resources ( @Nullable List<Path> paths ) {

            this.resources = paths;
            return this;
        }

        public Builder TOCPosition ( @NotNull TOCPosition TOCPosition ) {

            this.TOCPosition = TOCPosition;
            return this;
        }

        public Builder TOCTitle ( @Nullable String TOCTitle ) {

            this.TOCTitle = TOCTitle;
            return this;
        }

        public Builder maxTOCChapterLevel ( int maxTOCChapterLevel ) {

            this.maxTOCChapterLevel = maxTOCChapterLevel;
            return this;
        }

        public Builder sourceCodeHighlighter ( @NotNull SourceCodeHighlighter sourceCodeHighlighter ) {

            this.sourceCodeHighlighter = sourceCodeHighlighter;
            return this;
        }

        public Builder omitCSS ( boolean omitCSS ) {

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
}

