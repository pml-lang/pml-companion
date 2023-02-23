package dev.pmlc.data.nodespec.block.admonition;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.node.block.admonition.AdmonitionLabelNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class AdmonitionLabelNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "alabel" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Admonition Label",
        "The label (title) of an admonition, such as Tip, Warning, Important etc.",
        """
            [admon
                [label Tip]
                Later you'll see some [i striking] examples.
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "admonition-label" );

    public static final @NotNull PMLNodeSpec<Void, AdmonitionLabelNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        AdmonitionLabelNode::new, PMLNodesHandler::admonitionLabel, HTML_TAG, CSS_CLASS );
}
