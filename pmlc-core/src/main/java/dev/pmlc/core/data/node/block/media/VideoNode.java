package dev.pmlc.core.data.node.block.media;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.media.FormalVideoNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.parameter.Parameters;

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


    public @NotNull FormalPMLNode<Void, VideoNode> getFormalNode() { return FormalVideoNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        assert parameters != null;

        super.createAttributes ( parameters );

        source = parameters.getNonNull ( FormalVideoNode.SOURCE_ATTRIBUTE );
        width = parameters.getNullable ( FormalVideoNode.WIDTH_ATTRIBUTE );
        height = parameters.getNullable ( FormalVideoNode.HEIGHT_ATTRIBUTE );
        horizontalAlignment = parameters.getNonNull ( FormalVideoNode.HORIZONTAL_ALIGNMENT );
        border = parameters.getNonNull ( FormalVideoNode.BORDER );
    }
}
