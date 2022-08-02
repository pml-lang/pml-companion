package dev.pmlc.core.data.formalnode.inline.font;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.font.SuperscriptNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalSuperscriptNode {

    public static final @NotNull NodeName NAME = new NodeName ( "sup" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Superscript Text",
        "Superscript text is rendered in a smaller font above the normal text line.",
        """
        2[sup 4] = 16

        This is [sup superscript], and this is [sub subscript]""" );

    public static final @NotNull String HTML_TAG = "sup";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "superscript" );

    public static final @NotNull FormalPMLNode<Void, SuperscriptNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        SuperscriptNode::new, PMLNodesHandler::superscript, HTML_TAG, CSS_CLASS );
}
