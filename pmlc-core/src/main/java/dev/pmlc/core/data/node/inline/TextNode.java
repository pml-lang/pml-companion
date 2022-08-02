package dev.pmlc.core.data.node.inline;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.FormalTextNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.Map;

public class TextNode extends PMLInlineNode {

    // TODO? @NotNull -> @Nullable
    // private @NotNull String text = "";
    public @NotNull String getText() { return rawText == null ? "" : rawText; }
    /*
    public void setText ( @NotNull String text ) {
        this.text = text;
        this.text = text;
    }

    @Override
    public void setRawText ( @Nullable String rawText ) {

        super.setRawText ( rawText );
        this.text = rawText == null ? "" : rawText;
    }

     */


    public TextNode() { super(); }

    public TextNode (
        @Nullable Map<String, String> HTMLAttributes,
        @NotNull String text ) {

        super ( HTMLAttributes );
        this.rawText = text;
    }

    public TextNode ( @NotNull String text ) {

        this ( null, text );
    }


    public @NotNull FormalPMLNode<String, TextNode> getFormalNode() { return FormalTextNode.NODE; }
}
