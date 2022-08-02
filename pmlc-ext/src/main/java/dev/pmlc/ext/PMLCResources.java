package dev.pmlc.ext;

import dev.pmlc.ext.utilities.pmltohtml.options.PMLToHTMLResources;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.basics.utilities.directory.DirectoryConfig;
import dev.pp.basics.utilities.directory.DirectoryCreator;
import dev.pp.basics.utilities.ResourcesUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PMLCResources {

    public static @NotNull Path ROOT_DIRECTORY = DirectoryConfig.userConfigDirectoryForApplication (
        null, PMLCVersion.APPLICATION_NAME_WITHOUT_SPACES, PMLCVersion.getMajorVersionNumber(), PMLCVersion.getMinorVersionNumber() );

    public record ResourceFile (
        @NotNull Path path,
        boolean isTextFile ) {}

    public static void createResourcesDirectoryIfNotExists () throws IOException {

        // if ( ROOT_DIRECTORY.toFile().exists() ) return;
        if ( Files.exists ( ROOT_DIRECTORY ) ) return;

        SimpleLogger.info ( "Creating shared data directory " + ROOT_DIRECTORY );

        DirectoryCreator.createWithParentsIfNotExists ( ROOT_DIRECTORY );

        for ( ResourceFile resourceFile : getResourceFiles() ) {
            createResourceFile ( resourceFile );
        }
    }

    public static void addTextResourceFile (
        @NotNull List<ResourceFile> resourceFiles,
        @NotNull Path path ) {

        resourceFiles.add ( new ResourceFile ( path, true ) );
    }

    public static void addBinaryResourceFile (
        @NotNull List<ResourceFile> resourceFiles,
        @NotNull Path path ) {

        resourceFiles.add ( new ResourceFile ( path, false ) );
    }

    private static @NotNull List<ResourceFile> getResourceFiles() {

        List<ResourceFile> resources = new ArrayList<>();

        PMLToHTMLResources.addResources ( resources );

        return resources;
    }

    private static void createResourceFile ( @NotNull ResourceFile resourceFile ) throws IOException {

        createResourceFile ( resourceFile.path(), resourceFile.isTextFile() );
    }

    private static void createResourceFile ( @NotNull Path filePath, boolean isTextFile ) throws IOException {

        SimpleLogger.debug ( "Creating resource file " + filePath + " in directory " + ROOT_DIRECTORY );

        Path targetFile = ROOT_DIRECTORY.resolve ( filePath );
        Path targetDirectory = targetFile.getParent();
        DirectoryCreator.createWithParentsIfNotExists ( targetDirectory );

        if ( isTextFile ) {
            ResourcesUtils.copyTextResourceToFile (
                filePath.toString (),
                PMLCResources.class,
                targetFile );
        } else {
            ResourcesUtils.copyBinaryResourceToFile (
                filePath.toString (),
                PMLCResources.class,
                targetFile );
        }
    }
}
