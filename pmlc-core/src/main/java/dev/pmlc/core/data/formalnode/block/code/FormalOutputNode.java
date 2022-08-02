package dev.pmlc.core.data.formalnode.block.code;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.code.OutputNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalOutputNode {

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

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "pre";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "output" );

    public static final @NotNull FormalPMLNode<String, OutputNode> NODE = FormalPMLNodeCreator.createForRawTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        OutputNode::new, PMLNodesHandler::output, HTML_TAG, CSS_CLASS );
}
