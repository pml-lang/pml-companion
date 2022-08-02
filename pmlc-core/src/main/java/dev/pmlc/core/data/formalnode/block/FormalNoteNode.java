package dev.pmlc.core.data.formalnode.block;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.NoteNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalNoteNode {

    public static final @NotNull NodeName NAME = new NodeName ( "note" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Note",
        "A note is an admonition with label 'Note'.",
        "[note Please remember that ...]" );

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = FormalAdmonitionNode.HTML_TAG;

    public static final @NotNull String CSS_CLASS = FormalAdmonitionNode.CSS_CLASS;

    public static final @NotNull FormalPMLNode<Void, NoteNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        NoteNode::new, PMLNodesHandler::note, HTML_TAG, CSS_CLASS );
}
