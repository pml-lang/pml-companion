package dev.pmlc.data.nodespec.block.code;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.code.InputNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class InputNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "input" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Input",
        "Any input provided to a software application, such as a command typed in a terminal, text contained in a config file, etc.",
        """
            [input
                ~~~
                pmlc pml_to_html article.pml
                ~~~
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "pre";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "input" );

    public static final @NotNull PMLNodeSpec<String, InputNode> NODE = PMLNodeSpecCreator.createForRawTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        InputNode::new, PMLNodesHandler::input, HTML_TAG, CSS_CLASS );
}
