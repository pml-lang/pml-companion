package dev.pmlc.utils.nodehandlers;

import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PMLNodesHandlerRegistrar {

    /*
    public static interface OutputWriterSupplier {

        @NotNull PMLNodesHandler get ( @NotNull Writer writer );
    }

    private static final @NotNull Map<String, OutputWriterSupplier> writers = new HashMap<>();
     */

    private static final @NotNull Map<String, PMLNodesHandler> handlers = new HashMap<>();

/*
    static {
        addWriter ( new HTMLOutputWriter() );
    }

    public static boolean hasWriter ( String format ) {
        return writers.containsKey ( format );
    }

    public static PMLOutputWriter getWriter ( String format ) {

        if ( ! hasWriter ( format ) ) throw new IllegalArgumentException (
            "A writer for format '" + format + "' is not registered.");

        return writers.get ( format );
    }

    public static void addWriter ( PMLOutputWriter writer ) {

        String format = writer.getFormat();
        if ( hasWriter ( format ) ) throw new IllegalArgumentException (
            "A writer for format '" + format + "' is already registered.");

        addOrReplaceWriter ( writer );
    }

    public static void replaceWriter ( PMLOutputWriter writer ) {

        String format = writer.getFormat();
        if ( ! hasWriter ( format ) ) throw new IllegalArgumentException (
            "A writer for format '" + format + "' is not registered.");

        addOrReplaceWriter ( writer );
    }

    public static void addOrReplaceWriter ( PMLOutputWriter writer ) {
        writers.put ( writer.getFormat(), writer );
    }
*/
}
