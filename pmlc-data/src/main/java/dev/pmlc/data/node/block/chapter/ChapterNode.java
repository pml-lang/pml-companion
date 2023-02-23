package dev.pmlc.data.node.block.chapter;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.ChapterNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.TitleNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
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


    public @NotNull PMLNodeSpec<Void, ChapterNode> getNodeSpec () { return ChapterNodeSpec.NODE; }

    public @Nullable TitleNode getTitleNode() {

        return findFirstChildNodeByName ( TitleNodeSpec.NAME.toString() );
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
