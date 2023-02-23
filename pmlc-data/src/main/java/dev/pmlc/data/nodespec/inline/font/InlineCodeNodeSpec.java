package dev.pmlc.data.nodespec.inline.font;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.font.InlineCodeNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class InlineCodeNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "c" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Inline Source Code",
        "Source code embedded within text.",
        "The assignment [c pi = 3.1415] means we are using [i four] decimals for [c pi]." );

    public static final @NotNull String HTML_TAG = "code";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "inline-code" );

    public static final @NotNull PMLNodeSpec<String, InlineCodeNode> NODE = PMLNodeSpecCreator.createForRawTextInlineNode (
        NAME, null, true, DOCUMENTATION,
        InlineCodeNode::new, PMLNodesHandler::inlineCode, HTML_TAG, CSS_CLASS );
}
