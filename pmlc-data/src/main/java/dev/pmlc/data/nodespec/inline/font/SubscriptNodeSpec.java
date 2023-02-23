package dev.pmlc.data.nodespec.inline.font;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.font.SubscriptNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class SubscriptNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "sub" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Subscript Text",
        "Subscript text is rendered in a smaller font below the normal text line.",
        """
       Water: H[sub 2]O

       This is [sub subscript], and this is [sup superscript]""" );

    public static final @NotNull String HTML_TAG = "sub";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "subscript" );

    public static final @NotNull PMLNodeSpec<Void, SubscriptNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        SubscriptNode::new, PMLNodesHandler::subscript, HTML_TAG, CSS_CLASS );
}
