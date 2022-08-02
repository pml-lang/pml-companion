package dev.pmlc.core.data.formalnode.inline.font;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.font.SubscriptNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalSubscriptNode {

    public static final @NotNull NodeName NAME = new NodeName ( "sub" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Subscript Text",
        "Subscript text is rendered in a smaller font below the normal text line.",
        """
       Water: H[sub 2]O

       This is [sub subscript], and this is [sup superscript]""" );

    public static final @NotNull String HTML_TAG = "sub";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "subscript" );

    public static final @NotNull FormalPMLNode<Void, SubscriptNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        SubscriptNode::new, PMLNodesHandler::subscript, HTML_TAG, CSS_CLASS );
}
