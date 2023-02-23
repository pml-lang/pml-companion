package dev.pmlc.data.node.block.table;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.table.SimpleTableNodeSpec;
import dev.pmlc.data.nodespec.block.table.TableNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.text.token.TextToken;
import dev.pp.text.utilities.text.TextLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SimpleTableNode extends PMLBlockNode {

    // disabled, see https://github.com/tajmone/Sublime-PML/issues/17#issuecomment-1182903983
    // private static final @NotNull Pattern TAB_SEPARATOR = Pattern.compile ( "\\s*\\t\\s*" );

    private static final @NotNull Pattern VERTICAL_BAR_SEPARATOR = Pattern.compile ( "\\s*\\|\\s*" );
    private static final @NotNull Pattern COMMA_SEPARATOR = Pattern.compile ( "\\s*,\\s*" );
    private static final @NotNull Pattern SEMICOLON_SEPARATOR = Pattern.compile ( "\\s*;\\s*" );

    private static @Nullable List<HTextAlign> parseHorizontalColumnAlignments (
        @Nullable String parameterValue,
        int columnCount,
        @Nullable TextToken textToken ) throws DataValidatorException {

        if ( parameterValue == null ) return null;

        List<String> stringValues = List.of ( COMMA_SEPARATOR.split ( parameterValue ) );
        if ( stringValues.size() != columnCount ) throw new DataValidatorException (
            "The number of columns defined in the attribute for horizontal alignments (" + stringValues.size() + ") must be equal to the number of columns in the table (" + columnCount + ")",
            "INVALID_COLUMN_ALIGNMENTS",
            textToken, null );

        List<HTextAlign> result = new ArrayList<>();
        for ( String stringValue : stringValues ) {

            if ( stringValue == null || stringValue.isEmpty() ) {
                result.add ( null );
            } else {
                switch ( stringValue.toLowerCase () ) {
                    case "l", "left" -> result.add ( HTextAlign.LEFT );
                    case "r", "right" -> result.add ( HTextAlign.RIGHT );
                    case "c", "center" -> result.add ( HTextAlign.CENTER );
                    default -> throw new DataValidatorException (
                        "'" + stringValue +
                            """
                                '  is an invalid value for a horizontal alignment. Valid values are:
                                l, left
                                c, center
                                r, right
                                Uppercase letters are allowed.""",
                        "INVALID_COLUMN_ALIGNMENT",
                        textToken, null );
                }
            }
        }
        return result;
    }


    private @Nullable List<String> headerCells = null;
    public @Nullable List<String> getHeaderCells () {
        return headerCells;
    }

    private @Nullable List<String> footerCells = null;
    public @Nullable List<String> getFooterCells () {
        return footerCells;
    }

    private @Nullable List<List<String>> bodyRows = null;
    public @Nullable List<List<String>> getBodyRows () {
        return bodyRows;
    }

    private @Nullable String horizontalColumnAlignmentsString = null;

    private @Nullable List<HTextAlign> horizontalColumnAlignments = null;
    public @Nullable List<HTextAlign> getHorizontalColumnAlignments () {
        return horizontalColumnAlignments;
    }


    public SimpleTableNode () {
        super ();
    }


    public @NotNull PMLNodeSpec<String, SimpleTableNode> getNodeSpec () {
        return SimpleTableNodeSpec.NODE;
    }

    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        super.setAttributes ( parameters );

        horizontalColumnAlignmentsString = parameters == null ? null :
            parameters.castedValue ( TableNodeSpec.HORIZONTAL_ALIGNMENTS_ATTRIBUTE  );
    }

    @Override
    public void onNodeParsed () throws Exception {

        super.onNodeParsed ();

        @Nullable String rawText = getRawText();
        if ( rawText == null || rawText.isEmpty () ) {
            throw new DataValidatorException (
                "Table data (rows) are required.",
                "TABLE_CANNOT_BE_EMPTY",
                getStartToken (),
                null );
        }

        @NotNull List<String> lines = TextLines.textToLines ( rawText );
        @NotNull String firstLine = lines.get ( 0 );
        @Nullable Pattern cellSeparator = cellSeparator ( firstLine );
        int columnCount = columnCount ( firstLine, cellSeparator );

        headerCells = headerCells ( lines, cellSeparator );
        footerCells = footerCells ( lines, cellSeparator );

        bodyRows = bodyRows ( lines, cellSeparator );

        checkColumnCounts ( columnCount, headerCells, footerCells,  bodyRows );

        horizontalColumnAlignments = parseHorizontalColumnAlignments (
            horizontalColumnAlignmentsString, columnCount, startToken );
    }

    private @Nullable Pattern cellSeparator ( @NotNull String firstLine ) {

        // if ( firstLine.indexOf ( '\t' ) >= 0 ) return TAB_SEPARATOR;
        if ( firstLine.indexOf ( '|' ) >= 0 ) return VERTICAL_BAR_SEPARATOR;
        if ( firstLine.indexOf ( ',' ) >= 0 ) return COMMA_SEPARATOR;
        if ( firstLine.indexOf ( ';' ) >= 0 ) return SEMICOLON_SEPARATOR;
        return null;
    }

    private int columnCount ( @NotNull String firstLine, @Nullable Pattern cellSeparator ) {

        return cellSeparator == null ? 1 : cellSeparator.split ( firstLine ).length;
    }

    private @Nullable List<String> headerCells ( @NotNull List<String> lines, @Nullable Pattern cellSeparator ) {

        if ( lines.size () < 2 ) return null;
        if ( ! lines.get ( 1 ).equals ( "-" ) ) return null;

        String firstLine = lines.get ( 0 );
        lines.remove ( 0 );
        lines.remove ( 0 );

        return rowCells ( firstLine, cellSeparator );
    }

    private @Nullable List<String> footerCells ( @NotNull List<String> lines, @Nullable Pattern cellSeparator ) {

        if ( lines.size() < 2 ) return null;
        if ( ! lines.get ( lines.size() - 2 ).equals ( "-" ) ) return null;

        String lastLine = lines.get ( lines.size() - 1 );
        lines.remove ( lines.size() - 1 );
        lines.remove ( lines.size() - 1 );

        return rowCells ( lastLine, cellSeparator );
    }

    private @NotNull List<List<String>> bodyRows (
        @NotNull List<String> lines,
        @Nullable Pattern cellSeparator ) throws DataValidatorException {

        if ( lines.isEmpty () ) throw new DataValidatorException (
            "There are no body rows defined.",
            "TABLE_ROWS_REQUIRED",
            getStartToken (),
            null );

        List<List<String>> rows = new ArrayList<> ();
        for ( @NotNull String line : lines ) {
            rows.add ( rowCells ( line, cellSeparator ) );
        }

        return rows;
    }

    private @NotNull List<String> rowCells ( @NotNull String row, @Nullable Pattern cellSeparator ) {

        if ( cellSeparator == null ) {
            return List.of ( row );
        } else {
            return List.of ( cellSeparator.split ( row ) );
        }
    }

    private void checkColumnCounts (
        int columnCount,
        @Nullable List<String> headerCells,
        @Nullable List<String> footerCells,
        @NotNull List<List<String>> bodyRows ) throws DataValidatorException {

        if ( headerCells != null ) checkColumnCount ( columnCount, headerCells );
        if ( footerCells != null ) checkColumnCount ( columnCount, footerCells );

        for ( @NotNull List<String> row : bodyRows ) {
            checkColumnCount ( columnCount, row );
        }
    }

    private void checkColumnCount (
        int columnCount,
        @NotNull List<String> cells ) throws DataValidatorException {

        if ( cells.size() != columnCount ) throw new DataValidatorException (
            "The following row should contain " + columnCount + " cell(s), but it contains " + cells.size() + " cell(s): " + cells.toString(),
            "INVALID_COLUMN_COUNT",
            startToken, null );
    }
}
