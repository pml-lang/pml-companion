package dev.pmlc.data.nodespec.inline;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.SpaceNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class SpaceNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "sp" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Space Character",
        "An explicit space character inserted in the document. Whitespace composed of several characters (such as two spaces) is always converted into a single space (as in HTML). Hence, this node can be used to explicitly insert two or more spaces.",
        """
        This is  g  o  o  d !  [- 2 spaces will result in ONE space -]

        This is [sp]g[sp][sp]o[sp][sp]o[sp][sp]d[sp]!""" );

    public static final @NotNull String HTML_TAG = "";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "space" );

    public static final @NotNull PMLNodeSpec<Void, SpaceNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, null, false, false, DOCUMENTATION,
        SpaceNode::new, PMLNodesHandler::space, HTML_TAG, CSS_CLASS );
}
