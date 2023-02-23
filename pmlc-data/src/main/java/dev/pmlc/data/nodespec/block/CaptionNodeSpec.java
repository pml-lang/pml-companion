package dev.pmlc.data.nodespec.block;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.CaptionNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class CaptionNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "caption" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Caption",
        "A small title that is not part of the table of contents, typically displayed below a block element (image, video, table, etc.).",
        "[caption Figure 1: Top Brain Boosters]" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "caption" );

    public static final @NotNull PMLNodeSpec<Void, CaptionNode> NODE =
        PMLNodeSpecCreator.createForBlockNodeWithInlineChildNodes (
            NAME, ATTRIBUTES, true,
            DOCUMENTATION, CaptionNode::new, PMLNodesHandler::caption, HTML_TAG, CSS_CLASS );
}
