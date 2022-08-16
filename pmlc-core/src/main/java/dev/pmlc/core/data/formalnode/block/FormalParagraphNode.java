package dev.pmlc.core.data.formalnode.block;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.ParagraphNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalParagraphNode {

    public static final @NotNull NodeName NAME = new NodeName ( "p" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Paragraph",
        """
            A paragraph is a set of one or more sentences.
            It is not required to embed a paragraph within a node. Text not explicitly embedded in a node is automatically converted to a paragraph node.
            A double-new-line creates a paragraph break. All other whitespace is converted to a single space. Hence, two sentences separated by a single new-line will result in a single paragraph composed of two sentences.""",
        """
            First sentence of first paragraph. Second sentence of first paragraph.

            First sentence of second paragraph.
            Second sentence of second paragraph.
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            [p Third paragraph.]
            """ );


    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "p";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "paragraph" );

    public static final @NotNull FormalPMLNode<Void, ParagraphNode> NODE =
        FormalPMLNodeCreator.createForBlockNodeWithInlineChildNodes (
            NAME, ATTRIBUTES, true,
            DOCUMENTATION, ParagraphNode::new, PMLNodesHandler::paragraph, HTML_TAG, CSS_CLASS );
}
