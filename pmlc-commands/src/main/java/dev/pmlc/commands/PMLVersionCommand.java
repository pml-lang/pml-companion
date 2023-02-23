package dev.pmlc.commands;

import dev.pmlc.data.PmlcVersion;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.commands.command.CommandSpec;
import dev.pp.parameters.parameters.Parameters;

public class PMLVersionCommand {

    public static final @NotNull CommandSpec<Void,Void> COMMAND = CommandSpec.<Void,Void>builder (
        "version", PMLVersionCommand::execute )
        .documentation (
             "Display PMLC's Version Number",
            "Write the PMLC version number to the standard output device.",
            "pmlc version" )
        .build();

    public static Void execute ( @Nullable Parameters<?> parameters ) {

        System.out.println ( PmlcVersion.VERSION_TEXT );

        return null;
    }
}
