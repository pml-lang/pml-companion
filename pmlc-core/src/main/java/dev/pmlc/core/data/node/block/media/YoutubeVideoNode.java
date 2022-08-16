package dev.pmlc.core.data.node.block.media;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.media.FormalYoutubeVideoNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.parameter.Parameters;

public class YoutubeVideoNode extends PMLBlockNode {

    private @NotNull String youtubeId = "";
    public @NotNull String getYoutubeId() {
        return youtubeId;
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


    public YoutubeVideoNode () { super(); }

    public @NotNull FormalPMLNode<Void, YoutubeVideoNode> getFormalNode() { return FormalYoutubeVideoNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        assert parameters != null;

        super.createAttributes ( parameters );

        youtubeId = parameters.getNonNull ( FormalYoutubeVideoNode.YOUTUBE_ID_PARAMETER );
        width = parameters.getNullable ( FormalYoutubeVideoNode.WIDTH_ATTRIBUTE );
        height = parameters.getNullable ( FormalYoutubeVideoNode.HEIGHT_ATTRIBUTE );
        horizontalAlignment = parameters.getNonNull ( FormalYoutubeVideoNode.HORIZONTAL_ALIGNMENT );
        border = parameters.getNonNull ( FormalYoutubeVideoNode.BORDER );
    }
}
