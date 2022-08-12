package dev.pmlc.core.data.node.block.chapter;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalChapterNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalTitleNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChapterNode extends PMLBlockNode {


    private int level = 1;
    public int getLevel() { return level; }
    public void setLevel ( int level ) { this.level = level; }


    public ChapterNode() { super(); }


    public @NotNull FormalPMLNode<Void, ChapterNode> getFormalNode() { return FormalChapterNode.NODE; }

    public @Nullable TitleNode getTitleNode() {

        return findFirstChildNodeByName ( FormalTitleNode.NAME.toString() );
    }

    public @Nullable String getTitleText() {

        TitleNode titleNode = getTitleNode();
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

        List<ChapterNode> childChapters = new ArrayList<>();
        forEachDirectChildChapter ( childChapters::add );
        return childChapters.isEmpty() ? null : childChapters;
    }

    public boolean hasChildChapters() { return getDirectChildChapters() != null; }
}
