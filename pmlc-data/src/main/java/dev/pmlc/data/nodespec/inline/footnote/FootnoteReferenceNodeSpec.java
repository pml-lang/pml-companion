package dev.pmlc.data.nodespec.inline.footnote;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.footnote.FootnoteReferenceNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableParameterSpecs;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FootnoteReferenceNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "fnote_ref" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Footnote Reference",
        """
            Node 'fnote_ref' is used to insert a footnote defined with an 'fnote_def' node.
            Attribute 'did' must be explicitly defined, and must be equal to the 'id' attribute defined in node 'fnote_def'.
            The same 'fnote_def' node can be re-used in several 'fnote_ref' nodes.
            """,
        """
            text [fnote_ref did=f1] text

            Advice for life [fnote_ref did=f2 text="Rodegast 1987"].

            text [fnote_ref did=f1] [fnote_ref did=f2] text

            [fnote_def (id=f1)
                Text of [i footnote 1]
            ]

            [fnote_def (id=f2)
                Rodegast, P. (1987). [i Emmanuel's Book: A Manual for Living Comfortably in the Cosmos.] Bantam.
            ]

            [header Footnotes]
            [fnotes]""" );

    public static final @NotNull ParameterSpec<String> DEFINITION_ID_ATTRIBUTE = new ParameterSpec.Builder<> (
        "did", CommonDataTypes.STRING )
        .documentation ( "Footnote Definition Id",
            "The identifier of the footnote definition.",
            "did = fn_7" )
        .build();

    public static final @NotNull ParameterSpec<String> INLINE_TEXT_ATTRIBUTE = new ParameterSpec.Builder<> (
        "text", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "Inline Text",
            """
            The text to be displayed inline.
            By default, the index in the list of footnotes is used.""",
            "text = \"Bloch, 2017\"" )
        .build();

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = new MutableParameterSpecs()
        .add ( DEFINITION_ID_ATTRIBUTE )
        .add ( INLINE_TEXT_ATTRIBUTE )
        .makeImmutable();

    public static final @NotNull String HTML_TAG = "sup";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "footnote-ref" );

    public static final @NotNull PMLNodeSpec<Void, FootnoteReferenceNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        FootnoteReferenceNode::new, PMLNodesHandler::footnoteReference, HTML_TAG, CSS_CLASS );
}
