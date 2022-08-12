package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.FormalTOCNode;
import dev.pmlc.core.data.node.PMLNode;
import dev.pmlc.core.data.node.block.chapter.TitleNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TOCNode extends PMLBlockNode {


    private final @NotNull TitleNode chapterTitleNode;
    public @NotNull TitleNode getChapterTitleNode () { return chapterTitleNode; }

    private final @NotNull String chapterId;
    public @NotNull String getChapterId() { return chapterId; }

    private final int chapterLevel;
    public int getChapterLevel() { return chapterLevel; }

    private @Nullable List<TOCNode> childTOCNodes;
    public @Nullable List<TOCNode> getChildTOCNodes () { return childTOCNodes; }


    public TOCNode() {

        // TODO
        this ( null, null, 0 );
        throw new RuntimeException ( "Should never be called." );
    }

    public TOCNode (
        @NotNull TitleNode chapterTitleNode,
        @NotNull String chapterId,
        int chapterLevel ) {

        super();
        this.chapterTitleNode = chapterTitleNode;
        this.chapterId = chapterId;
        this.chapterLevel = chapterLevel;
        this.childTOCNodes = null;
    }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, TOCNode> getFormalNode() { return FormalTOCNode.NODE; }

    @Override
    public @Nullable List<? extends PMLBlockNode> getBlockChildNodes () { return childTOCNodes; }

    @Override
    public boolean hasBlockChildNodes() { return childTOCNodes != null && ! childTOCNodes.isEmpty(); }

    @Override
    public @Nullable List<? extends PMLNode> getChildNodes() { return childTOCNodes; }

    public boolean isRootNode() { return chapterLevel == 0; }

    public @NotNull String getChapterTitleText() {

        String text = chapterTitleNode.getTextInTree();
        assert text != null;
        return text;
    }

    public void addChildNode ( @NotNull TOCNode childNode ) {

        if ( childTOCNodes == null ) childTOCNodes = new ArrayList<>();
        childTOCNodes.add ( childNode );
    }
}
