package dev.pmlc.core.data.formalnode.block;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.block.OptionsNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalOptionsNode {

    public static final @NotNull NodeName NAME = new NodeName ( "options" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Options",
        """
        Used to define a set of options applied to the document.
        If used, this node must be the first child node of the 'doc' node.
        Please consult the user manual and/or the CLI manual to see the list of options available.""",
        """
        [doc [title Options Demo]
            [options
                [TOCPosition top]
                [TOCTitle Inhaltsverzeichnis]
            ]
            text
        ]""" );

    public static final @NotNull String HTML_TAG = "ul";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "config" );

    public static final @NotNull FormalPMLNode<Void, OptionsNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, null, false, false, DOCUMENTATION,
        OptionsNode::new, PMLNodesHandler::options, HTML_TAG, CSS_CLASS );
}
