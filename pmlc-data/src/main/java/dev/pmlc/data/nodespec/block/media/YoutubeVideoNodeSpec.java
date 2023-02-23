package dev.pmlc.data.nodespec.block.media;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.media.YoutubeVideoNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class YoutubeVideoNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "youtube_video" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Embedded Youtube Video",
        "A Youtube video embedded in the document.",
        """
            [header Hellzapoppin 1941 - [i Enjoy!]]
            [youtube_video (
                yid = qrcZqnICYbs
                width = 600
                height = 400 ) ]
            """ );

    public static final @NotNull ParameterSpec<String> YOUTUBE_ID_PARAMETER = new ParameterSpec.Builder<> (
        "yid", CommonDataTypes.STRING )
        .documentation ( "Youtube Video Id",
            """
            The identifier of the Youtube video.
            This identifier is displayed in the video's URL on Youtube.
            Example: If the URL of the Youtube video is https://www.youtube.com/watch?v=NUDhA4hXdS8 then the identifier is NUDhA4hXdS8.
            """,
            "yid = NUDhA4hXdS8" )
        .build();

    public static final @NotNull ParameterSpec<Integer> WIDTH_ATTRIBUTE =
        VideoNodeSpec.WIDTH_ATTRIBUTE;

    public static final @NotNull ParameterSpec<Integer> HEIGHT_ATTRIBUTE =
        VideoNodeSpec.HEIGHT_ATTRIBUTE;

    public static final @NotNull ParameterSpec<HTextAlign> HORIZONTAL_ALIGNMENT =
        SharedMediaNodeSpecAttributes.HORIZONTAL_ALIGNMENT;

    public static final @NotNull ParameterSpec<Boolean> BORDER =
        SharedMediaNodeSpecAttributes.BORDER;

    // TODO add other Youtube parameters
    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.mutableListWithIdAttribute()
        .add ( YOUTUBE_ID_PARAMETER )
        .add ( WIDTH_ATTRIBUTE )
        .add ( HEIGHT_ATTRIBUTE )
        .add ( HORIZONTAL_ALIGNMENT )
        .add ( BORDER )
        .copyToImmutable();

    public static final @NotNull String HTML_TAG = "iframe";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "youtube-video" );

    public static final @NotNull PMLNodeSpec<Void, YoutubeVideoNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        YoutubeVideoNode::new, PMLNodesHandler::youtubeVideo, HTML_TAG, CSS_CLASS );
}
