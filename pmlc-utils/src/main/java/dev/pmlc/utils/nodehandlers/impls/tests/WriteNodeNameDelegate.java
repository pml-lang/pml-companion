package dev.pmlc.utils.nodehandlers.impls.tests;

import dev.pmlc.data.node.PMLNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pmlc.utils.nodehandlers.impls.DelegatingNodesHandler;
import dev.pmlc.utils.nodehandlers.impls.NodesHandlerDelegate;
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

        writer.write ( node.getNodeSpec ().getName().toString() );
        writer.write ( StringConstants.OS_NEW_LINE );
    }
}
