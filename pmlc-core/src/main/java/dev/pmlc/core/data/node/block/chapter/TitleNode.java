package dev.pmlc.core.data.node.block.chapter;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalTitleNode;
import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pmlc.core.data.node.inline.TextNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

public class TitleNode extends PMLBlockNode {


    private int level = 1;
    public int getLevel() { return level; }
    public void setLevel ( int level ) { this.level = level; }


    public TitleNode () { super(); }

    public TitleNode ( @NotNull String text, int level, @Nullable TextLocation location ) {

        super();
        this.level = level;
        addInlineChildNode ( new TextNode ( text, location ) );
    }


    public @NotNull FormalPMLNode<Void, TitleNode> getFormalNode() { return FormalTitleNode.NODE; }

    public boolean isDocumentTitle() { return parentNode instanceof DocumentNode; }
}
