package dev.pmlc.core.data.formalnode.block.chapter;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.chapter.SubtitleNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalSubtitleNode {

    public static final @NotNull NodeName NAME = new NodeName ( "subtitle" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Chapter Subtitle",
        "A subtitle for a chapter, displayed on a separate line after a chapter's title. If present, this node must follow a 'title' (chapter title) node. The subtitle is not displayed in the table of contents.",
        """
            [ch [title My Life as a Gardener]
                [subtitle Why I Left the Corporate World]
                ...
            ]""" );

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "h2";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "chapter-subtitle" );

    public static final @NotNull FormalPMLNode<Void, SubtitleNode> NODE =
        FormalPMLNodeCreator.createForBlockNodeWithInlineChildNodes (
            NAME, ATTRIBUTES, true,
            DOCUMENTATION, SubtitleNode::new, PMLNodesHandler::chapterSubtitle, HTML_TAG, CSS_CLASS );
}
