package dev.pmlc.core.data.formalnode.block.media;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.media.VideoNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalVideoNode {

    public static final @NotNull NodeName NAME = new NodeName ( "video" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Video",
        "A video, such as an .mp4 or .webm file.",
        """
            [header Beautiful Nature]
            [video source = media/red_flower.mp4]
            """ );

    public static final @NotNull FormalParameter<String> SOURCE_ATTRIBUTE =
        SharedMediaFormalNodeAttributes.source ( "Video Source", "source = videos/happy_kids.mp4" );

    public static final @NotNull FormalParameter<Integer> WIDTH_ATTRIBUTE =
        SharedMediaFormalNodeAttributes.width ( "Video Width" );

    public static final @NotNull FormalParameter<Integer> HEIGHT_ATTRIBUTE =
        SharedMediaFormalNodeAttributes.height ( "Video Height" );

    public static final @NotNull FormalParameter<HTextAlign> HORIZONTAL_ALIGNMENT =
        SharedMediaFormalNodeAttributes.HORIZONTAL_ALIGNMENT;

    public static final @NotNull FormalParameter<Boolean> BORDER =
        SharedMediaFormalNodeAttributes.BORDER;

    // TODO add autoplay, controls, loop, muted, poster (see https://www.w3schools.com/tags/tag_video.asp)
    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute()
        .add ( SOURCE_ATTRIBUTE )
        .add ( WIDTH_ATTRIBUTE )
        .add ( HEIGHT_ATTRIBUTE )
        .add ( HORIZONTAL_ALIGNMENT )
        .add ( BORDER );

    public static final @NotNull String HTML_TAG = "video";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "video" );

    public static final @NotNull FormalPMLNode<Void, VideoNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        VideoNode::new, PMLNodesHandler::video, HTML_TAG, CSS_CLASS );
}
