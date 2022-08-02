package dev.pmlc.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

/*
    This dummy class is necessary to force the Picocli annotation processor to create files
    reflect-config.json, proxy-config.json, and resource-config.json
    in directory build\classes\java\main\META-INF\native-image\picocli-generated\PMLC_cli\
    These files are needed by GraalVM to create native images (because Picocli uses reflection).
    'mixinStandardHelpOptions = true' is needed to add 'picocli.CommandLine$AutoHelpMixin' in 'reflect-config.json'

    Reason for this class: bug(?) in Picocli 4.6.3: The json files are not created if Picocli commands are only created
    programmatically (and not declaratively using annotations)
*/
@CommandLine.Command (name = "dummy", mixinStandardHelpOptions = true)
public class Dummy implements Callable<Integer> {

        @Override
        public Integer call() throws Exception { // the business logic...
            return 0;
        }
}
