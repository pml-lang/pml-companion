package dev.pmlc.commands;

import dev.pmlc.data.PMLConstants;
import dev.pp.basics.utilities.SimpleLogger;

public class Start {

    public static void main ( String[] args ) {

        // https://clig.dev/#guidelines

        init();

        int exitCode;
        if ( args.length == 1 && args[0].toLowerCase().endsWith ( PMLConstants.PML_FILE_NAME_EXTENSION_WITH_DOT ) ) { // e.g. pmlc index.pml
            exitCode = PMLCommands.PMLToHTML ( args[0] );
        } else {
            exitCode = PMLCommands.runCommand ( args );
        }

        System.exit ( exitCode );
    }

    private static void init() {
        SimpleLogger.useSimpleFormat();
    }
}
