package dev.pmlc.data.nodespec.block.list;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.list.ListNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class ListNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "list" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "List",
        "A bulleted list of nodes.\n" +
            "CSS can be used to change the item marker's appearance (e.g. use numbers). The CSS must be assigned to attribute 'html_style'. See examples.",
        """
            Standard list:
            [list
                [el item 1]
                [el item 2]
            ]

            Numbered list:
            [list (html_style="list-style-type:decimal")
                [el item 1]
                [el item 2]
            ]

            No markers:
            [list (html_style="list-style-type:none")
                [el item 1]
                [el item 2]
            ]

            Picture for marker:
            [list (html_style="list-style-image: url('images/gold_star.png')")
                [el item 1]
                [el item 2]
            ]

            Text for marker:
            [list (html_style="list-style-type: 'x '")
                [el item 1]
                [el item 2]
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "ul";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "list" );

    public static final @NotNull PMLNodeSpec<Void, ListNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        ListNode::new, PMLNodesHandler::list, HTML_TAG, CSS_CLASS );
}
