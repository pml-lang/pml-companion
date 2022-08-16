package dev.pmlc.core.data.formalnode.block.chapter;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.chapter.TitleNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalTitleNode {

    public static final @NotNull NodeName NAME = new NodeName ( "title" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Chapter Title",
        "A title for a chapter. This node must be the first child-node of a chapter. The text of this node is used in the table of contents.",
        """
            [ch [title Some [i Big] Advantages]
                 ...
            ]""" );

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "h2";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "chapter-title" );

    public static final @NotNull FormalPMLNode<Void, TitleNode> NODE =
        FormalPMLNodeCreator.createForBlockNodeWithInlineChildNodes (
            NAME, ATTRIBUTES, true,
            DOCUMENTATION, TitleNode::new, PMLNodesHandler::chapterTitle, HTML_TAG, CSS_CLASS );
}
