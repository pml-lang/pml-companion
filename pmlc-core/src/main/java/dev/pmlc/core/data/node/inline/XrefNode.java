package dev.pmlc.core.data.node.inline;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.FormalXrefNode;
import dev.pmlc.core.data.node.block.chapter.ChapterNode;
import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.Parameters;

public class XrefNode extends PMLInlineNode {

    private @NotNull String referencedNodeId = "none";
    public @NotNull String getReferencedNodeId() { return referencedNodeId; }
    // public void setReferencedNodeId ( @NotNull String referencedNodeId ) { this.referencedNodeId = referencedNodeId; }

    // value is set in PMLParserEventHandler
    private @Nullable PMLBlockNode referencedNode = null;
    @Nullable public PMLBlockNode getReferencedNode() { return referencedNode; }
    public void setReferencedNode ( @NotNull PMLBlockNode referencedNode ) { this.referencedNode = referencedNode; }

    private @Nullable String explicitText = null;
    public @Nullable String getExplicitText() { return explicitText; }
    // public void setExplicitText ( @Nullable String explicitText ) { this.explicitText = explicitText; }


    public XrefNode() { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, XrefNode> getFormalNode() { return FormalXrefNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        super.createAttributes ( parameters );

        assert parameters != null;
        referencedNodeId = parameters.getNonNull ( FormalXrefNode.REFERENCED_NODE_ID_ATTRIBUTE );
        explicitText = parameters.getNullable ( FormalXrefNode.EXPLICIT_TEXT_ATTRIBUTE );
    }

    public @NotNull String getText() {

        if ( explicitText != null ) return explicitText;

        // retrieve title from referencedNode (see rules in FormalXrefNode)
        if ( referencedNode != null ) {
            @Nullable String title = null;
            if ( referencedNode instanceof ChapterNode chapterNode ) {
                title = chapterNode.getTitleText();
            } else if ( referencedNode instanceof DocumentNode documentNode ) {
                title = documentNode.getTitleText();
            }
            if ( title != null ) return title;
        }

        return referencedNodeId;
    }
}
