package dev.pmlc.data.node.block;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.DocumentNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.TitleNodeSpec;
import dev.pmlc.data.node.block.chapter.ChapterNode;
import dev.pmlc.data.node.block.chapter.TitleNode;
import dev.pmlc.data.node.validator.NodeValidatorContext;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.text.inspection.TextErrorException;
import dev.pp.text.token.TextToken;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DocumentNode extends PMLBlockNode {

    public static final @NotNull String DOCUMENT_AUTO_ID = "doc__";


    public DocumentNode() { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, DocumentNode> getNodeSpec () { return DocumentNodeSpec.NODE; }

    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        super.setAttributes ( parameters );
        if ( nodeId == null ) {
            nodeId = DOCUMENT_AUTO_ID;
        }
    }

    @Override
    public void validate ( @NotNull NodeValidatorContext context ) {

        if ( getParentNode() != null ) {
            context.handleError (
                "Node '" + getName() + "' must be a root node. It cannot be a child of node '" + getParentNode().getName() + "'.",
                "INVALID_DOC_NODE",
                getStartToken() );
        }

        context.checkHasChildren ( this );
    }

    public @Nullable TitleNode getTitleNode() {

        return findFirstChildNodeByName ( TitleNodeSpec.NAME.toString() );
    }

    public @Nullable String getTitleText() {

        @Nullable TitleNode titleNode = getTitleNode();
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

        int level = 0;

        TitleNode titleNode = getTitleNode();
        if ( titleNode == null ) titleNode = new TitleNode ( "Untitled", level, null );

        @NotNull TOCNode rootNode = createTOCNode ( titleNode, getNodeId(), level, getStartToken() );
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
        @Nullable TitleNode titleNode,
        @Nullable String id,
        int level,
        @Nullable TextToken errorToken ) throws TextErrorException {

        if ( titleNode == null ) {
            // throw new RuntimeException ( "Each chapter included in the table of contents must have a title." );
            throw new TextErrorException (
                "Each chapter included in the table of contents must have a title.",
                "TOC_CHAPTER_NEEDS_TITLE",
                errorToken );

            // titleNode = new ChapterTitleNode ( "Untitled", level );
        }

        if ( id == null ) {
            // throw new RuntimeException ( "Each chapter included in the table of contents must have an id." );
            throw new TextErrorException (
                "Each chapter included in the table of contents must have an id.",
                "TOC_CHAPTER_NEEDS_ID",
                errorToken );
        }

        return new TOCNode ( titleNode, id, level );
    }
}
