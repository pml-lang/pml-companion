package dev.pmlc.core.data.formalnode.inline.font;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.font.InlineCodeNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalInlineCodeNode {

    public static final @NotNull NodeName NAME = new NodeName ( "c" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Inline Source Code",
        "Source code embedded within text.",
        "The assignment [c pi = 3.1415] means we are using [i four] decimals for [c pi]." );

    public static final @NotNull String HTML_TAG = "code";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "inline-code" );

    public static final @NotNull FormalPMLNode<String, InlineCodeNode> NODE = FormalPMLNodeCreator.createForRawTextInlineNode (
        NAME, null, true, DOCUMENTATION,
        InlineCodeNode::new, PMLNodesHandler::inlineCode, HTML_TAG, CSS_CLASS );
}
