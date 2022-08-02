package dev.pmlc.core.data.node.inline;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.inline.FormalLinkNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.list.Parameters;

public class LinkNode extends PMLInlineNode {


    private @NotNull String URL = "dummy";
    public @NotNull String getURL() { return URL; }

    private @Nullable String text = null;
    public @Nullable String getText() { return text; }


    public LinkNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull FormalPMLNode<Void, LinkNode> getFormalNode() { return FormalLinkNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        super.createAttributes ( parameters );

        assert parameters != null;
        URL = parameters.getNonNull ( FormalLinkNode.URL_ATTRIBUTE );
        text = parameters.getNullable ( FormalLinkNode.TEXT_ATTRIBUTE );
    }

    public @NotNull String getTextOrElseURL() { return text != null ? text : URL; }
}
