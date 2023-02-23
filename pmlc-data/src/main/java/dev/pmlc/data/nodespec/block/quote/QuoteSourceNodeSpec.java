package dev.pmlc.data.nodespec.block.quote;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.node.block.quote.QuoteSourceNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class QuoteSourceNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "qsource" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Source of Quote",
        "The source of the quote, such as the name of a person, the title of an article, etc.",
        """
            [quote
                Everything should be as simple as possible, but not simpler.
                [qsource [i Albert Einstein, physicist]]
            ]""" );

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "quote-source" );

    public static final @NotNull PMLNodeSpec<Void, QuoteSourceNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        QuoteSourceNode::new, PMLNodesHandler::quoteSource, HTML_TAG, CSS_CLASS );
}
