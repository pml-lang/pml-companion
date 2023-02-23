package dev.pmlc.utils.referencemanual;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.ResourcesUtils;

import java.io.IOException;
import java.nio.file.Path;

public class ReferenceManualResources {

    private static final @NotNull Path REL_RESOURCES_DIR = Path.of ("data/ref_manual/resources" );

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

        Path resourcePath = REL_RESOURCES_DIR.resolve ( Path.of ( resource ) );
        @NotNull Path targetFile = targetRootDirectory.resolve ( resource );
        ResourcesUtils.copyResourceToFile (
            resourcePath, ReferenceManualResources.class, targetFile, isTextFile, true );
    }
}
