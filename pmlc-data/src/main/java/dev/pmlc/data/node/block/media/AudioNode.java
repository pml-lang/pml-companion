package dev.pmlc.data.node.block.media;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.media.AudioNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.parameters.Parameters;

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


    public @NotNull PMLNodeSpec<Void, AudioNode> getNodeSpec () { return AudioNodeSpec.NODE; }

    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        assert parameters != null;

        super.setAttributes ( parameters );

        source = parameters.nonNullCastedValue ( AudioNodeSpec.SOURCE_ATTRIBUTE );
        horizontalAlignment = parameters.nonNullCastedValue ( AudioNodeSpec.HORIZONTAL_ALIGNMENT );
        border = parameters.nonNullCastedValue ( AudioNodeSpec.BORDER );
    }
}
