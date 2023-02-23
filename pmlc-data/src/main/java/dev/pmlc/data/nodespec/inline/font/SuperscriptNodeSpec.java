package dev.pmlc.data.nodespec.inline.font;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.font.SuperscriptNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class SuperscriptNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "sup" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Superscript Text",
        "Superscript text is rendered in a smaller font above the normal text line.",
        """
        2[sup 4] = 16

        This is [sup superscript], and this is [sub subscript]""" );

    public static final @NotNull String HTML_TAG = "sup";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "superscript" );

    public static final @NotNull PMLNodeSpec<Void, SuperscriptNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        SuperscriptNode::new, PMLNodesHandler::superscript, HTML_TAG, CSS_CLASS );
}
