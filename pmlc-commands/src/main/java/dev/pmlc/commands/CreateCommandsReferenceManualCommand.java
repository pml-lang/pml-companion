package dev.pmlc.commands;

import dev.pmlc.utils.referencemanual.CommandsReferenceManualCreator;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.commands.command.CommandSpec;
import dev.pp.parameters.parameters.Parameters;

public class CreateCommandsReferenceManualCommand {

    public static final @NotNull CommandSpec<Void,Void> COMMAND = CommandSpec.<Void,Void>builder (
        "create_commands_reference_manual", CreateCommandsReferenceManualCommand::execute )
        .alternativeName ( "ccrm" )
        .documentation (
             "Create Commands Reference Manual",
            """
            This command creates the 'PML Commands Reference Manual'.
            The auto-generated PML file is stored in sub-directory 'input' of the current working directory.
            The output is stored in sub-directory 'output' of the current working directory.""",
            "pmlc create_commands_manual" )
        .build();

    public static Void execute ( @Nullable Parameters<?> parameters ) throws Exception {

        new CommandsReferenceManualCreator().createManual ( PMLCommands.COMMANDS );

        return null;
    }
}
