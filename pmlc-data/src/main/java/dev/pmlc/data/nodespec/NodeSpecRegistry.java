package dev.pmlc.data.nodespec;

import dev.pdml.parser.nodespec.PdmlNodeSpecs;
import dev.pmlc.data.nodespec.block.*;
import dev.pmlc.data.nodespec.block.admonition.AdmonitionLabelNodeSpec;
import dev.pmlc.data.nodespec.block.admonition.AdmonitionNodeSpec;
import dev.pmlc.data.nodespec.block.admonition.NoteNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.ChapterNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.SubtitleNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.TitleNodeSpec;
import dev.pmlc.data.nodespec.block.code.*;
import dev.pmlc.data.nodespec.block.footnote.FootnoteDefinitionNodeSpec;
import dev.pmlc.data.nodespec.block.footnote.FootnotesPlaceholderNodeSpec;
import dev.pmlc.data.nodespec.block.list.ListElementNodeSpec;
import dev.pmlc.data.nodespec.block.list.ListNodeSpec;
import dev.pmlc.data.nodespec.block.media.AudioNodeSpec;
import dev.pmlc.data.nodespec.block.media.ImageNodeSpec;
import dev.pmlc.data.nodespec.block.media.VideoNodeSpec;
import dev.pmlc.data.nodespec.block.media.YoutubeVideoNodeSpec;
import dev.pmlc.data.nodespec.block.quote.QuoteNodeSpec;
import dev.pmlc.data.nodespec.block.quote.QuoteSourceNodeSpec;
import dev.pmlc.data.nodespec.block.table.*;
import dev.pmlc.data.nodespec.inline.*;
import dev.pmlc.data.nodespec.inline.font.*;
import dev.pmlc.data.nodespec.inline.footnote.FootnoteReferenceNodeSpec;
import dev.pmlc.data.nodespec.inline.footnote.InlineFootnoteNodeSpec;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodeSpecRegistry {

    private static final @NotNull Map<String, PMLNodeSpec<?, ?>> map = createStandardMap();

    public static @Nullable PMLNodeSpec<?, ?> getByNameOrNull ( @NotNull String name ) {
        return map.get ( name );
    }

    @SuppressWarnings ( { "raw", "unchecked" } )
    public static @NotNull PdmlNodeSpecs getAll() {

        PdmlNodeSpecs nodes = new PdmlNodeSpecs();
        for ( PMLNodeSpec<?, ?> node : map.values() ) {
            nodes.add ( node );
        }
        return nodes;
    }

    public static @NotNull List<PMLNodeSpec<?,?>> getListSortedByName() {

        return map
            .values()
            .stream()
            .sorted ( Comparator.comparing ( PMLNodeSpec::qualifiedName ) )
            .collect ( Collectors.toList() );
    }


    private static @NotNull Map<String, PMLNodeSpec<?, ?>> createStandardMap() {

        Map<String, PMLNodeSpec<?, ?>> map = new HashMap<>();

        // Block Nodes
        addNodeSpec ( AdmonitionLabelNodeSpec.NODE, map );
        addNodeSpec ( AdmonitionNodeSpec.NODE, map );
        addNodeSpec ( AudioNodeSpec.NODE, map );
        addNodeSpec ( CaptionNodeSpec.NODE, map );
        addNodeSpec ( ChapterNodeSpec.NODE, map );
        addNodeSpec ( SubtitleNodeSpec.NODE, map );
        addNodeSpec ( TitleNodeSpec.NODE, map );
        addNodeSpec ( DivisionNodeSpec.NODE, map );
        addNodeSpec ( DocumentNodeSpec.NODE, map );
        addNodeSpec ( FootnoteDefinitionNodeSpec.NODE, map );
        addNodeSpec ( FootnotesPlaceholderNodeSpec.NODE, map );
        addNodeSpec ( HeaderNodeSpec.NODE, map );
        addNodeSpec ( HtmlCodeNodeSpec.NODE, map );
        addNodeSpec ( ImageNodeSpec.NODE, map );
        addNodeSpec ( InputNodeSpec.NODE, map );
        addNodeSpec ( InsertSourceCodeNodeSpec.NODE, map );
        addNodeSpec ( ListNodeSpec.NODE, map );
        addNodeSpec ( ListElementNodeSpec.NODE, map );
        addNodeSpec ( MonospaceNodeSpec.NODE, map );
        addNodeSpec ( NoteNodeSpec.NODE, map );
        addNodeSpec ( OutputNodeSpec.NODE, map );
        addNodeSpec ( ParagraphNodeSpec.NODE, map );
        addNodeSpec ( QuoteNodeSpec.NODE, map );
        addNodeSpec ( QuoteSourceNodeSpec.NODE, map );
        addNodeSpec ( SourceCodeNodeSpec.NODE, map );
        addNodeSpec ( TableNodeSpec.NODE, map );
        addNodeSpec ( TableCellNodeSpec.NODE, map );
        addNodeSpec ( SimpleTableNodeSpec.NODE, map );
        addNodeSpec ( TableFooterNodeSpec.NODE, map );
        addNodeSpec ( TableHeaderNodeSpec.NODE, map );
        addNodeSpec ( TableRowNodeSpec.NODE, map );
        addNodeSpec ( VideoNodeSpec.NODE, map );
        addNodeSpec ( YoutubeVideoNodeSpec.NODE, map );

        // Inline Nodes
        addNodeSpec ( BoldNodeSpec.NODE, map );
        addNodeSpec ( FootnoteReferenceNodeSpec.NODE, map );
        addNodeSpec ( InlineCodeNodeSpec.NODE, map );
        addNodeSpec ( InlineFootnoteNodeSpec.NODE, map );
        addNodeSpec ( ItalicNodeSpec.NODE, map );
        addNodeSpec ( LinkNodeSpec.NODE, map );
        addNodeSpec ( NewLineNodeSpec.NODE, map );
        addNodeSpec ( SpaceNodeSpec.NODE, map );
        addNodeSpec ( SpanNodeSpec.NODE, map );
        addNodeSpec ( StrikethroughNodeSpec.NODE, map );
        addNodeSpec ( SubscriptNodeSpec.NODE, map );
        addNodeSpec ( SuperscriptNodeSpec.NODE, map );
        addNodeSpec ( TextNodeSpec.NODE, map );
        addNodeSpec ( VerbatimTextNodeSpec.NODE, map );
        addNodeSpec ( XrefNodeSpec.NODE, map );

        return map;
    }

    private static void addNodeSpec (
        @NotNull PMLNodeSpec<?, ?> nodeSpec,
        @NotNull Map<String, PMLNodeSpec<?, ?>> map ) {

        String name = nodeSpec.getName().toString();
        if ( map.containsKey ( name ) ) throw new RuntimeException (
            "PML node spec.'" + name + "' has already been registered." );

        map.put ( name, nodeSpec );
    }
}
