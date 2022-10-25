package dev.pmlc.ext.utilities.pmltohtml.writer;

import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.block.media.FormalAudioNode;
import dev.pmlc.core.data.formalnode.block.media.FormalImageNode;
import dev.pmlc.core.data.formalnode.block.media.FormalVideoNode;
import dev.pmlc.core.data.formalnode.block.media.FormalYoutubeVideoNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pmlc.core.data.node.block.media.AudioNode;
import dev.pmlc.core.data.node.block.media.ImageNode;
import dev.pmlc.core.data.node.block.media.VideoNode;
import dev.pmlc.core.data.node.block.media.YoutubeVideoNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MediaHTMLWriter {

    static void writeImage ( @NotNull ImageNode node, @NotNull HTMLNodesWriterHelper helper ) throws Exception {

        /*
        <figure id = "123" style="text-align: center">
            <a href="https://unsplash.com/photos/kH3Sr9K8EBA">
                <img src="images/strawberries.jpg" width="200" height="100" class="pml-image pml-bordered" alt="Delicious strawberries" />
            </a>
            <!-- <figurecaption>Delicious strawberries</figurecaption> -->
        </figure>
        */

        writeStart ( node.getHorizontalAlignment(), node.getNodeId(), helper );
        writeLinkStart ( node.getLink(), helper );
        writeElementStart ( node, FormalImageNode.HTML_TAG, FormalImageNode.CSS_CLASS, node.getSource(),
            node.getWidth(), node.getHeight(), node.getBorder(), null, helper );
        helper.writeNewLine();

        writeLinkEnd ( node.getLink(), helper );
        writeEnd ( helper );
    }

    static void writeAudio ( @NotNull AudioNode node, @NotNull HTMLNodesWriterHelper helper ) throws Exception {

        /*
        <figure style="text-align: center">
            <audio class="pml-audio pml-bordered" controls="controls">
                <source src="audios/bird_talk.mp3">
                Your browser does not support the audio element.
            </audio>
        </figure>
        */

        writeStart ( node.getHorizontalAlignment(), node.getNodeId(), helper );
        writeElementStart ( node, FormalAudioNode.HTML_TAG, FormalAudioNode.CSS_CLASS, null,
            null, null, node.getBorder(), Map.of ( "controls", "controls" ), helper );
        helper.writeNewLine();

        writeSource ( node.getSource(), "audio", helper );

        helper.writeIndent();
        helper.writeHTMLEndTag ( FormalAudioNode.HTML_TAG );
        helper.writeNewLine();

        writeEnd ( helper );
    }

    static void writeVideo ( @NotNull VideoNode node, @NotNull HTMLNodesWriterHelper helper ) throws Exception {

        /*
        <figure style="text-align: center">
            <video width="200" height="100" class="pml-video pml-bordered" controls="controls">
                <source src="videos/red_flower.mp4">
                Your browser does not support the video element.
            </video>
        </figure>
        */

        writeStart ( node.getHorizontalAlignment(), node.getNodeId(), helper );
        writeElementStart ( node, FormalVideoNode.HTML_TAG, FormalVideoNode.CSS_CLASS, null,
            node.getWidth(), node.getHeight(), node.getBorder(), Map.of ( "controls", "controls" ), helper );
        helper.writeNewLine();

        writeSource ( node.getSource(), "video", helper );

        helper.writeIndent();
        helper.writeHTMLEndTag ( FormalVideoNode.HTML_TAG );
        helper.writeNewLine();

        writeEnd ( helper );
    }

    static void writeYoutubeVideo ( @NotNull YoutubeVideoNode node, @NotNull HTMLNodesWriterHelper helper ) throws Exception {

        /*
        <figure style="text-align: center">
            <iframe width="200" height="100" src="https://www.youtube.com/embed/qrcZqnICYbs" class="pml-youtube-video pml-bordered"
                allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture; fullscreen"></iframe>
        </figure>
        */

        writeStart ( node.getHorizontalAlignment(), node.getNodeId(), helper );
        writeElementStart ( node, FormalYoutubeVideoNode.HTML_TAG, FormalYoutubeVideoNode.CSS_CLASS,
        "https://www.youtube.com/embed/" + node.getYoutubeId(),
            node.getWidth(), node.getHeight(), node.getBorder(),
            Map.of ( "allow", "accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture; fullscreen" ),
            helper );
        helper.write ( "</iframe>" );
        helper.writeNewLine();

        writeEnd ( helper );
    }


    private static void writeStart ( @NotNull HTextAlign alignment, @Nullable String id, @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        helper.writeIndent();

        String alignValue = switch ( alignment) {
            case LEFT -> "left";
            case CENTER -> "center";
            case RIGHT -> "right";
        };
        helper.writeHTMLStartTag ( "figure", id, null, null,
            Map.of ( "style", "text-align: " + alignValue ) );

        helper.writeNewLine();

        helper.increaseIndent();
    }

    private static void writeEnd ( @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        helper.decreaseIndent();
        helper.writeBlockEndLine ( "figure" );
    }

    private static void writeLinkStart ( @Nullable String link, @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        if ( link == null ) return;

        helper.writeIndent();
        helper.writeHTMLStartTag ( "a", null, null, null,
            Map.of ( "href", link ) );
        helper.writeNewLine();
        helper.increaseIndent();
    }

    private static void writeLinkEnd ( @Nullable String link, @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        if ( link == null ) return;

        helper.decreaseIndent();
        helper.writeBlockEndLine ( "a" );
    }

    private static void writeElementStart (
        @NotNull PMLBlockNode node,
        @NotNull String tag,
        @NotNull String CSSClass,
        @Nullable String source,
        @Nullable Integer width,

        @Nullable Integer height,
        @Nullable Boolean border,
        @Nullable Map<String, String> extraAttributes,
        @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        // <img src="images/strawberries.jpg" class="pml-image pml-bordered" width="200" height="100" alt="Delicious strawberries" />

        helper.writeIndent();

        String clazz = CSSClass;
        if ( border != null && border ) clazz = clazz + " " + FormalPMLNodeCreator.prefixedHTMLClassName ( "bordered" );

        Map<String, String> nodeHTMLAttributes = node.getHTMLAttributes();
        Map<String, String> HTMLAttributes = nodeHTMLAttributes != null ? new HashMap<> ( nodeHTMLAttributes ) : new HashMap<>();
        if ( source != null ) {
            HTMLAttributes.put ( "src", source );
        }
        if ( width != null ) {
            HTMLAttributes.put ( "width", width.toString() );
        }
        if ( height != null ) {
            HTMLAttributes.put ( "height", height.toString() );
        }

        helper.writeHTMLStartTag ( tag, null, clazz, HTMLAttributes, extraAttributes );
    }

    private static void writeSource (
        @NotNull String source, @NotNull String name, @NotNull HTMLNodesWriterHelper helper ) throws IOException {

        /*
        <source src="videos/red_flower.mp4">
        Your browser does not support the video element.
        */
        helper.increaseIndent();

        helper.writeIndent();
        helper.writeHTMLStartTag ( "source", null, null, null, Map.of ( "src", source ) );
        helper.writeNewLine();

        helper.writeIndent();
        helper.write ( "Your browser does not support the " + name + " element." );
        helper.writeNewLine();

        helper.decreaseIndent();
    }
}
