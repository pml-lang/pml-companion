package dev.pmlc.core.data.formalnode.inline;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.SpaceNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalSpaceNode {

    public static final @NotNull NodeName NAME = new NodeName ( "sp" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Space Character",
        "An explicit space character inserted in the document. Whitespace composed of several characters (such as two spaces) is always converted into a single space (as in HTML). Hence, this node can be used to explicitly insert two or more spaces.",
        """
        This is  g  o  o  d !  [- 2 spaces will result in ONE space -]

        This is [sp]g[sp][sp]o[sp][sp]o[sp][sp]d[sp]!""" );

    public static final @NotNull String HTML_TAG = "";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "space" );

    public static final @NotNull FormalPMLNode<Void, SpaceNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, null, false, false, DOCUMENTATION,
        SpaceNode::new, PMLNodesHandler::space, HTML_TAG, CSS_CLASS );
}
