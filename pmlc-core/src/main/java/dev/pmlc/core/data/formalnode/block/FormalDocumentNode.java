package dev.pmlc.core.data.formalnode.block;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalDocumentNode {

    public static final @NotNull NodeName NAME = new NodeName ( "doc" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Document",
        "Every PML document must start with a 'doc' node. It is the root node of the document.",
        """
        [doc [title A Nice Surprise]

            Author: Giovanni Spiridigliotsky

            Lorem ipsum blah blah blah
        ]""" );

/*
    // TODO? default_value_supplier = { ty_PML_document_node.TOC_title.default },
    public static final @NotNull FormalParameter<String> TOC_TITLE_ATTRIBUTE = new FormalParameter<> (
        "TOC_title",
        null,
        CommonDataTypes.STRING_OR_NULL,
        () -> "Table of Contents",
        1,
        () -> new SimpleDocumentation (
            "TOC Title",
            "The title of the table of contents.",
            "TOC_title = Contents" ) );

    public static final @NotNull FormalParameter<TOCPosition> TOC_POSITION_ATTRIBUTE = new FormalParameter<> (
        "TOC_position",
        null,
        new EnumDataType<> ( TOCPosition.class ),
        () -> TOCPosition.LEFT,
        2,
        () -> new SimpleDocumentation (
            "TOC Position",
            "The position of the table of contents ('left', 'top', or 'none').",
            "TOC_position = top" ) );

    public static final @NotNull FormalParameter<SourceCodeHighlighter> HIGHLIGHTER_ATTRIBUTE = new FormalParameter<> (
        "highlighter",
        null,
        new EnumDataType<> ( SourceCodeHighlighter.class ),
        () -> SourceCodeHighlighter.NONE,
        3,
        () -> new SimpleDocumentation (
            "Source Code Syntax Highlighter",
            "The name of the syntax highlighter used to highlight source code. Currently supported values are 'highlightjs', 'prism' and 'none'. Default is 'none', which means that source code highlighting is disabled.",
            "highlighter = prism" ) );

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute()
        .add ( TOC_TITLE_ATTRIBUTE )
        .add ( TOC_POSITION_ATTRIBUTE )
        .add ( HIGHLIGHTER_ATTRIBUTE );
*/

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "article";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "doc-text" );

    public static final @NotNull FormalPMLNode<Void, DocumentNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        DocumentNode::new, PMLNodesHandler::document, HTML_TAG, CSS_CLASS );
}
