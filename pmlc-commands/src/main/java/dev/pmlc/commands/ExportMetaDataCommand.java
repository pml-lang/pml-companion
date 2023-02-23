package dev.pmlc.commands;

import dev.pdml.shared.constants.CorePdmlConstants;
import dev.pmlc.data.PmlcVersion;
import dev.pmlc.data.nodespec.NodeSpecRegistry;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.basics.utilities.file.TextFileIO;
import dev.pp.commands.command.CommandSpec;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.utilities.json.JSONWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

public class ExportMetaDataCommand {

    public static final @NotNull ParameterSpec<Path> OUTPUT_FILE_PARAMETER = new ParameterSpec.Builder<> (
        "output_file", CommonDataTypes.FILE_PATH )
        .alternativeName ( "of" )
        .defaultValue ( Path.of ( "PML_meta_data.json" ) )
        .documentation ( "Output File Path",
            """
            The absolute or relative path of the file to be created.
            If the path is relative, it is relative to the current working directory.
            The default value is file 'PML_meta_data.json' created in the current working directory.
            If the file exists already it will be overwritten.""",
            "--output_file pml/nodes.json" )
        .build();

    public static final @NotNull CommandSpec<Path,Void> COMMAND = CommandSpec.<Path,Void>builder (
        "export_meta_data", ExportMetaDataCommand::execute )
        .alternativeName ( "md" )
        .documentation (
            "Export PML Meta Data to a JSON File",
            """
            This command creates a JSON file containing PML meta data (nodes, attributes, etc).
            The JSON file can be used by editor plugins and tools to query structured data about PML.""",
            "pmlc export_meta_data" )
        .addInputParameter ( OUTPUT_FILE_PARAMETER )
        .build();

    public static Void execute ( @Nullable Parameters<?> parameters ) throws IOException {

        assert parameters != null;

        @NotNull Path outputFile = parameters.nonNullCastedValue ( OUTPUT_FILE_PARAMETER );
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
            .writeObjectStartLine ( "pml_meta" )
                .writeStringAttributeLine ( "pml_version", PmlcVersion.VERSION, false )
                .writeStringAttributeLine ( "pml_release_date", PmlcVersion.DATE_PUBLISHED, false )
                .writeArrayStartLine ( "nodes" );
                    List<PMLNodeSpec<?,?>> nodes = NodeSpecRegistry.getListSortedByName();
                    int size = nodes.size();
                    for ( int i = 0; i < size; i++ ) {
                        PMLNodeSpec<?,?> node = nodes.get ( i );
                        writeNode ( node, i == size - 1, writer );
                    }
                writer.writeArrayEndLine ( true )
            .writeObjectEndLine ( true )
        .writeDocumentEndLine();
    }

    private static void writeNode (
        @NotNull PMLNodeSpec<?,?> node,
        boolean isLastNode,
        @NotNull JSONWriter writer ) throws IOException {

        String name = node.qualifiedName();
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
                .writeStringAttributeLine ( "opening_tag", CorePdmlConstants.NODE_START + name, false )
                .writeStringAttributeLine ( "latest_doc_url", "https://www.pml-lang.dev/docs/reference_manual/index.html#node_" + name, true );

        writer.writeObjectEndLine ( isLastNode );
    }

    private static void writeNodeAttributes (
        @NotNull PMLNodeSpec<?,?> node,
        @NotNull JSONWriter writer ) throws IOException {

        @Nullable ParameterSpecs<?> attributes = node.getAttributeSpecs();
        if ( attributes == null ) {
            writer.writeNullAttributeLine ( "attributes", false );
        } else {
            writer.writeArrayStartLine ( "attributes" );
            List<? extends ParameterSpec<?>> list = attributes.listSortedByName();
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                ParameterSpec<?> attribute = list.get ( i );
                writeAttribute ( attribute, i == size - 1, writer );
            }
            writer.writeArrayEndLine ( false );
        }
    }

    private static void writeAttribute (
        @NotNull ParameterSpec<?> attribute,
        boolean isLastNode,
        @NotNull JSONWriter writer ) throws IOException {

        writer.writeObjectStartLine()

            .writeStringAttributeLine ( "id", attribute.getName(), false )
            .writeStringAttributeLine ( "type", attribute.typeName (), false )
            .writeBooleanAttributeLine ( "required", attribute.isRequired(), false )
            .writeStringAttributeLine ( "default_value", attribute.defaultValueAsString (), false )
            .writeNumberAttributeLine ( "position", attribute.getPositionalParameterIndex(), false )

            .writeStringAttributeLine ( "title", attribute.documentationTitle (), false )
            .writeStringAttributeLine ( "description", attribute.documentationDescription (), false )
            .writeStringAttributeLine ( "examples", attribute.documentationExamples (), true )

        .writeObjectEndLine ( isLastNode );
    }
}
