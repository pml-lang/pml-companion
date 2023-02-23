package dev.pmlc.data.nodespec.block.code;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.code.HTMLCodeNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class HtmlCodeNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "html" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "HTML Code",
        """
        A node that contains HTML code.
        The HTML code is not processed in any way by the PML converter. It is passed as is to the resulting HTML document.
        This allows you to embed arbitrary HTML code in your final document.""",
        """
            [html
                ~~~
                <p>This is pure <b>HTML</b> code.</p>
                ~~~
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "html" );

    public static final @NotNull PMLNodeSpec<String, HTMLCodeNode> NODE = PMLNodeSpecCreator.createForRawTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        HTMLCodeNode::new, PMLNodesHandler::HTMLCode, HTML_TAG, CSS_CLASS );
}
