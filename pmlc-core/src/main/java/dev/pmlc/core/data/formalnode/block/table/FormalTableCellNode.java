package dev.pmlc.core.data.formalnode.block.table;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.table.TableCellNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalTableCellNode {

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

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "td";
    public static final @NotNull String HTML_TAG_IN_HEADER = "th";
    public static final @NotNull String HTML_TAG_IN_FOOTER = "td";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "table-body-cell" );
    public static final @NotNull String CSS_CLASS_IN_HEADER = FormalPMLNodeCreator.prefixedHTMLClassName ( "table-header-cell" );
    public static final @NotNull String CSS_CLASS_IN_FOOTER = FormalPMLNodeCreator.prefixedHTMLClassName ( "table-footer-cell" );

    public static final @NotNull FormalPMLNode<Void, TableCellNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        TableCellNode::new, PMLNodesHandler::tableCell, HTML_TAG, CSS_CLASS );
}
