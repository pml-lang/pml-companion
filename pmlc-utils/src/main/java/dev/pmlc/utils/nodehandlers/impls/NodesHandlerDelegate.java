package dev.pmlc.utils.nodehandlers.impls;

import dev.pmlc.data.node.PMLNode;
import dev.pp.basics.annotations.NotNull;

public interface NodesHandlerDelegate {

    @NotNull String getName();

    void start() throws Exception;

    void end() throws Exception;

    void handleNode ( @NotNull PMLNode node ) throws Exception;
}
