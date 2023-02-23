package dev.pmlc.commands;

import dev.pmlc.converter.PMLCResources;
import dev.pmlc.data.PmlcVersion;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSDirectories;
import dev.pp.basics.utilities.os.OSName;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.basics.utilities.string.StringAligner;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.commands.command.CommandSpec;
import dev.pp.parameters.parameters.Parameters;

public class PMLInfoCommand {

    public static final @NotNull CommandSpec<Void,Void> COMMAND = CommandSpec.<Void,Void>builder (
        "info", PMLInfoCommand::execute )
        .documentation (
             "Display PMLC Info",
            "Write info about PMLC to the standard output device.",
            "pmlc info" )
        .build();

    public static Void execute ( @Nullable Parameters<?> parameters ) {

        StringBuilder sb = new StringBuilder();

        append ( "Application name", PmlcVersion.APPLICATION_NAME, sb );
        append ( "Short name", PmlcVersion.APPLICATION_SHORT_NAME, sb );
        append ( "Version", PmlcVersion.VERSION, sb );
        append ( "Version date", PmlcVersion.DATE_PUBLISHED, sb );
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
