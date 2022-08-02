package dev.pmlc.core.data.formalnode.inline;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.SpanNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalSpanNode {

    public static final @NotNull NodeName NAME = new NodeName ( "span" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Span",
        "This inline node is typically used to render a HTML <span>...</span> inline element with a specific set of HTML attributes.",
        "This is a [span (html_style=\"color:red;background-color:yellow\") huge] advantage." );

    public static final @NotNull String HTML_TAG = "span";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "span" );

    public static final @NotNull FormalPMLNode<Void, SpanNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        SpanNode::new, PMLNodesHandler::span, HTML_TAG, CSS_CLASS );
}
