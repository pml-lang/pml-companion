package dev.pmlc.core.data.formalnode.inline;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.VerbatimTextNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalVerbatimTextNode {

    public static final @NotNull NodeName NAME = new NodeName ( "verbatim" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Verbatim Text",
        "Raw, verbatim text that is rendered as is, without any transformations",
        "Math's ring operator is [verbatim &#x2218;]." );

    public static final @NotNull String HTML_TAG = "";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "verbatim" );

    public static final @NotNull FormalPMLNode<String, VerbatimTextNode> NODE = FormalPMLNodeCreator.createForRawTextInlineNode (
        NAME, null, true, DOCUMENTATION,
        VerbatimTextNode::new, PMLNodesHandler::verbatimText, HTML_TAG, CSS_CLASS );
}
