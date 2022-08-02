package dev.pmlc.core.data.node.block;

import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.PMLNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.list.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class PMLBlockNode extends PMLNode {


    protected @Nullable String nodeId = null;
    public @Nullable String getNodeId() { return nodeId; }
    public void setNodeId ( @Nullable String nodeId ) { this.nodeId = nodeId; }

    protected @Nullable List<PMLBlockNode> blockChildNodes = null;
    public @Nullable List<? extends PMLBlockNode> getBlockChildNodes () { return blockChildNodes; }
    public void setBlockChildNodes ( @Nullable List<PMLBlockNode> blockChildNodes ) {
        this.blockChildNodes = blockChildNodes;
    }
    public boolean hasBlockChildNodes() { return blockChildNodes != null && ! blockChildNodes.isEmpty(); }



    protected PMLBlockNode() { super(); }

/*
    protected PMLBlockNode (
        @Nullable String nodeId,
        @Nullable Map<String, String> HTMLAttributes,
        @Nullable List<PMLBlockNode> blockChildNodes ) {

        this.nodeId = nodeId;
        this.HTMLAttributes = HTMLAttributes;
        this.blockChildNodes = blockChildNodes;
    }

 */


    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        if ( parameters == null ) return;

        String nodeId = parameters.getNullable ( SharedFormalNodeAttributes.ID_ATTRIBUTE.getName() );
        if ( nodeId != null ) setNodeId ( nodeId );
    }

    public void addBlockChildNode ( @NotNull PMLBlockNode node ) {

        if ( blockChildNodes == null ) blockChildNodes = new ArrayList<>();
        blockChildNodes.add ( node );
    }

    public @Nullable List<? extends PMLNode> getChildNodes() {

        if ( blockChildNodes != null ) {
            assert inlineChildNodes == null;
            return blockChildNodes;
        } else {
            return inlineChildNodes;
        }
    }

    public void forEachChildNode ( @NotNull Consumer<PMLNode> consumer ) {

        List<? extends PMLNode> childNodes = getChildNodes();

        if ( childNodes == null ) return;

        for ( PMLNode childNode : childNodes ) {
            consumer.accept ( childNode );
        }
    }

    /* not used
    public @Nullable PMLBlockNode findNodeById ( @NotNull String id ) {

        PMLNode found = findFirstChildNodeInTree ( true, node -> {
            if ( node instanceof PMLBlockNode blockNode ) {
                @Nullable String nodeId = blockNode.getNodeId();
                if ( nodeId != null && nodeId.equals ( id ) ) return true;
            }
            return false;
        } );
        return found == null ? null : (PMLBlockNode) found;
    }
    */
}
