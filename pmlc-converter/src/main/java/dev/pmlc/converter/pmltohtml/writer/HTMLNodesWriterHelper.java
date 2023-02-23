package dev.pmlc.converter.pmltohtml.writer;

import dev.pmlc.converter.pmltohtml.options.PMLToHTMLOptions;
import dev.pmlc.data.node.PMLNode;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.utilities.html.HTMLWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTMLNodesWriterHelper {


    private static final @NotNull String CSS_CLASS_ATTRIBUTE_NAME = "class";


    private final @NotNull HTMLWriter writer;

    private final @NotNull PMLToHTMLOptions options;
    PMLToHTMLOptions getOptions() { return options; }

    private final @NotNull PMLNodesHandler nodesHandler;


    public HTMLNodesWriterHelper (
        @NotNull HTMLWriter writer,
        @NotNull PMLToHTMLOptions options,
        @NotNull PMLNodesHandler nodesHandler ) {

        this.writer = writer;
        this.options = options;
        this.nodesHandler = nodesHandler;
    }


    // Helpers

    public void writeBlockNodeWithBlockChildren (
        @NotNull PMLBlockNode blockNode,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) throws Exception {

        writeBlockNodeWithBlockChildren ( blockNode, HTMLTag, CSSClass, null );
    }

    private void writeBlockNodeWithBlockChildren (
        @NotNull PMLBlockNode blockNode,
        @NotNull String HTMLTag,
        @NotNull String CSSClass,
        @Nullable Map<String,String> extraAttributes ) throws Exception {

        writeIndent();
        writeHTMLStartTag ( blockNode, HTMLTag, CSSClass, extraAttributes );
        writeNewLine();

        increaseIndent();
        nodesHandler.handleChildNodes ( blockNode.getBlockChildNodes() );
        decreaseIndent();

        writeNewLine();
        writeIndent();
        writeHTMLEndTag ( HTMLTag );
        writeNewLine();
    }

    public void writeBlockNodeWithInlineChildren (
        @NotNull PMLBlockNode blockNode,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) throws Exception {

        writeBlockNodeWithInlineChildren ( blockNode, HTMLTag, CSSClass, null );
    }

    private void writeBlockNodeWithInlineChildren (
        @NotNull PMLBlockNode node,
        @NotNull String HTMLTag,
        @NotNull String CSSClass,
        @Nullable Map<String,String> extraAttributes ) throws Exception {

        writeIndent();
        writeHTMLStartTag ( node, HTMLTag, CSSClass, extraAttributes );

        nodesHandler.handleChildNodes ( node.getInlineChildNodes () );

        writeHTMLEndTag ( HTMLTag );
        writeNewLine();
    }

    public void writeBlockNodeStartLine (
        @NotNull PMLBlockNode node,
        @NotNull String HTMLTag,
        @NotNull String CSSClass,
        @Nullable Map<String,String> extraAttributes,
        boolean increaseIndent ) throws IOException {

        writeIndent();
        writeHTMLStartTag ( node, HTMLTag, CSSClass, extraAttributes );
        if ( increaseIndent ) increaseIndent();
        writeNewLine();
    }

    public void writeInlineNode (
        @NotNull PMLInlineNode node,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) throws Exception {

        writeInlineNode ( node, HTMLTag, CSSClass, null );
    }

    private void writeInlineNode (
        @NotNull PMLInlineNode node,
        @NotNull String HTMLTag,
        @NotNull String CSSClass,
        @Nullable Map<String,String> extraAttributes ) throws Exception {

        writeHTMLStartTag ( node, HTMLTag, CSSClass, extraAttributes );
        nodesHandler.handleChildNodes ( node.getInlineChildNodes() );
        writeHTMLEndTag ( HTMLTag );
    }

    public void writeHTMLStartTag (
        @NotNull PMLNode node,
        @NotNull String HTMLTag,
        @Nullable String CSSClass,
        @Nullable Map<String,String> extraAttributes ) throws IOException {

        @Nullable String nodeId = null;
        if ( node instanceof PMLBlockNode blockNode ) nodeId = blockNode.getNodeId();

        writeHTMLStartTag ( HTMLTag, nodeId, CSSClass, node.getHTMLAttributes(), extraAttributes );
    }

    public void writeHTMLBlockStartTag (
        @NotNull String HTMLTag,
        @Nullable String idAttribute,
        @Nullable String classAttribute,
        @Nullable Map<String,String> nodeAttributes,
        @Nullable Map<String,String> extraAttributes ) throws IOException {

        writeIndent();
        writeHTMLStartTag ( HTMLTag, idAttribute, classAttribute, nodeAttributes, extraAttributes );
        writeNewLine();
    }

    public void writeHTMLStartTag (
        @NotNull String HTMLTag,
        @Nullable String idAttribute,
        @Nullable String classAttribute,
        @Nullable Map<String,String> nodeHTMLAttributes,
        @Nullable Map<String,String> extraAttributes ) throws IOException {

        write ( "<" );
        write ( HTMLTag );
        writeHTMLAttributes ( idAttribute, classAttribute, nodeHTMLAttributes, extraAttributes );
        write ( ">" );
    }

    public void writeHTMLATag ( String hrefValue ) throws IOException {

        writeHTMLStartTag ( "a", null, null, null, Map.of ( "href", hrefValue ) );
    }

    private void writeHTMLAttributes (
        @Nullable String idAttribute,
        @Nullable String classAttribute,
        @Nullable Map<String,String> nodeHTMLAttributes,
        @Nullable Map<String,String> extraAttributes ) throws IOException {

        // id
        if ( idAttribute != null ) writeAttribute ( "id", idAttribute );

        // class

        @Nullable String explicitCSSClass = nodeHTMLAttributes == null ? null : nodeHTMLAttributes.get ( CSS_CLASS_ATTRIBUTE_NAME );
        @Nullable Map<String,String> nodeHTMLAttributesCopy = nodeHTMLAttributes;
        if ( explicitCSSClass != null ) {
            nodeHTMLAttributesCopy = new HashMap<> ( nodeHTMLAttributes );
            nodeHTMLAttributesCopy.remove ( CSS_CLASS_ATTRIBUTE_NAME );
        }

        // merge 'class' attribute value
        StringBuilder CSSClass = new StringBuilder();
        if ( classAttribute != null ) CSSClass.append ( classAttribute );
        if ( explicitCSSClass != null ) {
            if ( ! CSSClass.isEmpty() ) CSSClass.append ( " " );
            CSSClass.append ( explicitCSSClass );
        }

        if ( ! CSSClass.isEmpty() ) writeHTMLClassAttribute ( CSSClass.toString() );

        // node's HTML attributes
        writeAttributes ( nodeHTMLAttributesCopy );

        // extra attribute
        writeAttributes ( extraAttributes );
    }

    private void writeHTMLClassAttribute ( @Nullable String className ) throws IOException {

        if ( className == null || options.omitCSS() ) return;

        writeAttribute ( CSS_CLASS_ATTRIBUTE_NAME, className );
    }


    // HTMLWriter method wrappers

    public void write ( @NotNull String string ) throws IOException { writer.write ( string ); }

    public void writeNullable ( @Nullable String string ) throws IOException { writer.writeNullable ( string ); }

    public void writeNewLine() throws IOException { writer.writeNewLine(); }

    public void writeIndent() throws IOException { writer.writeIndent(); }

    public void increaseIndent() { writer.increaseIndent(); }

    public void decreaseIndent() { writer.decreaseIndent(); }

    public @NotNull String escapeText ( @NotNull String text ) {
        return writer.escapeText ( text );
    }

    public void escapeAndWriteText ( @NotNull String text ) throws IOException {
        writer.escapeAndWriteText ( text ); }

    public void escapeAndWriteNullableText ( @Nullable String text ) throws IOException {
        writer.escapeAndWriteNullableText ( text ); }

    public void writeHTMLStartTag ( @NotNull String tag, @NotNull String CSSClass ) throws IOException {
        writer.writeStartTag ( tag, CSSClass ); }

    public void writeBlockStartLine ( @NotNull String tag, @NotNull String CSSClass ) throws IOException {
        writer.writeBlockStartLine ( tag, CSSClass ); }

    public void writeHTMLEndTag ( @NotNull String tag ) throws IOException { writer.writeEndTag ( tag ); }

    public void writeBlockEndLine ( @NotNull String tag ) throws IOException { writer.writeBlockEndLine ( tag ); }

    public void writeAttributes ( @Nullable Map<String,String> attributes ) throws IOException {
        writer.writeAttributes ( attributes ); }

    public void writeAttribute ( @NotNull String name, @Nullable String value ) throws IOException {
        writer.writeAttribute ( name, value ); }
}
