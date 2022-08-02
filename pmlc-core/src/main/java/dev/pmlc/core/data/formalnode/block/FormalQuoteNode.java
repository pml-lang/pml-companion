package dev.pmlc.core.data.formalnode.block;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.QuoteNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalQuoteNode {

    public static final @NotNull NodeName NAME = new NodeName ( "quote" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Quote",
        "A piece of text said or written by somebody else.",
        """
            [quote (source="Albert Einstein, physicist")
                Everything should be as simple as possible, but not simpler.
            ]""" );

    public static final @NotNull FormalParameter<String> SOURCE_ATTRIBUTE = new FormalParameter.Builder<> (
        "source", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "Source of Quote",
            "The source of the quote, such as the name of a person, the title of an article, etc.",
            "source = Dalai Lama" )
        .build();

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute()
        .add ( SOURCE_ATTRIBUTE );

    public static final @NotNull String HTML_TAG = "blockquote";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "quote" );

    public static final @NotNull FormalPMLNode<Void, QuoteNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        QuoteNode::new, PMLNodesHandler::quote, HTML_TAG, CSS_CLASS );
}
