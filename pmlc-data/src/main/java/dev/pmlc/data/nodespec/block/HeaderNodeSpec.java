package dev.pmlc.data.nodespec.block;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.HeaderNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class HeaderNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "header" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Header",
        "A header (small title) displayed on a separate line. A header is not included in the table of contents. Headers can be inserted anywhere in a document.",
        """
            [ch [title Advantages]
                [header Simple]
                ...
                [header Fast]
                ...
                [header Effective]
                ...
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "header" );

    public static final @NotNull PMLNodeSpec<Void, HeaderNode> NODE =
        PMLNodeSpecCreator.createForBlockNodeWithInlineChildNodes (
            NAME, ATTRIBUTES, true,
            DOCUMENTATION, HeaderNode::new, PMLNodesHandler::header, HTML_TAG, CSS_CLASS );
}
