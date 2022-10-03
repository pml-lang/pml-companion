package dev.pmlc.core.data.formalnode;

import dev.pdml.core.data.formalNode.FormalPDMLNodes;
import dev.pmlc.core.data.formalnode.block.*;
import dev.pmlc.core.data.formalnode.block.chapter.FormalChapterNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalSubtitleNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalTitleNode;
import dev.pmlc.core.data.formalnode.block.code.*;
import dev.pmlc.core.data.formalnode.block.footnote.FormalFootnoteDefinitionNode;
import dev.pmlc.core.data.formalnode.block.footnote.FormalFootnotesPlaceholderNode;
import dev.pmlc.core.data.formalnode.block.list.FormalListElementNode;
import dev.pmlc.core.data.formalnode.block.list.FormalListNode;
import dev.pmlc.core.data.formalnode.block.media.FormalAudioNode;
import dev.pmlc.core.data.formalnode.block.media.FormalImageNode;
import dev.pmlc.core.data.formalnode.block.media.FormalVideoNode;
import dev.pmlc.core.data.formalnode.block.media.FormalYoutubeVideoNode;
import dev.pmlc.core.data.formalnode.block.table.*;
import dev.pmlc.core.data.formalnode.inline.*;
import dev.pmlc.core.data.formalnode.inline.font.*;
import dev.pmlc.core.data.formalnode.inline.footnote.FormalFootnoteReferenceNode;
import dev.pmlc.core.data.formalnode.inline.footnote.FormalInlineFootnoteNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FormalNodeRegistry {

    private static final @NotNull Map<String, FormalPMLNode<?, ?>> map = createStandardMap();

    public static @Nullable FormalPMLNode<?, ?> getByNameOrNull ( @NotNull String name ) {
        return map.get ( name );
    }

    @SuppressWarnings ( { "raw", "unchecked" } )
    public static @NotNull FormalPDMLNodes getAll() {

        FormalPDMLNodes nodes = new FormalPDMLNodes();
        for ( FormalPMLNode<?, ?> node : map.values() ) {
            nodes.add ( node );
        }
        return nodes;
    }

    public static @NotNull List<FormalPMLNode<?,?>> getListSortedByName() {

        return map
            .values()
            .stream()
            .sorted ( Comparator.comparing ( FormalPMLNode::getNameAsString ) )
            .collect ( Collectors.toList() );
    }


    private static @NotNull Map<String, FormalPMLNode<?, ?>> createStandardMap() {

        Map<String, FormalPMLNode<?, ?>> map = new HashMap<>();

        // Block Nodes
        addFormalNode ( FormalAdmonitionNode.NODE, map );
        addFormalNode ( FormalAudioNode.NODE, map );
        addFormalNode ( FormalCaptionNode.NODE, map );
        addFormalNode ( FormalChapterNode.NODE, map );
        addFormalNode ( FormalSubtitleNode.NODE, map );
        addFormalNode ( FormalTitleNode.NODE, map );
        addFormalNode ( FormalDivisionNode.NODE, map );
        addFormalNode ( FormalDocumentNode.NODE, map );
        addFormalNode ( FormalFootnoteDefinitionNode.NODE, map );
        addFormalNode ( FormalFootnotesPlaceholderNode.NODE, map );
        addFormalNode ( FormalHeaderNode.NODE, map );
        addFormalNode ( FormalHTMLCodeNode.NODE, map );
        addFormalNode ( FormalImageNode.NODE, map );
        addFormalNode ( FormalInputNode.NODE, map );
        addFormalNode ( FormalInsertSourceCodeNode.NODE, map );
        addFormalNode ( FormalListNode.NODE, map );
        addFormalNode ( FormalListElementNode.NODE, map );
        addFormalNode ( FormalMonospaceNode.NODE, map );
        addFormalNode ( FormalNoteNode.NODE, map );
        addFormalNode ( FormalOptionsNode.NODE, map );
        addFormalNode ( FormalOutputNode.NODE, map );
        addFormalNode ( FormalParagraphNode.NODE, map );
        addFormalNode ( FormalQuoteNode.NODE, map );
        addFormalNode ( FormalSourceCodeNode.NODE, map );
        addFormalNode ( FormalTableNode.NODE, map );
        addFormalNode ( FormalTableCellNode.NODE, map );
        addFormalNode ( FormalTableDataNode.NODE, map );
        addFormalNode ( FormalTableFooterNode.NODE, map );
        addFormalNode ( FormalTableHeaderNode.NODE, map );
        addFormalNode ( FormalTableRowNode.NODE, map );
        addFormalNode ( FormalVideoNode.NODE, map );
        addFormalNode ( FormalYoutubeVideoNode.NODE, map );

        // Inline Nodes
        addFormalNode ( FormalBoldNode.NODE, map );
        addFormalNode ( FormalFootnoteReferenceNode.NODE, map );
        addFormalNode ( FormalInlineCodeNode.NODE, map );
        addFormalNode ( FormalInlineFootnoteNode.NODE, map );
        addFormalNode ( FormalItalicNode.NODE, map );
        addFormalNode ( FormalLinkNode.NODE, map );
        addFormalNode ( FormalNewLineNode.NODE, map );
        addFormalNode ( FormalSpaceNode.NODE, map );
        addFormalNode ( FormalSpanNode.NODE, map );
        addFormalNode ( FormalStrikethroughNode.NODE, map );
        addFormalNode ( FormalSubscriptNode.NODE, map );
        addFormalNode ( FormalSuperscriptNode.NODE, map );
        addFormalNode ( FormalTextNode.NODE, map );
        addFormalNode ( FormalVerbatimTextNode.NODE, map );
        addFormalNode ( FormalXrefNode.NODE, map );

        return map;
    }

    private static void addFormalNode (
        @NotNull FormalPMLNode<?, ?> formalNode,
        @NotNull Map<String, FormalPMLNode<?, ?>> map ) {

        String name = formalNode.getName().toString();
        if ( map.containsKey ( name ) ) throw new RuntimeException (
            "Formal PML node '" + name + "' has already been registered." );

        map.put ( name, formalNode );
    }
}
