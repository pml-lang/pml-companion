package dev.pmlc.core.data.formalnode.block.code;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.code.HTMLCodeNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalHTMLCodeNode {

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

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "html" );

    public static final @NotNull FormalPMLNode<String, HTMLCodeNode> NODE = FormalPMLNodeCreator.createForRawTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        HTMLCodeNode::new, PMLNodesHandler::HTMLCode, HTML_TAG, CSS_CLASS );
}
