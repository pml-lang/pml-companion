package dev.pmlc.data.nodespec.inline;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.TextNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class TextNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "text" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Text",
        """
        A node containing text.
        Normally this node is not used in hand-written documents, because free text is implicitly contained in a 'text' node.""",
        """
        [text All is very well.]
        All is very well.
        """ );

    public static final @NotNull String HTML_TAG = "";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "text" );

    public static final @NotNull PMLNodeSpec<String, TextNode> NODE = PMLNodeSpecCreator.createForRawTextInlineNode (
        NAME, null, true, DOCUMENTATION,
        TextNode::new, PMLNodesHandler::text, HTML_TAG, CSS_CLASS );
}
