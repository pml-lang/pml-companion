package dev.pmlc.data.node.block.chapter;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.chapter.TitleNodeSpec;
import dev.pmlc.data.node.block.DocumentNode;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.node.inline.TextNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.location.TextLocation;

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


    public @NotNull PMLNodeSpec<Void, TitleNode> getNodeSpec () { return TitleNodeSpec.NODE; }

    public boolean isDocumentTitle() { return parentNode instanceof DocumentNode; }
}
