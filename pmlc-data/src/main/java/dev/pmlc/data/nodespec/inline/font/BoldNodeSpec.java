package dev.pmlc.data.nodespec.inline.font;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.font.BoldNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class BoldNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "b" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Bold Text",
        null,
        "This is a [b huge] advantage." );

    public static final @NotNull String HTML_TAG = "b";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "bold" );

    public static final @NotNull PMLNodeSpec<Void, BoldNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        BoldNode::new, PMLNodesHandler::bold, HTML_TAG, CSS_CLASS );
}
