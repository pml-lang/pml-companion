package dev.pmlc.data.nodespec.block.table;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.table.TableCellNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class TableCellNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "tc" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Table Cell",
        "A cell in a table row.",
        """
            Table with 2 rows and 3 columns:

            [table
                [tr [tc cell 1.1] [tc cell 1.2] [tc cell 1.3]]
                [tr [tc cell 2.1] [tc cell 2.2] [tc cell 2.3]]
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "td";
    public static final @NotNull String HTML_TAG_IN_HEADER = "th";
    public static final @NotNull String HTML_TAG_IN_FOOTER = "td";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "table-body-cell" );
    public static final @NotNull String CSS_CLASS_IN_HEADER = PMLNodeSpecCreator.prefixedHTMLClassName ( "table-header-cell" );
    public static final @NotNull String CSS_CLASS_IN_FOOTER = PMLNodeSpecCreator.prefixedHTMLClassName ( "table-footer-cell" );

    public static final @NotNull PMLNodeSpec<Void, TableCellNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        TableCellNode::new, PMLNodesHandler::tableCell, HTML_TAG, CSS_CLASS );
}
