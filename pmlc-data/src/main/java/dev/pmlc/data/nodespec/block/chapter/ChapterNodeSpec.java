package dev.pmlc.data.nodespec.block.chapter;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.chapter.ChapterNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class ChapterNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "ch" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Chapter",
        """
            A chapter or sub-chapter in the document.
            A chapter can have any number of sub-chapters. Chapters can be nested to any level.

            Note: The idiomatic way of identifying a chapter for cross-referencing is to assign an 'id' to the chapter node (and not to the chapter's title or subtitle node).""",
        """
            [ch (id=intro) [title Introduction]
                 [ch [title What is This About?]
                     blah blah blah
                 ]
                 [ch [title Why is This Important?]
                     blah blah blah
                 ]
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "section";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "chapter" );

    public static final @NotNull PMLNodeSpec<Void, ChapterNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        ChapterNode::new,
        PMLNodesHandler::chapter,
        HTML_TAG, CSS_CLASS );
}
