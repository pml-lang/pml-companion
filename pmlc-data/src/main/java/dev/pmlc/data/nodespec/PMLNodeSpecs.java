package dev.pmlc.data.nodespec;

import dev.pdml.parser.nodespec.PdmlNodeSpecs;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
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

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PMLNodeSpecs {

    private final @NotNull Map<String, PMLNodeSpec<?,?>> map;


    public PMLNodeSpecs() {
        this.map = new HashMap<>();
    }

    public static @NotNull PMLNodeSpecs createStandardNodeSpecs() {

        PMLNodeSpecs nodeSpecs = new PMLNodeSpecs();
        nodeSpecs.addStandardNodeSpecs();
        return nodeSpecs;
    }


    public boolean containsQualifiedName ( @NotNull String qualifiedName ) { return map.containsKey ( qualifiedName ); }

    public @Nullable PMLNodeSpec<?,?> getOrNull ( @NotNull String qualifiedName ) {
        return map.get ( qualifiedName );
    }

    public @NotNull List<PMLNodeSpec<?,?>> getListSortedByName() {

        return map
            .values()
            .stream()
            .sorted ( Comparator.comparing ( PMLNodeSpec::qualifiedName ) )
            .toList();
    }


    public void add ( @NotNull PMLNodeSpec<?,?> nodeSpec ) {

        String qualifiedName = nodeSpec.qualifiedName();
        checkNotExists ( qualifiedName );
        map.put ( qualifiedName, nodeSpec );
    }

    public void addStandardNodeSpecs() {

        // Block Nodes
        add ( AdmonitionLabelNodeSpec.NODE );
        add ( AdmonitionNodeSpec.NODE );
        add ( AudioNodeSpec.NODE );
        add ( CaptionNodeSpec.NODE );
        add ( ChapterNodeSpec.NODE );
        add ( SubtitleNodeSpec.NODE );
        add ( TitleNodeSpec.NODE );
        add ( DivisionNodeSpec.NODE );
        add ( DocumentNodeSpec.NODE );
        add ( FootnoteDefinitionNodeSpec.NODE );
        add ( FootnotesPlaceholderNodeSpec.NODE );
        add ( HeaderNodeSpec.NODE );
        add ( HtmlCodeNodeSpec.NODE );
        add ( ImageNodeSpec.NODE );
        add ( InputNodeSpec.NODE );
        add ( InsertSourceCodeNodeSpec.NODE );
        add ( ListNodeSpec.NODE );
        add ( ListElementNodeSpec.NODE );
        add ( MonospaceNodeSpec.NODE );
        add ( NoteNodeSpec.NODE );
        add ( OutputNodeSpec.NODE );
        add ( ParagraphNodeSpec.NODE );
        add ( QuoteNodeSpec.NODE );
        add ( QuoteSourceNodeSpec.NODE );
        add ( SourceCodeNodeSpec.NODE );
        add ( TableNodeSpec.NODE );
        add ( TableCellNodeSpec.NODE );
        add ( SimpleTableNodeSpec.NODE );
        add ( TableFooterNodeSpec.NODE );
        add ( TableHeaderNodeSpec.NODE );
        add ( TableRowNodeSpec.NODE );
        add ( VideoNodeSpec.NODE );
        add ( YoutubeVideoNodeSpec.NODE );

        // Inline Nodes
        add ( BoldNodeSpec.NODE );
        add ( FootnoteReferenceNodeSpec.NODE );
        add ( InlineCodeNodeSpec.NODE );
        add ( InlineFootnoteNodeSpec.NODE );
        add ( ItalicNodeSpec.NODE );
        add ( LinkNodeSpec.NODE );
        add ( NewLineNodeSpec.NODE );
        add ( SpaceNodeSpec.NODE );
        add ( SpanNodeSpec.NODE );
        add ( StrikethroughNodeSpec.NODE );
        add ( SubscriptNodeSpec.NODE );
        add ( SuperscriptNodeSpec.NODE );
        add ( TextNodeSpec.NODE );
        add ( VerbatimTextNodeSpec.NODE );
        add ( XrefNodeSpec.NODE );
    }


    @SuppressWarnings ( { "raw", "unchecked" } )
    public @NotNull PdmlNodeSpecs toPdmlNodeSpecs() {

        PdmlNodeSpecs nodes = new PdmlNodeSpecs();
        for ( PMLNodeSpec<?, ?> node : map.values() ) {
            nodes.add ( node );
        }
        return nodes;
    }


    private void checkExists ( @NotNull String qualifiedName ) {

        if ( ! containsQualifiedName ( qualifiedName ) )
            throw new IllegalArgumentException ( "Node spec. '" + qualifiedName + "' does not exist." );
    }

    private void checkNotExists ( @NotNull String qualifiedName ) {

        if ( containsQualifiedName ( qualifiedName ) )
            throw new IllegalArgumentException ( "Node spec. '" + qualifiedName + "' exists already." );
    }
}
