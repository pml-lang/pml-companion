package dev.pmlc.core.data.formalnode.inline;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.TextNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalTextNode {

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

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "text" );

    public static final @NotNull FormalPMLNode<String, TextNode> NODE = FormalPMLNodeCreator.createForRawTextInlineNode (
        NAME, null, true, DOCUMENTATION,
        TextNode::new, PMLNodesHandler::text, HTML_TAG, CSS_CLASS );
}
