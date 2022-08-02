package dev.pmlc.core.data.formalnode.inline.font;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.font.ItalicNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalItalicNode {

    public static final @NotNull NodeName NAME = new NodeName ( "i" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Italic Text",
        null,
        """
        We want [i simplicity].

        This is a [b [i very] important] point.""" );

    public static final @NotNull String HTML_TAG = "i";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "italic" );

    public static final @NotNull FormalPMLNode<Void, ItalicNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, null, true, true, DOCUMENTATION,
        ItalicNode::new, PMLNodesHandler::italic, HTML_TAG, CSS_CLASS );
}
