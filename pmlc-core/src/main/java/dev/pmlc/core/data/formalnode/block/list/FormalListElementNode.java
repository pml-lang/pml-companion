package dev.pmlc.core.data.formalnode.block.list;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.list.ListElementNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalListElementNode {

    public static final @NotNull NodeName NAME = new NodeName ( "el" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "List Element",
        "An element of a list.",
        """
            Some planets:
            [list
                [el [header Jupiter]
                    Jupiter is ...
                ]
                [el [header Uranus]
                    Uranus is ...
                ]
                [el [header Neptun]
                    Neptun is ...
                ]
            ]""" );

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "li";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "list-element" );

    public static final @NotNull FormalPMLNode<Void, ListElementNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        ListElementNode::new, PMLNodesHandler::listElement, HTML_TAG, CSS_CLASS );
}
