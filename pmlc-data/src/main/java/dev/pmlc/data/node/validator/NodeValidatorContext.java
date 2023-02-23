package dev.pmlc.data.node.validator;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.node.PMLNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.inspection.message.TextError;
import dev.pp.text.inspection.message.TextWarning;
import dev.pp.text.token.TextToken;

import java.util.List;

public class NodeValidatorContext {

    private final @NotNull TextInspectionMessageHandler errorHandler;

    public NodeValidatorContext ( @NotNull TextInspectionMessageHandler errorHandler ) {
        this.errorHandler = errorHandler;
    }

    public void checkParentType ( @NotNull PMLNode node, @NotNull PMLNodeSpec<?,?> allowedParent ) {

        PMLNode parentNode = node.getParentNode();
        assert parentNode != null;

        if ( parentNode.getNodeSpec () != allowedParent ) {
            handleError (
                "Node '" + node.getName() + "' can only be a child of node '" + allowedParent.getName() + "'. It cannot be a child of node '" + parentNode.getName() + "'.",
                "INVALID_PARENT_NODE",
                node.getStartToken() );
        }
    }

    public void checkHasChildren ( @NotNull PMLNode node ) {

        @Nullable List<? extends PMLNode> childNodes = node.getChildNodes();
        if ( childNodes == null ) {
            handleWarning (
                "Node '" + node.getName() + "' has no content.",
                "EMPTY_NODE",
                node.getStartToken() );
        }
    }

    public void checkChildrenType ( @NotNull PMLNode node, @NotNull PMLNodeSpec<?,?> allowedChild ) {

        @Nullable List<? extends PMLNode> childNodes = node.getChildNodes();
        if ( childNodes == null ) return;
        for ( PMLNode childNode : childNodes ) {
            if ( childNode.getNodeSpec () != allowedChild ) {
                handleError (
                    "Node '" + node.getName() + "' can only contain '" + allowedChild.getName() + "' nodes. It cannot contain '" + childNode.getName() + "' nodes.",
                    "INVALID_CHILD_NODE",
                    childNode.getStartToken() );
            }
        }
    }

    public void handleError ( @NotNull String message, @Nullable String id, @Nullable TextToken token ) {
        errorHandler.handleMessage ( new TextError ( message, id, token ) );
    }

    public void handleWarning ( @NotNull String message, @Nullable String id, @Nullable TextToken token ) {
        errorHandler.handleMessage ( new TextWarning ( message, id, token ) );
    }
}
