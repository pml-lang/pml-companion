package dev.pmlc.core.data.node.block.media;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.media.FormalImageNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.parameters.parameter.Parameters;

public class ImageNode extends PMLBlockNode {

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
    public @NotNull HTextAlign getHorizontalAlignment () {
        return horizontalAlignment;
    }

    private @Nullable Boolean border = false;
    public @Nullable Boolean getBorder() {
        return border;
    }

    private @Nullable String link = null;
    public @Nullable String getLink() {
        return link;
    }


    public ImageNode () { super(); }


    public @NotNull FormalPMLNode<Void, ImageNode> getFormalNode() { return FormalImageNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        assert parameters != null;

        super.createAttributes ( parameters );

        source = parameters.getNonNull ( FormalImageNode.SOURCE_ATTRIBUTE );
        width = parameters.getNullable ( FormalImageNode.WIDTH_ATTRIBUTE );
        height = parameters.getNullable ( FormalImageNode.HEIGHT_ATTRIBUTE );
        horizontalAlignment = parameters.getNonNull ( FormalImageNode.HORIZONTAL_ALIGNMENT );
        border = parameters.getNonNull ( FormalImageNode.BORDER );
        link = parameters.getNullable ( FormalImageNode.LINK );
    }
}
