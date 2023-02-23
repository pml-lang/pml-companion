package dev.pmlc.commands;

import dev.pdml.commands.pdmltoxml.PdmlToXMLCommand;
import dev.pdml.commands.standalone.PdmlToStandaloneCommand;
import dev.pmlc.commands.pmltohtml.PMLToHTMLCommand;
import dev.pmlc.converter.pmltohtml.PMLToHTMLConverter;
import dev.pmlc.data.PmlcVersion;
import dev.pp.basics.annotations.NotNull;
import dev.pp.commands.command.CommandSpecs;
import dev.pp.commands.errors.CLIExceptionHandler;
import dev.pp.commands.picocli.PicocliCommandLineExecutor;

import java.nio.file.Path;

public class PMLCommands {

    @SuppressWarnings ( {"unchecked", "rawtypes"} )
    /* TODO
        add PMLToXMLCommand (must provide node specs to PDML parser)
        add command to convert PML documents (must provide node specs to PDML parser)
     */
    public static final @NotNull CommandSpecs COMMANDS = new CommandSpecs ()
        .add ( PMLToHTMLCommand.COMMAND_SPEC )
        .add ( PMLInfoCommand.COMMAND )
        .add ( PMLVersionCommand.COMMAND )
        .add ( PdmlToXMLCommand.COMMAND_SPEC )
        .add ( PdmlToStandaloneCommand.COMMAND_SPEC )
        .add ( ExportMetaDataCommand.COMMAND )
        .add ( CreateNodesReferenceManualCommand.COMMAND )
        .add ( CreateCommandsReferenceManualCommand.COMMAND );

    public static int runCommand ( @NotNull String[] args ) {

        return PicocliCommandLineExecutor.executeCommand ( args, PmlcVersion.VERSION_TEXT, PMLCommands.COMMANDS );
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
