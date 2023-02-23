package dev.pmlc.data.nodespec.block.chapter;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.chapter.TitleNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class TitleNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "title" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Chapter Title",
        "A title for a chapter. This node must be the first child-node of a chapter. The text of this node is used in the table of contents.",
        """
            [ch [title Some [i Big] Advantages]
                 ...
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "h2";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "chapter-title" );

    public static final @NotNull PMLNodeSpec<Void, TitleNode> NODE =
        PMLNodeSpecCreator.createForBlockNodeWithInlineChildNodes (
            NAME, ATTRIBUTES, true,
            DOCUMENTATION, TitleNode::new, PMLNodesHandler::chapterTitle, HTML_TAG, CSS_CLASS );
}
