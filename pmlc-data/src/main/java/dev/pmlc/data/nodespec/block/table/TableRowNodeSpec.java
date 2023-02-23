package dev.pmlc.data.nodespec.block.table;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.table.TableRowNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class TableRowNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "tr" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Table Row",
        "A row in a table.",
        """
            Table with 2 rows:

            [table
                [tr [tc cell 1.1] [tc cell 1.2] [tc cell 1.3]]
                [tr [tc cell 2.1] [tc cell 2.2] [tc row 2.3]]
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "tr";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "table-body-row" );
    public static final @NotNull String CSS_CLASS_IN_HEADER = PMLNodeSpecCreator.prefixedHTMLClassName ( "table-header-row" );
    public static final @NotNull String CSS_CLASS_IN_FOOTER = PMLNodeSpecCreator.prefixedHTMLClassName ( "table-footer-row" );

    public static final @NotNull PMLNodeSpec<Void, TableRowNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        TableRowNode::new, PMLNodesHandler::tableRow, HTML_TAG, CSS_CLASS );
}
