// Note: SUpport for Gradle is experimental. Use with care.

// import java.io.ByteArrayOutputStream

plugins {
    application

    // see https://openjfx.io/openjfx-docs/#gradle
    // and https://github.com/openjfx/javafx-gradle-plugin
    id ( "org.openjfx.javafxplugin" ) version "0.0.9"
}

dependencies {

    val PPL_HOME: String = System.getenv ( "PPL_HOME" ) ?: "missing"
    /*
        If your OS environment variable PPL_HOME is not set you can:
            - comment the line above
            - uncomment the line below
            - hardcode your path to PPL's installation directory in the line below
    */
    // val PPL_HOME: String = "/path/to/ppl"

    if ( PPL_HOME == "missing" ) {
        throw GradleException (
            """
                Your OS environment variable PPL_HOME is not set.
                You can set it to PPL's installation directory.
                Alternatively you can modify file build.gradle.tks to hardcode the directory path.
                For more information about PPL visit www.ppl-lang.dev
            """.trimIndent() )
    }

    implementation ( files (
        "$PPL_HOME/lib/org.ppl.core.jar",
        "$PPL_HOME/lib/org.ppl.ext.jar",
        "$PPL_HOME/lib/org.ppl.paiom.jar",
        "$PPL_HOME/lib/org.ppl.gui.jar" ) )
}

repositories {
    // mavenCentral()
    jcenter()
}

// Gradle should use directory 'buildGradle' for its builds. (Direcory 'build' is used by the PPL compiler/builder)
getProject().setBuildDir ( "buildGradle" )

// Use Java modules system
java {
    modularity.inferModulePath.set ( true )
}

// JavaFX is used for the desktop GUI
javafx {
    version = "15.0.1"
    modules ( "javafx.base", "javafx.controls", "javafx.graphics", "javafx.web" )
}

// Copy resource files into distributions
distributions {
    main {
        contents {
            from ( "work/resources")
        }
    }
}

val moduleName = "dev.pml.converter"

application {
    mainModule.set ( moduleName )
    mainClass.set ( "$moduleName.se_start" )
}

// The PPL compiler creates the Java source code files in directory temp/build/Java
sourceSets {
    main {
        java {
            setSrcDirs ( listOf ( "temp/build/Java" ) )
        }
    }
}

// Task to call the PPL compiler (creates Java source code files)
tasks.register ( "compilePPLToJava" ) {
    doLast {
        println ( "Compiling PPL to Java" )

        // val byteOut : ByteArrayOutputStream = ByteArrayOutputStream()
        project.exec {
            val OSName = System.getProperty ( "os.name" );
            if ( OSName.startsWith ( "Windows" ) ) {
                commandLine ( "cmd", "/c", "ppl", "compile_project", "--@input", "file work/config/compiler.gradle.cfg" )
            } else {
                commandLine ( "ppl", "compile_project", "--@input", "file work/config/compiler.gradle.cfg" )
            }
            // standardOutput = byteOut
        } 
        // val output : String = String ( byteOut.toByteArray() )

        println ( "Finished compiling PPL to Java" )
    }
}

tasks.compileJava {
    dependsOn ( "compilePPLToJava" )
    options.release.set ( 11 )
}

tasks.jar {
    archiveBaseName.set ( moduleName )
}

// OS script file name to start the application
tasks.startScripts {
    applicationName = "pmlc"
}
