package dev.pmlc.data.nodespec.block;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.DivisionNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class DivisionNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "div" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Division",
        "A division or section in the document. This node is typically used to render a HTML <div>...</div> block with a specific set of HTML attributes.",
        """
            [div (html_style="color:red;")
                Important message: ...
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "division" );

    public static final @NotNull PMLNodeSpec<Void, DivisionNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        DivisionNode::new, PMLNodesHandler::division, HTML_TAG, CSS_CLASS );
}
