package dev.pmlc.ext.nodeHandlers.impls.tests;

import dev.pmlc.core.data.node.inline.font.ItalicNode;
import dev.pmlc.ext.nodeHandlers.impls.DoNothingNodesHandler;
import dev.pp.basics.annotations.NotNull;

import java.io.Writer;

public class MyIncompleteTextOutputWriter1 extends DoNothingNodesHandler {

    private final @NotNull Writer writer;


    public MyIncompleteTextOutputWriter1 ( @NotNull Writer writer ) {
        this.writer = writer;
    }


    public @NotNull String getName () { return "text"; }


    // public void writeDocument ( @NotNull DocumentNode node ) throws IOException {}

    public void italic ( @NotNull ItalicNode node ) throws Exception {

        handleChildNodes ( node.getInlineChildNodes () );
    }
}
