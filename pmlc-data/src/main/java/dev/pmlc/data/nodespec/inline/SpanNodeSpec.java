package dev.pmlc.data.nodespec.inline;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.SpanNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class SpanNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "span" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Span",
        "This inline node is typically used to render a HTML <span>...</span> inline element with a specific set of HTML attributes.",
        "This is a [span (html_style=\"color:red;background-color:yellow\") huge] advantage." );

    public static final @NotNull String HTML_TAG = "span";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "span" );

    public static final @NotNull PMLNodeSpec<Void, SpanNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        SpanNode::new, PMLNodesHandler::span, HTML_TAG, CSS_CLASS );
}
