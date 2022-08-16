package dev.pmlc.commands;

import dev.pmlc.ext.PMLCResources;
import dev.pmlc.ext.PMLCVersion;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSDirectories;
import dev.pp.basics.utilities.os.OSName;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.basics.utilities.string.StringAligner;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.commands.command.FormalCommand;
import dev.pp.parameters.parameter.Parameters;

import java.util.Map;

public class PMLInfoCommand {

    public static final @NotNull FormalCommand<Void> COMMAND = FormalCommand.builder (
        "info", PMLInfoCommand::execute )
        .documentation (
             "Display PMLC Info",
            "Write info about PMLC to the standard output device.",
            "pmlc info" )
        .build();

    public static Void execute (
        @Nullable Map<String, String> stringParameters,
        @Nullable Parameters parameters ) {

        StringBuilder sb = new StringBuilder();

        append ( "Application name", PMLCVersion.APPLICATION_NAME, sb );
        append ( "Short name", PMLCVersion.APPLICATION_SHORT_NAME, sb );
        append ( "Version", PMLCVersion.VERSION, sb );
        append ( "Version date", PMLCVersion.DATE_PUBLISHED, sb );
        append ( "Shared data dir.", PMLCResources.ROOT_DIRECTORY.toString(), sb );
        append ( "Working dir.", OSDirectories.currentWorkingDirectory().toString(), sb );
        append ( "OS name", OSName.name (), sb );
        append ( "Java version.", System.getProperty ( "java.version" ), sb );

        System.out.println ( sb.toString() );

        return null;
    }

    private static void append ( @NotNull String label, @NotNull String value, @NotNull StringBuilder sb ) {

        sb.append ( StringAligner.align ( label + ":", 20, HTextAlign.RIGHT ) );
        sb.append ( " " );
        sb.append ( value );
        sb.append ( StringConstants.OS_NEW_LINE );
    }
}
