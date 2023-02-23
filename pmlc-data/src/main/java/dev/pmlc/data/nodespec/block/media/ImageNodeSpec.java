package dev.pmlc.data.nodespec.block.media;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.media.ImageNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class ImageNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "image" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Image",
        """
            An image, such as a .jpg or .png file.
            Note: Attribute 'html_alt' can be used to add an explicit 'alt' attribute in the resulting HTML (see example).
            """,
            // If no explicit 'html_alt' is provided then the value of the 'title' attribute is used for the 'alt' attribute.
            // If no 'title' attribute is provided then the value of the 'caption' attribute is used.
        """
            [header Strawberries (Photo by Jacek Dylag on Unsplash)]
            [image ( source = media/strawberries.jpg
                link = https://unsplash.com/photos/kH3Sr9K8EBA
                html_alt = "Delicious strawberries" ) ]
            """ );

    public static final @NotNull ParameterSpec<String> SOURCE_ATTRIBUTE =
        SharedMediaNodeSpecAttributes.source ( "Image Source", "source = images/ball.png" );

    public static final @NotNull ParameterSpec<Integer> WIDTH_ATTRIBUTE =
        SharedMediaNodeSpecAttributes.width ( "Image Width" );

    public static final @NotNull ParameterSpec<Integer> HEIGHT_ATTRIBUTE =
        SharedMediaNodeSpecAttributes.height ( "Image Height" );

    public static final @NotNull ParameterSpec<HTextAlign> HORIZONTAL_ALIGNMENT =
        SharedMediaNodeSpecAttributes.HORIZONTAL_ALIGNMENT;

    public static final @NotNull ParameterSpec<Boolean> BORDER =
        SharedMediaNodeSpecAttributes.BORDER;

    public static final @NotNull ParameterSpec<String> LINK =
        SharedMediaNodeSpecAttributes.LINK;

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.mutableListWithIdAttribute()
        .add ( SOURCE_ATTRIBUTE )
        .add ( WIDTH_ATTRIBUTE )
        .add ( HEIGHT_ATTRIBUTE )
        .add ( HORIZONTAL_ALIGNMENT )
        .add ( BORDER )
        .add ( LINK )
        .copyToImmutable();

    public static final @NotNull String HTML_TAG = "img";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "image" );

    public static final @NotNull PMLNodeSpec<Void, ImageNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        ImageNode::new, PMLNodesHandler::image, HTML_TAG, CSS_CLASS );
}
