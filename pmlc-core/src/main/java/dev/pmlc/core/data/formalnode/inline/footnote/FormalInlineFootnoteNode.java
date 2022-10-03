package dev.pmlc.core.data.formalnode.inline.footnote;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.footnote.InlineFootnoteNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalInlineFootnoteNode {

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

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "inline-footnote" );

    public static final @NotNull FormalPMLNode<Void, InlineFootnoteNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        InlineFootnoteNode::new, PMLNodesHandler::inlineFootnote, HTML_TAG, CSS_CLASS );
}
