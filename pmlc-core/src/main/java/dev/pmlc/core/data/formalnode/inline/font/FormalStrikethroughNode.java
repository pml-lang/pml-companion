package dev.pmlc.core.data.formalnode.inline.font;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.font.StrikethroughNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalStrikethroughNode {

    public static final @NotNull NodeName NAME = new NodeName ( "strike" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Strikethrough Text",
        "Strikethrough text is rendered with a line through it.",
        """
        [strike Only 50 tickets left.]

        SOLD OUT!""" );

    public static final @NotNull String HTML_TAG = "s";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "strikethrough" );

    public static final @NotNull FormalPMLNode<Void, StrikethroughNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        StrikethroughNode::new, PMLNodesHandler::strikethrough, HTML_TAG, CSS_CLASS );
}
