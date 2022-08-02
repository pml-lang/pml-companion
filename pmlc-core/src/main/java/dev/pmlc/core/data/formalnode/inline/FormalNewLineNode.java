package dev.pmlc.core.data.formalnode.inline;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.NewLineNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalNewLineNode {

    public static final @NotNull NodeName NAME = new NodeName ( "nl" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "New Line",
        "An explicit new line (line break) inserted in the document.",
        "Line 1[nl][nl]Line 2" );

    public static final @NotNull String HTML_TAG = "";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "new-line" );

    public static final @NotNull FormalPMLNode<Void, NewLineNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, null, false, false, DOCUMENTATION,
        NewLineNode::new, PMLNodesHandler::newLine, HTML_TAG, CSS_CLASS );
}
