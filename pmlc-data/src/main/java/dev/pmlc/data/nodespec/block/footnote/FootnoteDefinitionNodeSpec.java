package dev.pmlc.data.nodespec.block.footnote;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.footnote.FootnoteDefinitionNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FootnoteDefinitionNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "fnote_def" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Footnote Definition",
        """
            'fnote_def' is used to define the content of a footnote.
            Every 'fnote_def' node must have a unique identifier, explicitly specified by attribute 'id'. This identifier is used in 'fnote_ref' nodes to insert a link to the footnote in the text, using attribute 'did'.
            Several references to the same footnote can exist in the document. This is done by re-using the identifier of an 'fnote_def' node in several 'fnote_ref' nodes.
            'fnote_def' nodes can be defined anywhere in the document, but they must be defined before the 'fnotes' node used to display the footnotes. For example, the content of all footnotes could be defined at the beginning or the end of the document. Or, footnotes could be defined at the end of the paragraph in which they are used.
            'fnote_def' nodes can contain block nodes (besides inline nodes), for example to include lists, tables, images etc. in a footnote.
            """,
        """
            [fnote_def (id=fn_1)
                Text of [i footnote 1]

                Second paragraph.
            ]

            [fnote_def (id=fn_2)
                [sim_table
                    ~~~
                    c11 | c12 | c13
                    c21 | c22 | c23
                    ~~~
                ]
            ]

            text [fnote_ref did=fn_1] text

            text [fnote_ref did=fn_2] text [fnote_ref did=fn_1] text

            [header Footnotes]
            [fnotes]""" );


    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "tr";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "footnote" );

    public static final @NotNull PMLNodeSpec<Void, FootnoteDefinitionNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        FootnoteDefinitionNode::new, PMLNodesHandler::footnoteDefinition, HTML_TAG, CSS_CLASS );
}
