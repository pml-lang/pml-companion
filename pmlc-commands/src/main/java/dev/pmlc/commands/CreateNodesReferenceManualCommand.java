package dev.pmlc.commands;

import dev.pmlc.ext.utilities.referencemanual.NodesReferenceManualCreator;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.commands.command.FormalCommand;
import dev.pp.parameters.parameter.Parameters;

import java.util.Map;

public class CreateNodesReferenceManualCommand {

/*
    public static final @NotNull FormalParameter<Path> OUTPUT_DIRECTORY_PARAMETER = new FormalParameter.Builder<> (
        "output_directory", CommonDataTypes.DIRECTORY_PATH )
        .alternativeName ( "od" )
        .documentation ( "Output Directory",
            """
              Output directory in which the PML Reference Manual is created.
              If a relative path is specified then it is relative to the current working directory.
              If the output directory doesn't exist it will be created.""",
            "ref_manual/HTML/" )
        .build();

    public static final @NotNull FormalParameter<Path> TEMP_DIRECTORY_PARAMETER = new FormalParameter.Builder<> (
        "temp_directory", CommonDataTypes.DIRECTORY_PATH )
        .alternativeName ( "td" )
        .defaultValue ( OSDirectories.TEMPORARY_FILES_DIRECTORY )
        .documentation ( "Temporary Directory",
            """
            Temporary directory used to create the reference manual.
            If a relative path is specified then it is relative to the current working directory.
            By default directory '""" + OSDirectories.TEMPORARY_FILES_DIRECTORY + """
            ' is used.
            The temporary directory can be deleted manually after the reference manual has been created.""",
            "ref_manual/HTML/" )
        .build();
*/

    public static final @NotNull FormalCommand<Void> COMMAND = FormalCommand.builder (
        "create_nodes_reference_manual", CreateNodesReferenceManualCommand::execute )
        .alternativeName ( "cnrm" )
        .documentation (
             "Create Nodes Reference Manual",
            """
            This command creates the 'PML Nodes Reference Manual'.
            The auto-generated PML file is stored in sub-directory 'input' of the current working directory.
            The HTML output is stored in sub-directory 'output' of the current working directory.""",
            "pmlc create_reference_manual" )
        // .addInputParameter ( OUTPUT_DIRECTORY_PARAMETER )
        // .addInputParameter ( TEMP_DIRECTORY_PARAMETER )
        .build();

    public static Void execute (
        @Nullable Map<String, String> stringParameters,
        @Nullable Parameters parameters ) throws Exception {

/*
        assert parameters != null;

        @NotNull Path outputDirectory = parameters.getNonNull ( OUTPUT_DIRECTORY_PARAMETER );
        @Nullable Path tempDirectory = parameters.getNonNull ( TEMP_DIRECTORY_PARAMETER );

        new ReferenceManualCreator().createReferenceManual ( outputDirectory, tempDirectory );
*/
        new NodesReferenceManualCreator().createManual ();

        return null;
    }
}
