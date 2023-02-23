package dev.pmlc.data.nodespec.block;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.ParagraphNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class ParagraphNodeSpec {

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


    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "p";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "paragraph" );

    public static final @NotNull PMLNodeSpec<Void, ParagraphNode> NODE =
        PMLNodeSpecCreator.createForBlockNodeWithInlineChildNodes (
            NAME, ATTRIBUTES, true,
            DOCUMENTATION, ParagraphNode::new, PMLNodesHandler::paragraph, HTML_TAG, CSS_CLASS );
}
