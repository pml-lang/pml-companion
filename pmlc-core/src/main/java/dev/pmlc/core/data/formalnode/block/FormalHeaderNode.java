package dev.pmlc.core.data.formalnode.block;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.HeaderNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalHeaderNode {

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

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "header" );

    public static final @NotNull FormalPMLNode<Void, HeaderNode> NODE =
        FormalPMLNodeCreator.createForBlockNodeWithInlineChildNodes (
            NAME, ATTRIBUTES, true,
            DOCUMENTATION, HeaderNode::new, PMLNodesHandler::header, HTML_TAG, CSS_CLASS );
}
