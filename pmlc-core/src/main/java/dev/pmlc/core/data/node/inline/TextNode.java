package dev.pmlc.core.data.node.inline;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.FormalTextNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

public class TextNode extends PMLInlineNode {

    // TODO? @NotNull -> @Nullable
    // private @NotNull String text = "";
    public @NotNull String getText() { return rawText == null ? "" : rawText; }


    public TextNode() { super(); }

    public TextNode ( @NotNull TextToken token ) {

        super();
        this.rawText = token.getText();
        this.startToken = token;
    }

    public TextNode ( @NotNull String text, @Nullable TextLocation location ) {

        this ( new TextToken ( text, location ) );
    }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<String, TextNode> getFormalNode() { return FormalTextNode.NODE; }
}
