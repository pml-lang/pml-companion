package dev.pmlc.data.node.block.admonition;

import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.node.block.ParagraphNode;
import dev.pmlc.data.node.inline.TextNode;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.admonition.NoteNodeSpec;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.token.TextToken;

public class NoteNode extends PMLBlockNode {


    private static final @NotNull AdmonitionLabelNode LABEL_NODE = createLabelNode();

    private static @NotNull AdmonitionLabelNode createLabelNode() {

        TextNode textNode = new TextNode ( new TextToken ( "Note", null ) );
        ParagraphNode paragraphNode = new ParagraphNode();
        paragraphNode.addInlineChildNode ( textNode );
        AdmonitionLabelNode labelNode = new AdmonitionLabelNode();
        labelNode.addBlockChildNode ( paragraphNode );
        return labelNode;
    }



    public NoteNode() {
        super();
    }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, NoteNode> getNodeSpec() { return NoteNodeSpec.NODE; }

    public @NotNull AdmonitionLabelNode getLabelNode() { return LABEL_NODE; }
}
