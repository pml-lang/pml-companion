package dev.pmlc.data.nodespec.block;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.block.TOCNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class TOCNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "toc" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "TOC Entry",
        "",
        "" );

    public static final @Nullable ParameterSpecs<?> ATTRIBUTES = null;

    public static final @NotNull String HTML_TAG = "li";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "toc-leaf-node" );

    public static final @NotNull PMLNodeSpec<Void, TOCNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        TOCNode::new,
        PMLNodesHandler::TOC,
        HTML_TAG, CSS_CLASS );
}
