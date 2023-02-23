package dev.pmlc.data.node.block.media;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.media.YoutubeVideoNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.parameters.ParameterValueGetter;
import dev.pp.parameters.parameters.Parameters;

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

    public @NotNull PMLNodeSpec<Void, YoutubeVideoNode> getNodeSpec () { return YoutubeVideoNodeSpec.NODE; }

    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        assert parameters != null;

        super.setAttributes ( parameters );

        ParameterValueGetter pg = parameters.valueGetter();
        youtubeId = pg.nonNull ( YoutubeVideoNodeSpec.YOUTUBE_ID_PARAMETER );
        width = pg.nullable ( YoutubeVideoNodeSpec.WIDTH_ATTRIBUTE );
        height = pg.nullable ( YoutubeVideoNodeSpec.HEIGHT_ATTRIBUTE );
        horizontalAlignment = pg.nonNull ( YoutubeVideoNodeSpec.HORIZONTAL_ALIGNMENT );
        border = pg.nonNull ( YoutubeVideoNodeSpec.BORDER );
    }
}
