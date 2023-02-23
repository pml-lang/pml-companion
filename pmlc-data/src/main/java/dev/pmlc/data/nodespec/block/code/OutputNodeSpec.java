package dev.pmlc.data.nodespec.block.code;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.code.OutputNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class OutputNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "output" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Output",
        "Any output created by a software application, such as a result written to the OS's standard output device.",
        """
            [output
                ~~~
                Congratulations!
                The new database has been created successfully.
                ~~~
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "pre";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "output" );

    public static final @NotNull PMLNodeSpec<String, OutputNode> NODE = PMLNodeSpecCreator.createForRawTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        OutputNode::new, PMLNodesHandler::output, HTML_TAG, CSS_CLASS );
}
