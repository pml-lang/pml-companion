package dev.pmlc.data.nodespec.block.admonition;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.admonition.NoteNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class NoteNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "note" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Note",
        "A note is an admonition with label 'Note'.",
        "[note Please remember that ...]" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = AdmonitionNodeSpec.HTML_TAG;

    public static final @NotNull String CSS_CLASS = AdmonitionNodeSpec.CSS_CLASS;

    public static final @NotNull PMLNodeSpec<Void, NoteNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        NoteNode::new, PMLNodesHandler::note, HTML_TAG, CSS_CLASS );
}
