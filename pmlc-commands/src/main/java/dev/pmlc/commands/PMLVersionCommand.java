package dev.pmlc.commands;

import dev.pmlc.ext.PMLCVersion;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.commands.command.FormalCommand;
import dev.pp.parameters.parameter.list.Parameters;

import java.util.Map;

public class PMLVersionCommand {

    public static final @NotNull FormalCommand<Void> COMMAND = FormalCommand.builder (
        "version", PMLVersionCommand::execute )
        .documentation (
             "Display PMLC's Version Number",
            "Write the PMLC version number to the standard output device.",
            "pmlc version" )
        .build();

    public static Void execute (
        @Nullable Map<String, String> stringParameters,
        @Nullable Parameters parameters ) {

        System.out.println ( PMLCVersion.VERSION_TEXT );

        return null;
    }
}
