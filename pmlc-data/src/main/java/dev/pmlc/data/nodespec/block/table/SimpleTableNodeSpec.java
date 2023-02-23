package dev.pmlc.data.nodespec.block.table;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.table.SimpleTableNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class SimpleTableNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "sim_table" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Simple Table",
        """
            Simple table data defined as plain text, and rendered as a table.
            Each line of text represents a row.
            All cell values in all rows are separated by the same character which can be: a vertical bar (|), a semicolon (;), or a comma (,).
            The content of each cell can only be plain text. If formatted text or complex cell content (e.g. picture in a cell, or table in a table) is needed, then please use the 'table' tag.
            A header and footer can optionally be defined.
            The horizontal alignment (left, center, or right) can be specified for each column.
            The data will be rendered as a table.
            The following format rules apply:
            - Rows are separated by a line break. Empty lines are ignored.
            - Cells can be separated by a comma or a TAB character, but both separators cannot be mixed in a table.
            - Spaces before or after cell values are ignored.
            - If the second line is a dash (-) then the first line is a header.
            - If the second-last line is a dash (-) then the last line is a footer.
            """,
        """
            Simple table:
            [sim_table
                ~~~
                cell 1.1, cell 1.2
                cell 2.1, cell 2.2
                ~~~
            ]

            Table with header, footer, and column alignments:

            [sim_table (halign="C,L,R")
                ~~~
                Position, Product, Price
                -
                1, Organic food, 12.50
                2, Meditation lessons, 150.00
                -
                ,,Total: 162.50
                ~~~
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.mutableListWithIdAttribute()
        .add ( TableNodeSpec.HORIZONTAL_ALIGNMENTS_ATTRIBUTE )
        .copyToImmutable();

    public static final @NotNull String HTML_TAG = "table";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "table" );

    public static final @NotNull PMLNodeSpec<String, SimpleTableNode> NODE = PMLNodeSpecCreator.createForRawTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        SimpleTableNode::new, PMLNodesHandler::tableData, HTML_TAG, CSS_CLASS );
}
