package dev.pmlc.converter.pmltohtml.options;

import dev.pmlc.data.node.block.code.SourceCodeHighlighter;
import dev.pmlc.data.node.block.TOCPosition;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

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


    public static @NotNull PMLToHTMLOptionsBuilder builder() { return new PMLToHTMLOptionsBuilder(); }
}

