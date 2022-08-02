package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.FormalNoteNode;
import dev.pp.basics.annotations.NotNull;

public class NoteNode extends PMLBlockNode {

    public NoteNode () { super(); }

    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, NoteNode> getFormalNode() { return FormalNoteNode.NODE; }

    public @NotNull String getLabel() { return "Note"; }
}
