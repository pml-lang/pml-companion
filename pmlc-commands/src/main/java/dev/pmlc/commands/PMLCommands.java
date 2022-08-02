package dev.pmlc.commands;

import dev.pdml.commands.pdmltoxml.PDMLToXMLCommand;
import dev.pdml.commands.standalone.StandaloneCommand;
import dev.pmlc.ext.PMLCVersion;
import dev.pmlc.commands.pmltohtml.PMLToHTMLCommand;
import dev.pmlc.ext.utilities.pmltohtml.PMLToHTMLConverter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.commands.command.FormalCommands;
import dev.pp.commands.errors.CLIExceptionHandler;
import dev.pp.commands.picocli.PicocliCommandLineExecutor;

import java.nio.file.Path;

public class PMLCommands {

    public static @NotNull FormalCommands COMMANDS = new FormalCommands()
        .add ( PMLToHTMLCommand.COMMAND )
        .add ( PMLInfoCommand.COMMAND )
        .add ( PMLVersionCommand.COMMAND )
        .add ( PDMLToXMLCommand.COMMAND )
        .add ( StandaloneCommand.COMMAND )
        .add ( ExportTagsCommand.COMMAND )
        .add ( CreateNodesReferenceManualCommand.COMMAND )
        .add ( CreateCommandsReferenceManualCommand.COMMAND );

    public static int runCommand ( @NotNull String[] args ) {

        return PicocliCommandLineExecutor.executeCommand ( args, PMLCVersion.VERSION_TEXT, PMLCommands.COMMANDS );
    }

    public static int PMLToHTML ( @NotNull String PMLInputFile ) {

        try {
            PMLToHTMLConverter.convertFile ( Path.of ( PMLInputFile ) );
            return 0;
        } catch ( Exception e ) {
            return CLIExceptionHandler.handleThrowable ( e );
        }
    }
}
