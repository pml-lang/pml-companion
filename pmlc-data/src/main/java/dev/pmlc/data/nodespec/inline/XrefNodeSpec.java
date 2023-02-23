package dev.pmlc.data.nodespec.inline;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.XrefNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableParameterSpecs;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class XrefNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "xref" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Cross-Reference",
        "A cross-reference (link) to another node in the same document.",
        """
        [ch (id=solution) [title A Beautiful Solution]
            text text text
        ]

        [ch [title Conclusion]
            As we saw in chapter [xref (ref_id=solution)], ...
            ...
            As we saw [xref (ref_id=solution) previously], ...
        ]
        """ );

    @Deprecated
    public static final @NotNull ParameterSpec<String> REFERENCED_NODE_ID_ATTRIBUTE__OLD_NAME = new ParameterSpec.Builder<> (
        "node_id", CommonDataTypes.STRING )
        .documentation ( "Referenced Node Id",
            "The identifier of the node that is referenced.",
            "node_id = chapter_1" )
        .build();

    public static final @NotNull ParameterSpec<String> REFERENCED_NODE_ID_ATTRIBUTE = new ParameterSpec.Builder<> (
        "ref_id", CommonDataTypes.STRING )
        .documentation ( "Referenced Node Id",
            "The identifier of the node that is referenced.",
            "node_id = chapter_1" )
        .build();

    @Deprecated
    public static final @NotNull ParameterSpec<String> EXPLICIT_TEXT_ATTRIBUTE__REMOVED = new ParameterSpec.Builder<> (
        "text", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "Text",
            """
                The text to be displayed for the cross-reference.
                If this attribute is omitted, then the title of the target node is displayed. If the target node doesn't have a title, then the target node's identifier is displayed instead.""",
            "text = \"Hello World example\"" )
        .build();

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = new MutableParameterSpecs()
        .add ( REFERENCED_NODE_ID_ATTRIBUTE )
        .makeImmutable();

    public static final @NotNull String HTML_TAG = "a";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "xref" );

    public static final @NotNull PMLNodeSpec<Void, XrefNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        XrefNode::new, PMLNodesHandler::xref, HTML_TAG, CSS_CLASS );
}
