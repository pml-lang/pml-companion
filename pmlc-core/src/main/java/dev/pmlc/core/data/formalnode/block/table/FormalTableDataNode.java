package dev.pmlc.core.data.formalnode.block.table;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.table.TableDataNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalTableDataNode {

    public static final @NotNull NodeName NAME = new NodeName ( "table_data" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Table Data",
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
            [table_data
                cell 1.1, cell 1.2
                cell 2.1, cell 2.2
            table_data]

            Table with header, footer, and column alignments:

            [table_data (halign="C,L,R")
                ~~~
                Position, Product, Price
                -
                1, Organic food, 12.50
                2, Meditation lessons, 150.00
                -
                ,,Total: 162.50
                ~~~
            ]""" );

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute()
        .add ( FormalTableNode.HORIZONTAL_ALIGNMENTS_ATTRIBUTE );

    public static final @NotNull String HTML_TAG = "table";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "table" );

    public static final @NotNull FormalPMLNode<String, TableDataNode> NODE = FormalPMLNodeCreator.createForRawTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        TableDataNode::new, PMLNodesHandler::tableData, HTML_TAG, CSS_CLASS );
}
