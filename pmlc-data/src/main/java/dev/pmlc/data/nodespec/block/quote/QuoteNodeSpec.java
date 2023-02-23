package dev.pmlc.data.nodespec.block.quote;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.quote.QuoteNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class QuoteNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "quote" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Quote",
        "The exact words said or written by somebody else.",
        """
            [quote
                Everything should be as simple as possible, but not simpler.
                [qsource [i Albert Einstein, physicist]]
            ]""" );

    @Deprecated
    public static final @NotNull ParameterSpec<String> SOURCE_ATTRIBUTE__REMOVED = new ParameterSpec.Builder<> (
        "source", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "Source of Quote",
            "The source of the quote, such as the name of a person, the title of an article, etc.",
            "source = Dalai Lama" )
        .build();

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.listWithIdAttribute();

    public static final @NotNull String HTML_TAG = "blockquote";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "quote" );

    public static final @NotNull PMLNodeSpec<Void, QuoteNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        QuoteNode::new, PMLNodesHandler::quote, HTML_TAG, CSS_CLASS );
}
