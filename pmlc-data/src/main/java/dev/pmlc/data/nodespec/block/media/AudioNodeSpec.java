package dev.pmlc.data.nodespec.block.media;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.media.AudioNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class AudioNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "audio" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Audio (Sound)",
        "An audio stream (sound), such as an .mp3 or .wav file.",
        """
           [header Bird talk]
           [audio source=media/bird_talk.mp3]
            """ );

    public static final @NotNull ParameterSpec<String> SOURCE_ATTRIBUTE = SharedMediaNodeSpecAttributes.source (
        "Audio Source", "source = audios/violin.mp3" );

    public static final @NotNull ParameterSpec<HTextAlign> HORIZONTAL_ALIGNMENT =
        SharedMediaNodeSpecAttributes.HORIZONTAL_ALIGNMENT;

    public static final @NotNull ParameterSpec<Boolean> BORDER = SharedMediaNodeSpecAttributes.BORDER;

    // TODO add autoplay, controls, loop, muted (see https://www.w3schools.com/tags/tag_audio.asp)
    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.mutableListWithIdAttribute()
        .add ( SOURCE_ATTRIBUTE )
        .add ( HORIZONTAL_ALIGNMENT )
        .add ( BORDER )
        .copyToImmutable();

    public static final @NotNull String HTML_TAG = "audio";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "audio" );

    public static final @NotNull PMLNodeSpec<Void, AudioNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        AudioNode::new, PMLNodesHandler::audio, HTML_TAG, CSS_CLASS );
}
