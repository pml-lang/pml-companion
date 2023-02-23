package dev.pmlc.data.nodespec.block.list;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.list.ListElementNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class ListElementNodeSpec {

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

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "li";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "list-element" );

    public static final @NotNull PMLNodeSpec<Void, ListElementNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        ListElementNode::new, PMLNodesHandler::listElement, HTML_TAG, CSS_CLASS );
}
