package dev.pmlc.data.nodespec.block.admonition;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pmlc.data.node.block.admonition.AdmonitionNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class AdmonitionNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "admon" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Admonition",
        "A labeled piece of advice, such as a note, tip, warning, etc.",
        """
            [admon
                [alabel Tip]
                Later you'll see some [i striking] examples.
            ]""" );

    @Deprecated
    public static final @NotNull ParameterSpec<String> LABEL_ATTRIBUTE__REMOVED = new ParameterSpec.Builder<> (
        "label", CommonDataTypes.STRING )
        .documentation ( "Label",
            "The admonition's label, such as 'Note', 'Tip', 'Warning', etc.",
            "label = Tip" )
        .build();

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "admonition" );

    public static final @NotNull PMLNodeSpec<Void, AdmonitionNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        AdmonitionNode::new, PMLNodesHandler::admonition, HTML_TAG, CSS_CLASS );
}
