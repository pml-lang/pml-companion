package dev.pmlc.data.nodespec.inline;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.VerbatimTextNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class VerbatimTextNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "verbatim" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Verbatim Text",
        """
            Raw, verbatim text that is rendered as is, without any transformations.
            Note: The text still has to be escaped, according to the standard PML escaping rules.""",
        "Math's ring operator is [verbatim &#x2218;]." );

    public static final @NotNull String HTML_TAG = "";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "verbatim" );

    public static final @NotNull PMLNodeSpec<String, VerbatimTextNode> NODE = PMLNodeSpecCreator.createForRawTextInlineNode (
        NAME, null, true, DOCUMENTATION,
        VerbatimTextNode::new, PMLNodesHandler::verbatimText, HTML_TAG, CSS_CLASS );
}
