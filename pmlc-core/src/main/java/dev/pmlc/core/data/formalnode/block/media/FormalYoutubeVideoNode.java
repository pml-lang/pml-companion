package dev.pmlc.core.data.formalnode.block.media;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.media.YoutubeVideoNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalYoutubeVideoNode {

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

    public static final @NotNull FormalParameter<String> YOUTUBE_ID_PARAMETER = new FormalParameter.Builder<> (
        "yid", CommonDataTypes.STRING )
        .documentation ( "Youtube Video Id",
            """
            The identifier of the Youtube video.
            This identifier is displayed in the video's URL on Youtube.
            Example: If the URL of the Youtube video is https://www.youtube.com/watch?v=NUDhA4hXdS8 then the identifier is NUDhA4hXdS8.
            """,
            "yid = NUDhA4hXdS8" )
        .build();

    public static final @NotNull FormalParameter<Integer> WIDTH_ATTRIBUTE =
        FormalVideoNode.WIDTH_ATTRIBUTE;

    public static final @NotNull FormalParameter<Integer> HEIGHT_ATTRIBUTE =
        FormalVideoNode.HEIGHT_ATTRIBUTE;

    public static final @NotNull FormalParameter<HTextAlign> HORIZONTAL_ALIGNMENT =
        SharedMediaFormalNodeAttributes.HORIZONTAL_ALIGNMENT;

    public static final @NotNull FormalParameter<Boolean> BORDER =
        SharedMediaFormalNodeAttributes.BORDER;

    // TODO add other Youtube parameters
    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute()
        .add ( YOUTUBE_ID_PARAMETER )
        .add ( WIDTH_ATTRIBUTE )
        .add ( HEIGHT_ATTRIBUTE )
        .add ( HORIZONTAL_ALIGNMENT )
        .add ( BORDER );

    public static final @NotNull String HTML_TAG = "iframe";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "youtube-video" );

    public static final @NotNull FormalPMLNode<Void, YoutubeVideoNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        YoutubeVideoNode::new, PMLNodesHandler::youtubeVideo, HTML_TAG, CSS_CLASS );
}
