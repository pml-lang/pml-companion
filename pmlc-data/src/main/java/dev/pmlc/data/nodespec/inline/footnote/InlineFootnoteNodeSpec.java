package dev.pmlc.data.nodespec.inline.footnote;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.footnote.InlineFootnoteNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class InlineFootnoteNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "fnote" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Inline Footnote",
        """
            Inline footnotes are footnotes whose content is defined inline. They are displayed later in the document, as soon as an 'fnotes' node is encountered.
            Inline footnotes can contain text and other inline nodes to style the footnote. They cannot contain block nodes.""",
        """
            text text [fnote text of footnote 1] text

            text [fnote text of footnote 2 with [i italic] and [b bold] words] text text

            text text [fnote
                text of footnote 3
            ] text text

            [fnotes]
            """ );

    public static final @NotNull String HTML_TAG = "span";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "inline-footnote" );

    public static final @NotNull PMLNodeSpec<Void, InlineFootnoteNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        InlineFootnoteNode::new, PMLNodesHandler::inlineFootnote, HTML_TAG, CSS_CLASS );
}
