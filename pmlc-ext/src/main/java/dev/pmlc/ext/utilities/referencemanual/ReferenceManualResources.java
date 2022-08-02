package dev.pmlc.ext.utilities.referencemanual;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.ResourcesUtils;
import dev.pp.basics.utilities.directory.DirectoryCreator;

import java.io.IOException;
import java.nio.file.Path;

public class ReferenceManualResources {

    private static final @NotNull Path RESOURCES_ROOT_DIR = Path.of ("data/ref_manual/resources" );

    public static void copyResources ( @NotNull Path targetRootDirectory ) throws IOException {

        copyResource ( "media/bird_talk.mp3", targetRootDirectory, false );
        copyResource ( "media/gold_star.png", targetRootDirectory, false );
        copyResource ( "media/red_flower.mp4", targetRootDirectory, false );
        copyResource ( "media/strawberries.jpg", targetRootDirectory, false );
        copyResource ( "source_code/hello_world.ppl", targetRootDirectory, true );
    }

    private static void copyResource (
        @NotNull String resource,
        @NotNull Path targetRootDirectory,
        boolean isTextFile ) throws IOException {

        Path resourcePath = RESOURCES_ROOT_DIR.resolve ( Path.of ( resource ) );
        @NotNull Path targetFile = targetRootDirectory.resolve ( resource );
        DirectoryCreator.createWithParentsIfNotExists ( targetFile.getParent() );
        ResourcesUtils.copyResourceToFile (
            resourcePath.toString(), ReferenceManualResources.class, targetFile, isTextFile );
    }
}
