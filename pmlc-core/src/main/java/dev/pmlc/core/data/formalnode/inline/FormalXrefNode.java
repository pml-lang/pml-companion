package dev.pmlc.core.data.formalnode.inline;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.XrefNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalXrefNode {

    public static final @NotNull NodeName NAME = new NodeName ( "xref" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Cross-Reference",
        "A cross-reference (link) to another node in the same document.",
        """
        [ch (id=solution) [title A Beautiful Solution]
            text text text
        ]

        [ch [title Conclusion]
            As we saw in chapter [xref node_id=solution], it is ...
        ]
        """ );

    public static final @NotNull FormalParameter<String> REFERENCED_NODE_ID_ATTRIBUTE = new FormalParameter.Builder<> (
        "node_id", CommonDataTypes.STRING )
        .documentation ( "Referenced Node Id",
            "The identifier of the node that is referenced.",
            "node_id = chapter_1" )
        .build();

    public static final @NotNull FormalParameter<String> EXPLICIT_TEXT_ATTRIBUTE = new FormalParameter.Builder<> (
        "text", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "Text",
            """
                The text to be displayed for the cross-reference.
                If this attribute is omitted, then the title of the target node is displayed. If the target node doesn't have a title, then the target node's identifier is displayed instead.""",
            "text = \"Hello World example\"" )
        .build();

    public static final @NotNull FormalParameters ATTRIBUTES = new FormalParameters()
        .add ( REFERENCED_NODE_ID_ATTRIBUTE )
        .add ( EXPLICIT_TEXT_ATTRIBUTE );

    public static final @NotNull String HTML_TAG = "a";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "xref" );

    public static final @NotNull FormalPMLNode<Void, XrefNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        XrefNode::new, PMLNodesHandler::xref, HTML_TAG, CSS_CLASS );
}
