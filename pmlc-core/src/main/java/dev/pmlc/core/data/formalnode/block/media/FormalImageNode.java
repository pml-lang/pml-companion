package dev.pmlc.core.data.formalnode.block.media;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.media.ImageNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalImageNode {

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

    public static final @NotNull FormalParameter<String> SOURCE_ATTRIBUTE =
        SharedMediaFormalNodeAttributes.source ( "Image Source", "source = images/ball.png" );

    public static final @NotNull FormalParameter<Integer> WIDTH_ATTRIBUTE =
        SharedMediaFormalNodeAttributes.width ( "Image Width" );

    public static final @NotNull FormalParameter<Integer> HEIGHT_ATTRIBUTE =
        SharedMediaFormalNodeAttributes.height ( "Image Height" );

    public static final @NotNull FormalParameter<HTextAlign> HORIZONTAL_ALIGNMENT =
        SharedMediaFormalNodeAttributes.HORIZONTAL_ALIGNMENT;

    public static final @NotNull FormalParameter<Boolean> BORDER =
        SharedMediaFormalNodeAttributes.BORDER;

    public static final @NotNull FormalParameter<String> LINK =
        SharedMediaFormalNodeAttributes.LINK;

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute()
        .add ( SOURCE_ATTRIBUTE )
        .add ( WIDTH_ATTRIBUTE )
        .add ( HEIGHT_ATTRIBUTE )
        .add ( HORIZONTAL_ALIGNMENT )
        .add ( BORDER )
        .add ( LINK );

    public static final @NotNull String HTML_TAG = "img";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "image" );

    public static final @NotNull FormalPMLNode<Void, ImageNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        ImageNode::new, PMLNodesHandler::image, HTML_TAG, CSS_CLASS );
}
