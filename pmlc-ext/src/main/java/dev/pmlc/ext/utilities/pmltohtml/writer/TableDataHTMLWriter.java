package dev.pmlc.ext.utilities.pmltohtml.writer;

import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.block.table.*;
import dev.pmlc.core.data.node.block.table.TableDataNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;

import java.io.IOException;
import java.util.List;

public class TableDataHTMLWriter {

    public static void writeTableDataNode (
        @NotNull TableDataNode node,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        String tag = FormalTableDataNode.HTML_TAG;
        helper.writeBlockStartLine ( tag, FormalTableDataNode.CSS_CLASS );
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

        String tag = FormalTableHeaderNode.HTML_TAG;
        helper.writeBlockStartLine ( tag, FormalTableHeaderNode.CSS_CLASS );
        helper.increaseIndent();

        writeRow ( headerCells,
            FormalTableRowNode.CSS_CLASS_IN_HEADER,
            FormalTableCellNode.HTML_TAG_IN_HEADER,
            FormalTableCellNode.CSS_CLASS_IN_HEADER,
            null, helper );

        helper.decreaseIndent();
        helper.writeBlockEndLine ( tag );
    }

    private static void writeFooter (
        @Nullable List<String> footerCells,
        @Nullable List<HTextAlign> horizontalColumnAlignments,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        if ( footerCells == null ) return;

        String tag = FormalTableFooterNode.HTML_TAG;
        helper.writeBlockStartLine ( tag, FormalTableFooterNode.CSS_CLASS );
        helper.increaseIndent();

        writeRow ( footerCells,
            FormalTableRowNode.CSS_CLASS_IN_FOOTER,
            FormalTableCellNode.HTML_TAG_IN_FOOTER,
            FormalTableCellNode.CSS_CLASS_IN_FOOTER,
            horizontalColumnAlignments, helper );

        helper.decreaseIndent();
        helper.writeBlockEndLine ( tag );
    }

    private static void writeBody (
        @Nullable List<List<String>> rows,
        @Nullable List<HTextAlign> horizontalColumnAlignments,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        if ( rows == null ) return;

        String tag = FormalTableBodyNode.HTML_TAG;
        helper.writeBlockStartLine ( tag, FormalTableBodyNode.CSS_CLASS );
        helper.increaseIndent();

        for ( List<String> cells : rows ) {
            writeRow ( cells,
                FormalTableRowNode.CSS_CLASS,
                FormalTableCellNode.HTML_TAG,
                FormalTableCellNode.CSS_CLASS,
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

        String rowTag = FormalTableRowNode.HTML_TAG;
        helper.writeBlockStartLine ( rowTag, rowClassName );

        for ( int index = 0; index < cells.size(); index ++ ) {
            String className = cellClassName ( index, horizontalColumnAlignments, cellClassName );
            helper.writeHTMLStartTag ( cellTag, className );
            String cellValue = cells.get ( index );
            if ( cellValue != null ) helper.escapeAndWriteText ( cellValue );
            helper.writeHTMLEndTag ( cellTag );
        }

        helper.writeHTMLEndTag ( rowTag );
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

        return basicCellClassName + " " + FormalPMLNodeCreator.prefixedHTMLClassName ( unprefixedAlignmentClassName );
    }
}
