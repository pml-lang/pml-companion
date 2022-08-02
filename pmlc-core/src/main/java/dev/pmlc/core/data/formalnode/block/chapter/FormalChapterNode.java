package dev.pmlc.core.data.formalnode.block.chapter;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.chapter.ChapterNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalChapterNode {

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

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "section";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "chapter" );

    public static final @NotNull FormalPMLNode<Void, ChapterNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        ChapterNode::new,
        PMLNodesHandler::chapter,
        HTML_TAG, CSS_CLASS );
}
