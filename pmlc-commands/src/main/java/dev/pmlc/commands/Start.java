package dev.pmlc.commands;

import dev.pmlc.ext.PMLCResources;
import dev.pp.basics.utilities.SimpleLogger;

import java.io.IOException;

public class Start {

    public static void main ( String[] args ) {

        // https://clig.dev/#guidelines

        init();

        int exitCode;
        if ( args.length == 1 && args[0].toLowerCase().endsWith ( ".pml" ) ) { // e.g. pmlc index.pml
            exitCode = PMLCommands.PMLToHTML ( args[0] );
        } else {
            exitCode = PMLCommands.runCommand ( args );
        }

        System.exit ( exitCode );
    }

    private static void init() {

        try {
            SimpleLogger.useSimpleFormat();
            // SimpleLogger.setLevel ( SimpleLogger.LogLevel.DEBUG );
            PMLCResources.createResourcesDirectoryIfNotExists ();
        } catch ( IOException e ) {
            // CLIExceptionHandler.handleThrowable ( e );
            e.printStackTrace ();
            System.exit ( 1 );
        }
    }
}
