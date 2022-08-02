package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalChapterTitleNode;
import dev.pmlc.core.data.formalnode.block.FormalOptionsNode;
import dev.pmlc.core.data.formalnode.block.FormalDocumentNode;
import dev.pmlc.core.data.node.block.chapter.ChapterNode;
import dev.pmlc.core.data.node.block.chapter.ChapterTitleNode;
import dev.pmlc.core.data.node.validation.NodeValidationContext;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.list.Parameters;
import dev.pp.text.error.TextErrorException;
import dev.pp.text.token.TextToken;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DocumentNode extends PMLBlockNode {

    public static final @NotNull String DOCUMENT_AUTO_ID = "doc__";


    public DocumentNode() { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, DocumentNode> getFormalNode() { return FormalDocumentNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        super.createAttributes ( parameters );
        if ( nodeId == null ) {
            nodeId = DOCUMENT_AUTO_ID;
        }
    }

    @Override
    public void validate ( @NotNull NodeValidationContext context ) {

        if ( getParentNode() != null ) {
            context.handleError (
                "INVALID_DOC_NODE",
                "Node '" + getName() + "' must be a root node. It cannot be a child of node '" + getParentNode().getName() + "'.",
                getStartToken() );
        }

        context.checkHasChildren ( this );
    }

    public @Nullable List<OptionsNode> getOptionsNodes () {

        return findChildNodesByName ( FormalOptionsNode.NAME.toString() );
    }

    public @Nullable ChapterTitleNode getTitleNode() {

        return findFirstChildNodeByName ( FormalChapterTitleNode.NAME.toString() );
    }

    public @Nullable String getTitleText() {

        @Nullable ChapterTitleNode titleNode = getTitleNode();
        if ( titleNode == null ) return null;

        return titleNode.getTextInTree();
    }

    public void forEachDirectChildChapter ( @NotNull Consumer<ChapterNode> consumer ) {

        forEachChildNode ( childNode -> {
            if ( childNode instanceof ChapterNode chapterNode ) {
                consumer.accept ( chapterNode );
            }
        });
    }

    public @Nullable List<ChapterNode> getDirectChildChapters() {

        List<ChapterNode> childChapters = new ArrayList<> ();
        forEachDirectChildChapter ( childChapters::add );
        return childChapters.isEmpty() ? null : childChapters;
    }

    public boolean hasChildChapters() { return getDirectChildChapters() != null; }

    public @NotNull TOCNode createTOC ( int maxChapterLevel ) throws TextErrorException {

        @NotNull TOCNode rootNode = createTOCNode ( getTitleNode(), getNodeId(), 0, getStartToken() );
        addTOCChildNodes ( rootNode, getDirectChildChapters(), maxChapterLevel );
        return rootNode;
    }

    private void addTOCChildNodes (
        @NotNull TOCNode parentTOCNode,
        @Nullable List<ChapterNode> childChapterNodes,
        int maxChapterLevel ) throws TextErrorException {

        if ( parentTOCNode.getChapterLevel() >= maxChapterLevel ) return;

        if ( childChapterNodes == null ) return;

        for ( ChapterNode childChapterNode : childChapterNodes ) {
            TOCNode childTOCNode = createTOCNode (
                childChapterNode.getTitleNode(),
                childChapterNode.getNodeId(),
                childChapterNode.getLevel(),
                childChapterNode.getStartToken() );
            parentTOCNode.addChildNode ( childTOCNode );
            addTOCChildNodes ( childTOCNode, childChapterNode.getDirectChildChapters(), maxChapterLevel );
        }
    }

    private @NotNull TOCNode createTOCNode (
        @Nullable ChapterTitleNode titleNode,
        @Nullable String id,
        int level,
        @Nullable TextToken errorToken ) throws TextErrorException {

        if ( titleNode == null )
            // throw new RuntimeException ( "Each chapter included in the table of contents must have a title." );
            throw new TextErrorException (
                "TOC_CHAPTER_NEEDS_TITLE",
                "Each chapter included in the table of contents must have a title.",
                errorToken );

        if ( id == null )
            // throw new RuntimeException ( "Each chapter included in the table of contents must have an id." );
            throw new TextErrorException (
                "TOC_CHAPTER_NEEDS_ID",
                "Each chapter included in the table of contents must have an id.",
                errorToken );

        return new TOCNode ( titleNode, id, level );
    }
}
