package dev.pmlc.core.data.formalnode.block.table;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.table.TableNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalTableNode {

    public static final @NotNull NodeName NAME = new NodeName ( "table" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Table",
        """
            A table consisting of rows and columns.
            The content of each cell can be just plain text or any complex node, such as formatted text, a picture, a table (table in a table), etc.
            The table's structure is similar to a HTML table.
            HTML 'class' and 'style' attributes can be use to format the table with CSS.
            The table can optionally have a header and a footer.""",
        """
            Simple table:

            [table
                [tr [tc cell 1.1] [tc cell 1.2]]
                [tr [tc cell 2.1] [tc cell 2.2]]
            ]

            Table with header and footer:

            [table
                [theader
                    [tr [tc Position] [tc Product] [tc Price]]
                ]
                [tr [tc 1] [tc [i Organic] food] [tc (html_style="text-align:right;") 12.50]]
                [tr [tc 2] [tc Meditation lessons] [tc (html_style="text-align:right;") 150.00]]
                [tfooter
                    [tr [tc] [tc (html_style="text-align:right;") Total:] [tc (html_style="text-align:right;") 162.50]]
                ]
            ]""" );

    public static final @NotNull FormalParameter<String> HORIZONTAL_ALIGNMENTS_ATTRIBUTE = new FormalParameter.Builder<> (
        "halign", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "Horizontal Alignments for Table Columns",
            """
                This optional parameter specifies the horizontal alignment for each column in a table.
                The alignments are defined as a comma-separated list, in the order of the columns.
                For each column the following values can be specified:
                - left or l
                - center or c
                - right or r
                - {empty} (default alignment (usually left-aligned) will be used)
                The values are case-insensitive. Uppercase letters are allowed.""",
            """
                halign = "Left, Center, , Right"
                short from:
                halign = "L, C, , R""" )
        .build();

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute()
        .add ( HORIZONTAL_ALIGNMENTS_ATTRIBUTE );

    public static final @NotNull String HTML_TAG = "table";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "table" );

    public static final @NotNull FormalPMLNode<Void, TableNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        TableNode::new, PMLNodesHandler::table, HTML_TAG, CSS_CLASS );
}
