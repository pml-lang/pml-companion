package dev.pmlc.core.data.formalnode.inline;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.node.inline.LinkNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalLinkNode {

    public static final @NotNull NodeName NAME = new NodeName ( "link" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "URL Link",
        "A URL link to a resource, such as a website or a file to download.",
        "Please have a look at [link url=https://www.pml-lang.dev/docs/articles/practical-document-markup-language/index.html text=\"this article\"] for more information." );

    public static final @NotNull FormalParameter<String> URL_ATTRIBUTE = new FormalParameter.Builder<> (
        "url", CommonDataTypes.STRING )
        .documentation ( "URL",
            "The Uniform Resource Locator (URL) of the link.",
            "url = https://www.ppl-lang.dev" )
        .build();

    public static final @NotNull FormalParameter<String> TEXT_ATTRIBUTE = new FormalParameter.Builder<> (
        "text", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "URL Text",
            "The text to be displayed instead of the URL. If no text is specified then the URL itself is displayed.",
            "text = our website" )
        .build();

    public static final @NotNull FormalParameters ATTRIBUTES = new FormalParameters()
        .add ( URL_ATTRIBUTE )
        .add ( TEXT_ATTRIBUTE );

    public static final @NotNull String HTML_TAG = "a";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "link" );

    public static final @NotNull FormalPMLNode<Void, LinkNode> NODE = FormalPMLNodeCreator.createForInlineNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        LinkNode::new, PMLNodesHandler::link, HTML_TAG, CSS_CLASS );
}
