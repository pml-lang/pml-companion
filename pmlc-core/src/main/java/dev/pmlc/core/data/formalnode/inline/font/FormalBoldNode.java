package dev.pmlc.core.data.formalnode.inline.font;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.font.BoldNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalBoldNode {

    public static final @NotNull NodeName NAME = new NodeName ( "b" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Bold Text",
        null,
        "This is a [b huge] advantage." );

    public static final @NotNull String HTML_TAG = "b";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "bold" );

    public static final @NotNull FormalPMLNode<Void, BoldNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        BoldNode::new, PMLNodesHandler::bold, HTML_TAG, CSS_CLASS );
}
