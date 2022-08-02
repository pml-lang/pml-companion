package dev.pmlc.core.data.formalnode.block;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.MonospaceNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalMonospaceNode {

    public static final @NotNull NodeName NAME = new NodeName ( "monospace" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Monospace",
        """
            A paragraph in which whitespace is preserved, and a fixed-width font is used. The text will be rendered exactly as written in the PML document.
            The following rules apply to the text in a 'monospace' block:
            - The width of each character (letter, digit, symbol) is the same.
            - All spaces and line-breaks in the text are preserved and rendered in the final document.
            Inline nodes can be used within a 'monospace' block. Hence standard character escape rules are applied.
            """,
        """
            [header A Pascal Triangle]
            [monospace
                  1
                1   1
              1   2   1
            1   3   3   1
            ]
            """ );


    /*
    r.add_yes_no_parameter (
        ids = [# keep_indent_parameter_id.value, "ki" ]
        default_value_supplier = { no }
        title = "Keep Indent"
        description = """By default the indent (i.e. preceding space characters) of the first text line is removed in all lines of the 'monospace' block. This behavior can be disabled by setting '{{a_keep_indent_parameter_id}}' to 'yes'."""
        examples = "keep_indent = yes" )
    */

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "pre";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "monospace" );

    public static final @NotNull FormalPMLNode<Void, MonospaceNode> NODE =
        FormalPMLNodeCreator.createForBlockNodeWithInlineChildNodes (
            NAME, ATTRIBUTES, true,
            DOCUMENTATION, MonospaceNode::new, PMLNodesHandler::monospace, HTML_TAG, CSS_CLASS );
}
