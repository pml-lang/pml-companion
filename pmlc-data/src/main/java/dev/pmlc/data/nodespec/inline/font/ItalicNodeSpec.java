package dev.pmlc.data.nodespec.inline.font;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.font.ItalicNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class ItalicNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "i" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Italic Text",
        null,
        """
        We want [i simplicity].

        This is a [b [i very] important] point.""" );

    public static final @NotNull String HTML_TAG = "i";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "italic" );

    public static final @NotNull PMLNodeSpec<Void, ItalicNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        ItalicNode::new, PMLNodesHandler::italic, HTML_TAG, CSS_CLASS );
}
