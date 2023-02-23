package dev.pmlc.data.nodespec.block.media;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.media.VideoNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class VideoNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "video" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Video",
        "A video, such as an .mp4 or .webm file.",
        """
            [header Beautiful Nature]
            [video source = media/red_flower.mp4]
            """ );

    public static final @NotNull ParameterSpec<String> SOURCE_ATTRIBUTE =
        SharedMediaNodeSpecAttributes.source ( "Video Source", "source = videos/happy_kids.mp4" );

    public static final @NotNull ParameterSpec<Integer> WIDTH_ATTRIBUTE =
        SharedMediaNodeSpecAttributes.width ( "Video Width" );

    public static final @NotNull ParameterSpec<Integer> HEIGHT_ATTRIBUTE =
        SharedMediaNodeSpecAttributes.height ( "Video Height" );

    public static final @NotNull ParameterSpec<HTextAlign> HORIZONTAL_ALIGNMENT =
        SharedMediaNodeSpecAttributes.HORIZONTAL_ALIGNMENT;

    public static final @NotNull ParameterSpec<Boolean> BORDER =
        SharedMediaNodeSpecAttributes.BORDER;

    // TODO add autoplay, controls, loop, muted, poster (see https://www.w3schools.com/tags/tag_video.asp)
    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.mutableListWithIdAttribute()
        .add ( SOURCE_ATTRIBUTE )
        .add ( WIDTH_ATTRIBUTE )
        .add ( HEIGHT_ATTRIBUTE )
        .add ( HORIZONTAL_ALIGNMENT )
        .add ( BORDER )
        .copyToImmutable();

    public static final @NotNull String HTML_TAG = "video";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "video" );

    public static final @NotNull PMLNodeSpec<Void, VideoNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        VideoNode::new, PMLNodesHandler::video, HTML_TAG, CSS_CLASS );
}
