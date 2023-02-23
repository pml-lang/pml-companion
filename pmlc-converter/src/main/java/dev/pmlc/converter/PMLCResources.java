package dev.pmlc.converter;

import dev.pmlc.data.PmlcVersion;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.basics.utilities.directory.DirectoryConfig;
import dev.pp.basics.utilities.directory.DirectoryCreator;
import dev.pp.basics.utilities.ResourcesUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PMLCResources {

    public static final @NotNull Path ROOT_DIRECTORY = DirectoryConfig.userConfigDirectoryForApplication (
        null,
        PmlcVersion.APPLICATION_NAME_WITHOUT_SPACES,
        PmlcVersion.getMajorVersionNumber(),
        PmlcVersion.getMinorVersionNumber() );

    public static void createResourceFile (
        @NotNull Class<?> clazz,
        @NotNull Path resourcePath,
        boolean isTextFile ) throws IOException {

        createResourcesDirectoryIfNotExists();

        SimpleLogger.info ( "Creating resource file " + resourcePath + " in directory " + ROOT_DIRECTORY );

        ResourcesUtils.copyResourceToFile (
            resourcePath,
            clazz,
            ROOT_DIRECTORY.resolve ( resourcePath ),
            isTextFile,
            true );
    }

    public static @NotNull Path absoluteResourcePath ( @NotNull Path relativeResourcePath ) {
        return ROOT_DIRECTORY.resolve ( relativeResourcePath );
    }

    private static void createResourcesDirectoryIfNotExists() throws IOException {

        if ( Files.exists ( ROOT_DIRECTORY ) ) return;

        SimpleLogger.info ( "Creating shared data directory " + ROOT_DIRECTORY );
        DirectoryCreator.createWithParentsIfNotExists ( ROOT_DIRECTORY );
    }
}
