package dev.pmlc.core.data.node.validation;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.node.PMLNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.error.handler.TextErrorHandler;
import dev.pp.text.token.TextToken;

import java.util.List;

public class NodeValidationContext {

    private final @NotNull TextErrorHandler errorHandler;

    public NodeValidationContext ( @NotNull TextErrorHandler errorHandler ) {
        this.errorHandler = errorHandler;
    }

    public void checkParentType ( @NotNull PMLNode node, @NotNull FormalPMLNode<?,?> allowedParent ) {

        PMLNode parentNode = node.getParentNode();
        assert parentNode != null;

        if ( parentNode.getFormalNode() != allowedParent ) {
            handleError (
                "INVALID_PARENT_NODE",
                "Node '" + node.getName() + "' can only be a child of node '" + allowedParent.getName() + "'. It cannot be a child of node '" + parentNode.getName() + "'.",
                node.getStartToken() );
        }
    }

    public void checkHasChildren ( @NotNull PMLNode node ) {

        @Nullable List<? extends PMLNode> childNodes = node.getChildNodes();
        if ( childNodes == null ) {
            handleWarning (
                "EMPTY_NODE",
                "Node '" + node.getName() + "' has no content.",
                node.getStartToken() );
        }
    }

    public void checkChildrenType ( @NotNull PMLNode node, @NotNull FormalPMLNode<?,?> allowedChild ) {

        @Nullable List<? extends PMLNode> childNodes = node.getChildNodes();
        if ( childNodes == null ) return;
        for ( PMLNode childNode : childNodes ) {
            if ( childNode.getFormalNode() != allowedChild ) {
                handleError (
                    "INVALID_CHILD_NODE",
                    "Node '" + node.getName() + "' can only contain '" + allowedChild.getName() + "' nodes. It cannot contain '" + childNode.getName() + "' nodes.",
                    childNode.getStartToken() );
            }
        }
    }

    public void handleError ( @Nullable String id, @NotNull String message, @Nullable TextToken token ) {
        errorHandler.handleNonAbortingError ( id, message, token );
    }

    public void handleWarning ( @Nullable String id, @NotNull String message, @Nullable TextToken token ) {
        errorHandler.handleNonAbortingWarning ( id, message, token );
    }
}
