package dev.pmlc.core.data.formalnode.block.table;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.table.TableRowNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalTableRowNode {

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

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "tr";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "table-body-row" );
    public static final @NotNull String CSS_CLASS_IN_HEADER = FormalPMLNodeCreator.prefixedHTMLClassName ( "table-header-row" );
    public static final @NotNull String CSS_CLASS_IN_FOOTER = FormalPMLNodeCreator.prefixedHTMLClassName ( "table-footer-row" );

    public static final @NotNull FormalPMLNode<Void, TableRowNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        TableRowNode::new, PMLNodesHandler::tableRow, HTML_TAG, CSS_CLASS );
}
