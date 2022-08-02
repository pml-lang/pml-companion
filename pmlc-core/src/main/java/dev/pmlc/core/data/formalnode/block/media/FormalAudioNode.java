package dev.pmlc.core.data.formalnode.block.media;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.media.AudioNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalAudioNode {

    public static final @NotNull NodeName NAME = new NodeName ( "audio" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Audio (Sound)",
        "An audio stream (sound), such as an .mp3 or .wav file.",
        """
           [header Bird talk]
           [audio source=media/bird_talk.mp3]
            """ );

    public static final @NotNull FormalParameter<String> SOURCE_ATTRIBUTE = SharedMediaFormalNodeAttributes.source (
        "Audio Source", "source = audios/violin.mp3" );

    public static final @NotNull FormalParameter<HTextAlign> HORIZONTAL_ALIGNMENT =
        SharedMediaFormalNodeAttributes.HORIZONTAL_ALIGNMENT;

    public static final @NotNull FormalParameter<Boolean> BORDER =SharedMediaFormalNodeAttributes.BORDER;

    // public static final @NotNull FormalParameter<String> LINK = SharedMediaFormalNodeAttributes.LINK;

    // TODO add autoplay, controls, loop, muted (see https://www.w3schools.com/tags/tag_audio.asp)
    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute()
        .add ( SOURCE_ATTRIBUTE )
        .add ( HORIZONTAL_ALIGNMENT )
        .add ( BORDER );

    public static final @NotNull String HTML_TAG = "audio";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "audio" );

    public static final @NotNull FormalPMLNode<Void, AudioNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        AudioNode::new, PMLNodesHandler::audio, HTML_TAG, CSS_CLASS );
}
