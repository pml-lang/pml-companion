package dev.pmlc.core.data.node.block.chapter;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalChapterTitleNode;
import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;

public class ChapterTitleNode extends PMLBlockNode {


    private int level = 1;
    public int getLevel() { return level; }
    public void setLevel ( int level ) { this.level = level; }


    public ChapterTitleNode () { super(); }


    public @NotNull FormalPMLNode<Void, ChapterTitleNode> getFormalNode() { return FormalChapterTitleNode.NODE; }

    public boolean isDocumentTitle() { return parentNode instanceof DocumentNode; }
}
