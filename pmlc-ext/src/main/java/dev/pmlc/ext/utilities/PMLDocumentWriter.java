package dev.pmlc.ext.utilities;

import dev.pdml.core.PDMLConstants;
import dev.pdml.core.data.node.name.NodeName;
import dev.pdml.ext.extensions.utilities.PDMLEscaper;
import dev.pmlc.core.data.formalnode.block.FormalDocumentNode;
import dev.pmlc.core.data.formalnode.block.FormalParagraphNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalChapterNode;
import dev.pmlc.core.data.formalnode.block.chapter.FormalTitleNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.commands.command.FormalCommand;
import dev.pp.datatype.DataType;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.utilities.text.BasicDocumentWriter;
import dev.pp.text.utilities.text.TextLines;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class PMLDocumentWriter extends BasicDocumentWriter {


    public interface ElementWriter<T> {

        void writeElement ( T element, @NotNull PMLDocumentWriter writer ) throws IOException;
    }


    public PMLDocumentWriter ( @NotNull Writer writer, @NotNull String singleIndent ) {
        super ( writer, singleIndent );
    }

    public PMLDocumentWriter ( @NotNull Writer writer ) {
        super ( writer );
    }


    // Basics

    @Override
    public PMLDocumentWriter write ( @NotNull String string ) throws IOException {
        super.write ( string );
        return this;
    }

    @Override
    public PMLDocumentWriter write ( char c ) throws IOException {
        super.write ( c );
        return this;
    }

    @Override
    public PMLDocumentWriter writeNullable ( @Nullable String string ) throws IOException {
        super.writeNullable ( string );
        return this;
    }

    @Override
    public PMLDocumentWriter writeLine ( @NotNull String string ) throws IOException {
        super.writeLine ( string );
        return this;
    }

    @Override
    public PMLDocumentWriter writeNewLine() throws IOException {
        super.writeNewLine();
        return this;
    }

    @Override
    public PMLDocumentWriter flush() throws IOException {
        super.flush();
        return this;
    }


    // Text

    public @NotNull String escapeText ( @NotNull String text ) {
        return PDMLEscaper.escapeNodeText ( text );
    }

    @Override
    public PMLDocumentWriter escapeAndWriteText ( @NotNull String text ) throws IOException {
        super.escapeAndWriteText ( text );
        return this;
    }

    @Override
    public PMLDocumentWriter escapeAndWriteNullableText ( @Nullable String text ) throws IOException {
        super.escapeAndWriteNullableText ( text );
        return this;
    }

    @Override
    public PMLDocumentWriter writeText ( @NotNull String text, boolean escapeText ) throws IOException {
        super.writeText ( text, escapeText );
        return this;
    }


    // Indent

    @Override
    public PMLDocumentWriter increaseIndent() {
        super.increaseIndent();
        return this;
    }

    @Override
    public PMLDocumentWriter decreaseIndent() {
        super.decreaseIndent();
        return this;
    }

    @Override
    public PMLDocumentWriter writeIndent() throws IOException {
        super.writeIndent();
        return this;
    }


    // Specific Block Nodes

    public PMLDocumentWriter writeDocNodeStartLine ( @NotNull String title ) throws IOException {

        return writeDocNodeStartLine ( title, false );
    }

    public PMLDocumentWriter writeDocNodeStartLine (
        @NotNull String title,
        boolean escapeTitle ) throws IOException {

        return writeDocOrChapterNodeStartLine ( FormalDocumentNode.NAME, title, escapeTitle, null );
    }

    public PMLDocumentWriter writeChapterNodeStartLine (
        @NotNull String title,
        @Nullable String id ) throws IOException {

        return writeChapterNodeStartLine ( title, false, id );
    }

    public PMLDocumentWriter writeChapterNodeStartLine ( @NotNull String title ) throws IOException {

        return writeChapterNodeStartLine ( title, false, null );
    }

    public PMLDocumentWriter writeChapterNodeStartLine (
        @NotNull String title,
        boolean escapeTitle,
        @Nullable String id ) throws IOException {

        return writeDocOrChapterNodeStartLine ( FormalChapterNode.NAME, title, escapeTitle, id );
    }

    private PMLDocumentWriter writeDocOrChapterNodeStartLine (
        @NotNull NodeName nodeName,
        @NotNull String title,
        boolean escapeTitle,
        @Nullable String id ) throws IOException {

        writeIndent();
        writeNodeStart ( nodeName, id == null ? null : Map.of ( "id", id ) );
        writeInlineNodeWithTextContent ( FormalTitleNode.NAME.toString(), null, title, escapeTitle );
        writeNewLine();
        increaseIndent();

        return this;
    }

    public PMLDocumentWriter writeParagraph ( @NotNull String text ) throws IOException {

        return writeParagraph ( text, false );
    }

    public PMLDocumentWriter writeParagraph (
        @NotNull String text,
        boolean escapeText ) throws IOException {

        writeBlockNodeWithTextContent ( FormalParagraphNode.NAME.toString(), null, text, escapeText );

        return this;
    }


    // Block Nodes

    public PMLDocumentWriter writeBlockNodeStartLine (
        @NotNull String nodeName,
        @Nullable Map<String,String> attributes ) throws IOException {

        writeIndent();
        writeNodeStart ( nodeName, attributes );
        writeNewLine();
        increaseIndent();

        return this;
    }

    public PMLDocumentWriter writeBlockNodeWithTextContent (
        @NotNull String nodeName,
        @NotNull String text ) throws IOException {

        return writeBlockNodeWithTextContent ( nodeName, null, text, false );
    }

    public PMLDocumentWriter writeBlockNodeWithTextContent (
        @NotNull String nodeName,
        @Nullable Map<String,String> attributes,
        @NotNull String text,
        boolean escapeText ) throws IOException {

        writeIndent();
        writeNodeStart ( nodeName, attributes );
        writeNodeText ( text, escapeText );
        writeNodeEnd();
        writeNewLine();

        return this;
    }

    public PMLDocumentWriter writeBlockNodeWithRawTextContent (
        @NotNull String nodeName,
        @Nullable Map<String,String> attributes,
        @NotNull String rawText ) throws IOException {

        writeBlockNodeStartLine ( nodeName, attributes );

        writeIndent();
        writeLine ( "~~~" );

        @NotNull List<String> lines = TextLines.textToLines ( rawText );
        for ( String line : lines ) {
            writeIndent();
            writeLine ( line );
        }

        writeIndent();
        writeLine ( "~~~" );

        writeBlockNodeEndLine();

        return this;
    }

    public PMLDocumentWriter writeBlockNodeEndLine () throws IOException {

        decreaseIndent();
        writeIndent();
        writeNodeEnd();
        writeNewLine();

        return this;
    }


    // Inline Nodes

    public PMLDocumentWriter writeInlineNodeStart (
        @NotNull String nodeName,
        @Nullable Map<String,String> attributes ) throws IOException {

        writeNodeStart ( nodeName, attributes );

        return this;
    }

    public PMLDocumentWriter writeInlineNodeWithTextContent (
        @NotNull String nodeName,
        @NotNull String text ) throws IOException {

        return writeInlineNodeWithTextContent ( nodeName, null, text, false );
    }

    public PMLDocumentWriter writeInlineNodeWithTextContent (
        @NotNull String nodeName,
        @Nullable Map<String,String> attributes,
        @NotNull String text,
        boolean escapeText ) throws IOException {

        writeNodeStart ( nodeName, attributes );
        writeNodeText ( text, escapeText );
        writeNodeEnd();

        return this;
    }

    private PMLDocumentWriter writeInlineNodeEnd() throws IOException {

        writeNodeEnd();

        return this;
    }


    // Attributes

    public void writeAttributes ( @NotNull Map<String,String> attributes ) throws IOException {

        write ( PDMLConstants.ATTRIBUTES_START );

        for ( Map.Entry<String, String> entry : attributes.entrySet() ) {
            write ( " " );
            write ( entry.getKey() );
            write ( PDMLConstants.ATTRIBUTE_ASSIGN );
            write ( PDMLConstants.ATTRIBUTE_VALUE_DOUBLE_QUOTE );
            write ( PDMLEscaper.escapeDoubleQuotedAttributeValue ( entry.getValue() ) );
            write ( PDMLConstants.ATTRIBUTE_VALUE_DOUBLE_QUOTE );
        }

        write ( " " );
        write ( PDMLConstants.ATTRIBUTES_END );
        write ( " " );
    }


    // Text

    public void writeNodeText ( @NotNull String text, boolean escapeText ) throws IOException {

        if ( escapeText ) {
            escapeAndWriteText ( text );
        } else {
            write ( text );
        }
    }


    // List

    public void writeCollection ( @NotNull Collection<?> collection ) throws IOException {
        writeCollection ( collection, false );
    }

    public void writeCollection ( @NotNull Collection<?> collection, boolean escapeText ) throws IOException {
        writeCollection (
            collection,
            (element, writer) -> { writer.writeText ( element.toString(), escapeText ); } );
    }

    public <T> void writeCollection (
        @NotNull Collection<T> collection,
        @NotNull ElementWriter<T> elementWriter ) throws IOException {

        writeBlockNodeStartLine ( "list", null );
        for ( T element : collection ) {
            writeBlockNodeStartLine ( "el", null );
            elementWriter.writeElement ( element, this );
            writeBlockNodeEndLine();
            writeNewLine();
        }
        writeBlockNodeEndLine();
    }


    // Map

    public <K,V> void writeMap ( @NotNull Map<K,V> map ) throws IOException {
        writeMap ( map, false, false );
    }

    public <K,V> void writeMap (
        @NotNull Map<K,V> map,
        boolean escapeKey,
        boolean escapeValue ) throws IOException {

        writeMap (
            map,
            (key, writer) -> writer.writeText ( key.toString(), escapeKey ),
            (value, writer) -> writer.writeText ( value.toString(), escapeValue ) );
    }

    public <K,V> void writeMap (
        @NotNull Map<K,V> map,
        @NotNull ElementWriter<K> keyWriter,
        @NotNull ElementWriter<V> valueWriter ) throws IOException {

        writeBlockNodeStartLine ( "table", null );
        for ( Map.Entry<K,V> entry : map.entrySet() ) {
            writeIndent();
            write ( "[tr [tc [b " );
            keyWriter.writeElement ( entry.getKey(), this );
            write ( "]][tc " );
            valueWriter.writeElement ( entry.getValue(), this );
            write ( "]]" );
            writeNewLine();
        }
        writeBlockNodeEndLine();
    }


    // Formal Parameter

    public void writeFormalParameters ( @NotNull List<FormalParameter<?>> parameters ) throws IOException {

        writeCollection (
            parameters,
            (parameter, writer) -> writer.writeFormalParameter ( parameter, this ) );
    }

    public <T> void writeFormalParameter (
        @NotNull FormalParameter<T> parameter,
        @NotNull PMLDocumentWriter writer ) throws IOException {

        @NotNull String name = parameter.getName();
        @Nullable String title = parameter.getDocumentationTitle();

        writeHeader ( title != null ? title : name + ( parameter.isRequired() ? " (required)" : "" ) );

        Map<String,String> map = new LinkedHashMap<>();

        map.put ( "Name", name );

        putAlternativeNames ( parameter.getAlternativeNames(), map );

        @Nullable Integer position = parameter.getPositionalParameterIndex();
        map.put ( "Is positional parameter", position == null ? "No" : "Yes (# " + position + ")" );

        putDescription ( parameter.getDocumentation(), map );

        DataType<?> dataType = parameter.getType();
        map.put ( "Type", dataType.getName() );

        map.put ( "Required", parameter.isRequired() ? "yes" : "no" );

        @Nullable String defaultValue = parameter.getDefaultValueAsString();
        map.put ( "Default Value", defaultValue == null ? "none" : escapeText ( defaultValue ) );

        @Nullable String allowedValues = dataType.validValuesAsString();
        if ( allowedValues != null )
            map.put ( "Allowed Values", escapeText ( allowedValues ) );

        String examples = parameter.getDocumentationExamples();
        if ( examples != null )
            map.put ( "Example(s)", replaceNewlinesWithPMLNewlines ( escapeText ( examples ) ) );

        writeMap ( map );
    }

    private void putAlternativeNames ( @Nullable Set<String> alternativeNames, @NotNull Map<String,String> map ) {

        if ( alternativeNames == null ) return;

        String label = "Alternative name";
        if ( alternativeNames.size() > 1 ) label = label + "s";

        map.put ( label, String.join ( ", ", alternativeNames ) );
    }

    private void putDescription ( @Nullable SimpleDocumentation documentation, @NotNull Map<String,String> map ) {

        if ( documentation == null ) return;

        @Nullable String description = documentation.getDescription();
        if ( description == null ) return;

        map.put ( "Description", replaceNewlinesWithPMLNewlines ( escapeText ( description ) ) );
    }


    // Command

    public void writeCommands ( @NotNull List<FormalCommand<?>> commands ) throws Exception {

        for ( FormalCommand<?> command : commands ) {
            writeCommand ( command );
        }
    }

    public void writeCommand ( @NotNull FormalCommand<?> command ) throws Exception {

        @NotNull String name = command.getName();
        @Nullable String title = command.getDocumentationTitle();

        startChapter ( title != null ? title : name, "command_" + name );

        writeName ( name );
        writeAlternativeNames ( command.getAlternativeNames() );
        writeDocDescription ( command.getDocumentation() );
        writeDocExamples ( command.getDocumentation() );

        writeHeader ( "Input Parameters" );
        @Nullable FormalParameters parameters = command.getInputParameters();
        if ( parameters == null ) {
            writeParagraph ( "This command has no input parameters" );
        } else {
            // writeFormalParameters ( parameters.getAllSortedByName() );
            writeFormalParameters ( parameters.getAllSortedByPositionalIndexThenName() );
        }

        endChapter();
    }

    public void writeName ( @NotNull String name ) throws IOException {

        writeNames ( "Name", name );
    }

    private void writeAlternativeNames ( @Nullable Set<String> alternativeNames ) throws IOException {

        if ( alternativeNames == null ) return;

        String label = "Alternative name";
        if ( alternativeNames.size() > 1 ) label = label + "s";

        writeNames ( label, String.join ( ", ", alternativeNames ) );
    }

    private void writeNames (
        @NotNull String label,
        @NotNull String names ) throws IOException {

        // [b Name: ][c doc]
        writeIndent();
        write ( "[b " );
        write ( label );
        write ( ": ][c " );
        write ( names );
        write ( "]" );
        writeNewLine();
        writeNewLine();
    }

    public void writeDocDescription ( @Nullable SimpleDocumentation documentation ) throws IOException {

        writeHeader ( "Description" );

        if ( documentation != null ) {

            @Nullable String description = documentation.getDescription();
            if ( description != null ) {
                description = escapeText ( description );
                description = replaceNewlinesWithPMLNewlines ( description );
                writeParagraph ( description );
            }
        }

        writeNewLine();
    }

    private void writeDocExamples ( @Nullable SimpleDocumentation documentation ) throws IOException {

        if ( documentation == null ) return;
        @Nullable String examples = documentation.getExamples();
        if ( examples == null ) return;

        writeHeader ( "Examples" );

        writeBlockNodeWithRawTextContent ( "input", null, examples );
/*
        examples = escapeText ( examples );
        examples = replaceNewlinesWithPMLNewlines ( examples );
        text
        writeParagraph ( examples );

 */

        writeNewLine();
    }

    // private

    private void startChapter ( @NotNull String title, @NotNull String id ) throws IOException {
        writeChapterNodeStartLine ( title, id );
    }

    private void endChapter() throws IOException {
        writeBlockNodeEndLine();
        writeNewLine();
    }

    private void writeNodeStart (
        @NotNull NodeName nodeName,
        @Nullable Map<String,String> attributes ) throws IOException {

        writeNodeStart ( nodeName.toString(), attributes );
    }

    private void writeNodeStart (
        @NotNull String nodeName,
        @Nullable Map<String,String> attributes ) throws IOException {

        write ( PDMLConstants.NODE_START );
        write ( nodeName );
        write ( PDMLConstants.NAME_VALUE_SEPARATOR );

        if ( attributes != null ) writeAttributes ( attributes );
    }

    private void writeNodeEnd() throws IOException {

        write ( PDMLConstants.NODE_END );
    }

    private void writeHeader ( @NotNull String header ) throws IOException {
        writeBlockNodeWithTextContent ( "header", header );
    }

    private @NotNull String replaceNewlinesWithPMLNewlines ( @NotNull String text ) {

        return text
            .replace ( "\r\n", "[nl]" )
            .replace ( "\n", "[nl]" );
    }
}
