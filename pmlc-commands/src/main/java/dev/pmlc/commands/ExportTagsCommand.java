package dev.pmlc.commands;

import dev.pdml.core.PDMLConstants;
import dev.pmlc.core.data.formalnode.FormalNodeRegistry;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.ext.PMLCVersion;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.basics.utilities.file.TextFileIO;
import dev.pp.commands.command.FormalCommand;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.parameters.parameter.Parameters;
import dev.pp.text.utilities.json.JSONWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ExportTagsCommand {

    public static final @NotNull FormalParameter<Path> OUTPUT_FILE_PARAMETER = new FormalParameter.Builder<> (
        "output_file", CommonDataTypes.FILE_PATH )
        .alternativeName ( "of" )
        .defaultValue ( Path.of ( "pml_tags.json" ) )
        .documentation ( "JSON Output File Path",
            """
            The absolute or relative path of the JSON file to be created.
            If the path is relative, it is relative to the current working directory.
            The default value is file 'pml_tags.json' created in the current working directory.
            If the file exists already it will be overwritten.""",
            "--output_file pml/tags.json" )
        .build();

    public static final @NotNull FormalCommand<Void> COMMAND = FormalCommand.builder (
        "export_tags", ExportTagsCommand::execute )
        .alternativeName ( "et" )
        .documentation (
            "Export PML Nodes And Attributes Data to a JSON File",
            """
            This command creates a JSON file describing PML nodes and attributes.
            The JSON file can be used by editor plugins and tools to get structured data about PML nodes and attributes.""",
            "pmlc export_tags" )
        .addInputParameter ( OUTPUT_FILE_PARAMETER )
        .build();

    public static Void execute ( @Nullable Parameters parameters ) throws IOException {

        assert parameters != null;

        @NotNull Path outputFile = parameters.getNonNull ( OUTPUT_FILE_PARAMETER );
        write ( outputFile );

        return null;
    }

    public static void write ( @NotNull Path outputFile ) throws IOException {

        try ( Writer writer = TextFileIO.getUTF8FileWriter ( outputFile ) ) {
            write ( writer );
        }

        SimpleLogger.info ( "File '" + outputFile + "' has been created." );
    }

    public static void write ( @NotNull Writer basicWriter ) throws IOException {

        JSONWriter writer = new JSONWriter ( basicWriter );

        writer.writeDocumentStartLine()
            .writeObjectStartLine ( "pml_tags" )
                .writeStringAttributeLine ( "pml_version", PMLCVersion.VERSION, false )
                .writeStringAttributeLine ( "pml_release_date", PMLCVersion.DATE_PUBLISHED, false )
                .writeArrayStartLine ( "tags" );
                    List<FormalPMLNode<?,?>> nodes = FormalNodeRegistry.getListSortedByName();
                    int size = nodes.size();
                    for ( int i = 0; i < size; i++ ) {
                        FormalPMLNode<?,?> node = nodes.get ( i );
                        writeNode ( node, i == size - 1, writer );
                    }
                writer.writeArrayEndLine ( true )
            .writeObjectEndLine ( true )
        .writeDocumentEndLine();
    }

    private static void writeNode (
        @NotNull FormalPMLNode<?,?> node,
        boolean isLastNode,
        @NotNull JSONWriter writer ) throws IOException {

        String name = node.getNameAsString();
        boolean isInlineNode = node.isInlineNode();

        writer.writeObjectStartLine()

            .writeStringAttributeLine ( "id", name, false )
            .writeStringAttributeLine ( "type", isInlineNode ? "inline" : "block", false )
            .writeStringAttributeLine ( "title", node.getDocumentationTitle(), false )
            .writeStringAttributeLine ( "description", node.getDocumentationDescription(), false )
            .writeStringAttributeLine ( "examples", node.getDocumentationExamples(), false );

            writeNodeAttributes ( node, writer );

            writer.writeBooleanAttributeLine ( "HTML_attributes_allowed", node.isHTMLAttributesAllowed(), false )
                .writeBooleanAttributeLine ( "is_inline_type", isInlineNode, false )
                .writeBooleanAttributeLine ( "is_raw_text_block", node.isRawTextNode(), false )
                .writeBooleanAttributeLine ( "child_nodes_allowed", node.isBlockChildNodesAllowed() || node.isInlineChildNodesAllowed(), false )
                .writeStringAttributeLine ( "opening_tag", PDMLConstants.NODE_START + name, false )
                .writeStringAttributeLine ( "latest_doc_url", "https://www.pml-lang.dev/docs/reference_manual/index.html#node_" + name, true );

        writer.writeObjectEndLine ( isLastNode );
    }

    private static void writeNodeAttributes (
        @NotNull FormalPMLNode<?,?> node,
        @NotNull JSONWriter writer ) throws IOException {

        @Nullable FormalParameters attributes = node.getAttributes();
        if ( attributes == null ) {
            writer.writeNullAttributeLine ( "attributes", false );
        } else {
            writer.writeArrayStartLine ( "attributes" );
            List<FormalParameter<?>> list = attributes.getAllSortedByName();
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                FormalParameter<?> attribute = list.get ( i );
                writeAttribute ( attribute, i == size - 1, writer );
            }
            writer.writeArrayEndLine ( false );
        }
    }

    private static void writeAttribute (
        @NotNull FormalParameter<?> attribute,
        boolean isLastNode,
        @NotNull JSONWriter writer ) throws IOException {

        writer.writeObjectStartLine()

            .writeStringAttributeLine ( "id", attribute.getName(), false )
            .writeStringAttributeLine ( "type", attribute.getTypeName(), false )
            .writeBooleanAttributeLine ( "required", attribute.isRequired(), false )
            .writeStringAttributeLine ( "default_value", attribute.getDefaultValueAsString(), false )
            .writeNumberAttributeLine ( "position", attribute.getPositionalParameterIndex(), false )

            .writeStringAttributeLine ( "title", attribute.getDocumentationTitle(), false )
            .writeStringAttributeLine ( "description", attribute.getDocumentationDescription(), false )
            .writeStringAttributeLine ( "examples", attribute.getDocumentationExamples(), true )

        .writeObjectEndLine ( isLastNode );
    }
}
