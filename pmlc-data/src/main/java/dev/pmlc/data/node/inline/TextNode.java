package dev.pmlc.data.node.inline;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.TextNodeSpec;
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
    public @NotNull PMLNodeSpec<String, TextNode> getNodeSpec () { return TextNodeSpec.NODE; }
}
