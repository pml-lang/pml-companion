package dev.pmlc.data.nodespec.block.footnote;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.footnote.FootnotesPlaceholderNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FootnotesPlaceholderNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "fnotes" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Footnotes Placeholder",
        """
            Node 'fnotes' is used as a placeholder for printing/displaying footnotes.
            All footnotes not yet displayed will be displayed as soon as an 'fnotes' node is encountered. This includes inline and non-inline footnotes.
            A document can have any number of 'fnotes' nodes. This allows different sets of footnotes to be printed at different locations. For example, instead of displaying all footnotes at the end of the document, the footnotes contained in each chapter could be displayed at the end of each chapter.""",
        """
            [ch [title Chapter 1]
                text [fnote footnote 1] text

                text [fnote footnote 2] text

                [header Footnotes of Chapter 1]
                [fnotes]
            ]

            [ch [title Chapter 2]
                text [fnote_ref did=f3] text

                text [fnote footnote 4] text

                [fnote_def (id=f3)
                    footnote 3
                ]

                [header Footnotes of Chapter 2]
                [fnotes]
            ]
            """ );


    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "footnotes" );

    public static final @NotNull PMLNodeSpec<Void, FootnotesPlaceholderNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        FootnotesPlaceholderNode::new, PMLNodesHandler::footnotes, HTML_TAG, CSS_CLASS );
}
