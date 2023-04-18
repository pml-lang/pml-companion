package dev.pmlc.data.nodespec.block.chapter;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.chapter.SubtitleNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class SubtitleNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "subtitle" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Subtitle",
        "A subtitle for a document or a chapter, displayed on a separate line after a title. If present, this node must follow a 'title' node (document or chapter title). The subtitle is not displayed in the table of contents.",
        """
            [ch [title My Life as a Gardener]
                [subtitle Why I Left the Corporate World]
                ...
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "h2";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "chapter-subtitle" );

    public static final @NotNull PMLNodeSpec<Void, SubtitleNode> NODE =
        PMLNodeSpecCreator.createForBlockNodeWithInlineChildNodes (
            NAME, ATTRIBUTES, true,
            DOCUMENTATION, SubtitleNode::new, PMLNodesHandler::chapterSubtitle, HTML_TAG, CSS_CLASS );
}
