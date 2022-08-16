package dev.pmlc.core.data.node.block.media;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.media.FormalAudioNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.parameter.Parameters;

public class AudioNode extends PMLBlockNode {

    private @NotNull String source = "";
    public @NotNull String getSource() {
        return source;
    }

    private @NotNull HTextAlign horizontalAlignment = HTextAlign.LEFT;
    public @NotNull HTextAlign getHorizontalAlignment() {
        return horizontalAlignment;
    }

    private @Nullable Boolean border = false;
    public @Nullable Boolean getBorder() {
        return border;
    }


    public AudioNode () { super(); }


    public @NotNull FormalPMLNode<Void, AudioNode> getFormalNode() { return FormalAudioNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        assert parameters != null;

        super.createAttributes ( parameters );

        source = parameters.getNonNull ( FormalAudioNode.SOURCE_ATTRIBUTE );
        horizontalAlignment = parameters.getNonNull ( FormalAudioNode.HORIZONTAL_ALIGNMENT );
        border = parameters.getNonNull ( FormalAudioNode.BORDER );
    }
}
