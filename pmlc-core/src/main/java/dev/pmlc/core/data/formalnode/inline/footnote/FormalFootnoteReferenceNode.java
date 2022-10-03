package dev.pmlc.core.data.formalnode.inline.footnote;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.footnote.FootnoteReferenceNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalFootnoteReferenceNode {

    public static final @NotNull NodeName NAME = new NodeName ( "fnote_ref" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Footnote Reference",
        """
            Node 'fnote_ref' is used to insert a footnote defined with an 'fnote_def' node.
            Attribute 'did' must be explicitly defined, and must be equal to the 'id' attribute defined in node 'fnote_def'.
            The same 'fnote_def' node can be re-used in several 'fnote_ref' nodes.
            """,
        """
            text [fnote_ref did=1] text

            Advice for life [fnote_ref did=2 text="Rodegast 1987"].

            text [fnote_ref did=1] [fnote_ref did=2] text

            [fnote_def (id=1)
                Text of [i footnote 1]
            ]

            [fnote_def (id=2)
                Rodegast, P. (1987). [i Emmanuel's Book: A Manual for Living Comfortably in the Cosmos.] Bantam.
            ]

            [header Footnotes]
            [fnotes]""" );

    public static final @NotNull FormalParameter<String> DEFINITION_ID_ATTRIBUTE = new FormalParameter.Builder<> (
        "did", CommonDataTypes.STRING )
        .documentation ( "Footnote Definition Id",
            "The identifier of the footnote definition.",
            "did = fn_7" )
        .build();

    public static final @NotNull FormalParameter<String> INLINE_TEXT_ATTRIBUTE = new FormalParameter.Builder<> (
        "text", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "Inline Text",
            """
            The text to be displayed inline.
            By default, the index in the list of footnotes is used.""",
            "text = \"Bloch, 2017\"" )
        .build();

    public static final @NotNull FormalParameters ATTRIBUTES = new FormalParameters()
        .add ( DEFINITION_ID_ATTRIBUTE )
        .add ( INLINE_TEXT_ATTRIBUTE );

    public static final @NotNull String HTML_TAG = "sup";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "footnote-ref" );

    public static final @NotNull FormalPMLNode<Void, FootnoteReferenceNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        FootnoteReferenceNode::new, PMLNodesHandler::footnoteReference, HTML_TAG, CSS_CLASS );
}
