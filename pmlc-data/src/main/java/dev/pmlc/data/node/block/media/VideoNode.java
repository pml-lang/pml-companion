package dev.pmlc.data.node.block.media;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.media.VideoNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.parameters.ParameterValueGetter;
import dev.pp.parameters.parameters.Parameters;

public class VideoNode extends PMLBlockNode {

    private @NotNull String source = "";
    public @NotNull String getSource() {
        return source;
    }

    private @Nullable Integer width = null;
    public @Nullable Integer getWidth() {
        return width;
    }

    private @Nullable Integer height = null;
    public @Nullable Integer getHeight() {
        return height;
    }

    private @NotNull HTextAlign horizontalAlignment = HTextAlign.LEFT;
    public @NotNull HTextAlign getHorizontalAlignment() {
        return horizontalAlignment;
    }

    private @Nullable Boolean border = false;
    public @Nullable Boolean getBorder() {
        return border;
    }


    public VideoNode () { super(); }


    public @NotNull PMLNodeSpec<Void, VideoNode> getNodeSpec () { return VideoNodeSpec.NODE; }

    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        assert parameters != null;

        super.setAttributes ( parameters );

        ParameterValueGetter pg = parameters.valueGetter();
        source = pg.nonNull ( VideoNodeSpec.SOURCE_ATTRIBUTE );
        width = pg.nullable ( VideoNodeSpec.WIDTH_ATTRIBUTE );
        height = pg.nullable ( VideoNodeSpec.HEIGHT_ATTRIBUTE );
        horizontalAlignment = pg.nonNull ( VideoNodeSpec.HORIZONTAL_ALIGNMENT );
        border = pg.nonNull ( VideoNodeSpec.BORDER );
    }
}
