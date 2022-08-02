package dev.pmlc.core.data.formalnode.block.code;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.code.InputNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalInputNode {

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

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "pre";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "input" );

    public static final @NotNull FormalPMLNode<String, InputNode> NODE = FormalPMLNodeCreator.createForRawTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        InputNode::new, PMLNodesHandler::input, HTML_TAG, CSS_CLASS );
}
