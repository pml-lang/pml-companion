package dev.pmlc.data.nodespec.inline;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.NewLineNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class NewLineNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "nl" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "New Line",
        "An explicit new line (line break) inserted in the document.",
        "Line 1[nl][nl]Line 2" );

    public static final @NotNull String HTML_TAG = "";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "new-line" );

    public static final @NotNull PMLNodeSpec<Void, NewLineNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, null, false, false, DOCUMENTATION,
        NewLineNode::new, PMLNodesHandler::newLine, HTML_TAG, CSS_CLASS );
}
