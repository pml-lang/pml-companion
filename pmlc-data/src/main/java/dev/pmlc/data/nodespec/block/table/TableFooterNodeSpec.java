package dev.pmlc.data.nodespec.block.table;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.table.TableFooterNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class TableFooterNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "tfooter" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Table Footer",
        "A footer in a table.",
        """
            Table with footer:

            [table
                [tr [tc cell 1.1] [tc cell 1.2] [tc cell 1.3]]
                [tr [tc cell 2.1] [tc cell 2.2] [tc cell 2.3]]
                [tfooter
                    [tr [tc Footer 1] [tc Footer 2] [tc Footer 3]]
                ]
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "tfoot";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "table-footer" );

    public static final @NotNull PMLNodeSpec<Void, TableFooterNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        TableFooterNode::new, PMLNodesHandler::tableFooter, HTML_TAG, CSS_CLASS );
}
