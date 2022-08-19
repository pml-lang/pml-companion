package dev.pmlc.ext;

import dev.pp.basics.annotations.NotNull;

public class PMLCVersion {

    public static @NotNull String APPLICATION_NAME = "PML Companion";
    public static @NotNull String APPLICATION_NAME_WITHOUT_SPACES = APPLICATION_NAME.replace ( ' ', '_' );
    public static @NotNull String APPLICATION_SHORT_NAME = "PMLC";

    public static @NotNull String VERSION = "3.0.0";
    public static @NotNull String DATE_PUBLISHED = "2022-08-19";
    public static @NotNull String VERSION_TEXT = APPLICATION_SHORT_NAME + " " + VERSION + " " + DATE_PUBLISHED;

    public static @NotNull String getMajorVersion() {

        int index = VERSION.indexOf ( "." );
        return VERSION.substring ( 0, index );
    }

    public static @NotNull String getMinorVersion() {

        int index = VERSION.lastIndexOf ( "." );
        return VERSION.substring ( index + 1 );
    }

    public static int getMajorVersionNumber() {
        return Integer.parseInt ( getMajorVersion() );
    }

    public static int getMinorVersionNumber() {
        return Integer.parseInt ( getMinorVersion() );
    }
}
