package dev.pmlc.utils.nodehandlers.impls.tests;

import dev.pmlc.data.node.block.DocumentNode;
import dev.pmlc.utils.nodehandlers.impls.ThrowingNodesHandler;
import dev.pp.basics.annotations.NotNull;

import java.io.Writer;

public class MyIncompleteTextOutputWriter2 extends ThrowingNodesHandler {

    private final @NotNull Writer writer;


    public MyIncompleteTextOutputWriter2 ( @NotNull Writer writer ) {
        this.writer = writer;
    }

    public @NotNull String getName () { return "text"; }


    public void document ( @NotNull DocumentNode node ) throws Exception {

        handleChildNodes ( node.getBlockChildNodes () );
    }

    // public void writeItalic ( @NotNull ItalicNode node ) throws IOException
}
