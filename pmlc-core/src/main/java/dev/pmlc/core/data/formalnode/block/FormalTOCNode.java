package dev.pmlc.core.data.formalnode.block;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.block.TOCNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalTOCNode {

    public static final @NotNull NodeName NAME = new NodeName ( "toc" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "TOC Entry",
        "",
        "" );

    public static final @Nullable FormalParameters ATTRIBUTES = null;

    public static final @NotNull String HTML_TAG = "li";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "toc-leaf-node" );

    public static final @NotNull FormalPMLNode<Void, TOCNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        TOCNode::new,
        PMLNodesHandler::TOC,
        HTML_TAG, CSS_CLASS );
}
