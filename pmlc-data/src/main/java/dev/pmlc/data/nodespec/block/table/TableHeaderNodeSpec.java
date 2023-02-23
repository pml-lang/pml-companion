package dev.pmlc.data.nodespec.block.table;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.table.TableHeaderNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class TableHeaderNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "theader" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Table Header",
        "A header in a table.",
        """
            Table with header:

            [table
                [theader
                    [tr [tc Header 1] [tc Header 2] [tc Header 3]]
                ]
                [tr [tc cell 1.1] [tc cell 1.2] [tc cell 1.3]]
                [tr [tc cell 2.1] [tc cell 2.2] [tc cell 2.3]]
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "thead";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "table-header" );

    public static final @NotNull PMLNodeSpec<Void, TableHeaderNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        TableHeaderNode::new, PMLNodesHandler::tableHeader, HTML_TAG, CSS_CLASS );
}
