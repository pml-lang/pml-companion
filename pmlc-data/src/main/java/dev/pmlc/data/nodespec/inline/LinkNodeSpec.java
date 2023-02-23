package dev.pmlc.data.nodespec.inline;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.node.inline.LinkNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableParameterSpecs;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class LinkNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "link" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "URL Link",
        "A URL link to a resource, such as a website or a file to download.",
        "Please have a look at [link (url=https://www.pml-lang.dev/docs/articles/practical-document-markup-language/index.html) this article] for more information." );

    public static final @NotNull ParameterSpec<String> URL_ATTRIBUTE = new ParameterSpec.Builder<> (
        "url", CommonDataTypes.STRING )
        .documentation ( "URL",
            "The Uniform Resource Locator (URL) of the link.",
            "url = https://www.ppl-lang.dev" )
        .build();

    @Deprecated
    public static final @NotNull ParameterSpec<String> TEXT_ATTRIBUTE__REMOVED = new ParameterSpec.Builder<> (
        "text", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "URL Text",
            "The text to be displayed instead of the URL. If no text is specified then the URL itself is displayed.",
            "text = our website" )
        .build();

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = new MutableParameterSpecs()
        .add ( URL_ATTRIBUTE )
        .makeImmutable();

    public static final @NotNull String HTML_TAG = "a";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "link" );

    public static final @NotNull PMLNodeSpec<Void, LinkNode> NODE = PMLNodeSpecCreator.createForInlineNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        LinkNode::new, PMLNodesHandler::link, HTML_TAG, CSS_CLASS );
}
