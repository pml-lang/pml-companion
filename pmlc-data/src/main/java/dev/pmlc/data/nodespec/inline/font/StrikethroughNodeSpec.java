package dev.pmlc.data.nodespec.inline.font;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.font.StrikethroughNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class StrikethroughNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "strike" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Strikethrough Text",
        "Strikethrough text is rendered with a line through it.",
        """
        [strike Only 50 tickets left.]

        SOLD OUT!""" );

    public static final @NotNull String HTML_TAG = "s";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "strikethrough" );

    public static final @NotNull PMLNodeSpec<Void, StrikethroughNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        StrikethroughNode::new, PMLNodesHandler::strikethrough, HTML_TAG, CSS_CLASS );
}
