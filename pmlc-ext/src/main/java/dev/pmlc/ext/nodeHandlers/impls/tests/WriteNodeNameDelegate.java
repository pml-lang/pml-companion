package dev.pmlc.ext.nodeHandlers.impls.tests;

import dev.pmlc.core.data.node.PMLNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pmlc.ext.nodeHandlers.impls.DelegatingNodesHandler;
import dev.pmlc.ext.nodeHandlers.impls.NodesHandlerDelegate;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.StringConstants;

import java.io.IOException;
import java.io.Writer;

public class WriteNodeNameDelegate implements NodesHandlerDelegate {


    public static PMLNodesHandler getNodesHandler ( @NotNull Writer writer ) {
        return new DelegatingNodesHandler ( new WriteNodeNameDelegate ( writer ) );
    }


    private final @NotNull Writer writer;


    public WriteNodeNameDelegate ( @NotNull Writer writer ) {
        this.writer = writer;
    }


    public @NotNull String getName() { return "node_names_writer"; }

    public void start() {}

    public void end() {}

    public void handleNode ( @NotNull PMLNode node ) throws IOException {

        writer.write ( node.getFormalNode().getName().toString() );
        writer.write ( StringConstants.OS_NEW_LINE );
    }
}
