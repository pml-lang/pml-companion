package dev.pmlc.converter.pmltohtml.writer;

import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.block.table.*;
import dev.pmlc.data.node.block.table.SimpleTableNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;

import java.io.IOException;
import java.util.List;

public class TableDataHTMLWriter {

    public static void writeTableDataNode (
        @NotNull SimpleTableNode node,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        String tag = SimpleTableNodeSpec.HTML_TAG;
        helper.writeBlockStartLine ( tag, SimpleTableNodeSpec.CSS_CLASS );
        helper.increaseIndent();

        @Nullable List<HTextAlign> horizontalColumnAlignments = node.getHorizontalColumnAlignments();
        writeHeader ( node.getHeaderCells(), horizontalColumnAlignments, helper );
        writeBody ( node.getBodyRows(), horizontalColumnAlignments, helper );
        writeFooter ( node.getFooterCells(), horizontalColumnAlignments, helper );

        helper.decreaseIndent();
        helper.writeBlockEndLine ( tag );

    }

    private static void writeHeader (
        @Nullable List<String> headerCells,
        @Nullable List<HTextAlign> horizontalColumnAlignments,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        if ( headerCells == null ) return;

        String tag = TableHeaderNodeSpec.HTML_TAG;
        helper.writeBlockStartLine ( tag, TableHeaderNodeSpec.CSS_CLASS );
        helper.increaseIndent();

        writeRow ( headerCells,
            TableRowNodeSpec.CSS_CLASS_IN_HEADER,
            TableCellNodeSpec.HTML_TAG_IN_HEADER,
            TableCellNodeSpec.CSS_CLASS_IN_HEADER,
            null, helper );

        helper.decreaseIndent();
        helper.writeBlockEndLine ( tag );
    }

    private static void writeFooter (
        @Nullable List<String> footerCells,
        @Nullable List<HTextAlign> horizontalColumnAlignments,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        if ( footerCells == null ) return;

        String tag = TableFooterNodeSpec.HTML_TAG;
        helper.writeBlockStartLine ( tag, TableFooterNodeSpec.CSS_CLASS );
        helper.increaseIndent();

        writeRow ( footerCells,
            TableRowNodeSpec.CSS_CLASS_IN_FOOTER,
            TableCellNodeSpec.HTML_TAG_IN_FOOTER,
            TableCellNodeSpec.CSS_CLASS_IN_FOOTER,
            horizontalColumnAlignments, helper );

        helper.decreaseIndent();
        helper.writeBlockEndLine ( tag );
    }

    private static void writeBody (
        @Nullable List<List<String>> rows,
        @Nullable List<HTextAlign> horizontalColumnAlignments,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        if ( rows == null ) return;

        String tag = TableBodyNodeSpec.HTML_TAG;
        helper.writeBlockStartLine ( tag, TableBodyNodeSpec.CSS_CLASS );
        helper.increaseIndent();

        for ( List<String> cells : rows ) {
            writeRow ( cells,
                TableRowNodeSpec.CSS_CLASS,
                TableCellNodeSpec.HTML_TAG,
                TableCellNodeSpec.CSS_CLASS,
                horizontalColumnAlignments, helper );
        }

        helper.decreaseIndent();
        helper.writeBlockEndLine ( tag );
    }

    private static void writeRow (
        @NotNull List<String> cells,
        @NotNull String rowClassName,
        @NotNull String cellTag,
        @NotNull String cellClassName,
        @Nullable List<HTextAlign> horizontalColumnAlignments,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        String rowTag = TableRowNodeSpec.HTML_TAG;
        helper.writeBlockStartLine ( rowTag, rowClassName );

        for ( int index = 0; index < cells.size(); index ++ ) {
            String className = cellClassName ( index, horizontalColumnAlignments, cellClassName );
            helper.writeStartTag ( cellTag, className );
            String cellValue = cells.get ( index );
            if ( cellValue != null ) helper.escapeAndWriteText ( cellValue );
            helper.writeEndTag ( cellTag );
        }

        helper.writeEndTag ( rowTag );
        helper.writeNewLine();
    }

    private static @NotNull String cellClassName (
        int columnIndex,
        @Nullable List<HTextAlign> horizontalColumnAlignments,
        @NotNull String basicCellClassName ) {

        if ( horizontalColumnAlignments == null ) return basicCellClassName;

        HTextAlign alignment = horizontalColumnAlignments.get ( columnIndex );
        if ( alignment == null ) return basicCellClassName;

        String unprefixedAlignmentClassName = switch ( alignment ) {
            case LEFT -> "text-align-left";
            case RIGHT -> "text-align-right";
            case CENTER -> "text-align-center";
        };

        return basicCellClassName + " " + PMLNodeSpecCreator.prefixedHTMLClassName ( unprefixedAlignmentClassName );
    }
}
