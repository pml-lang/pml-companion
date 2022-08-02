package dev.pmlc.core.data.node.validation;

import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.error.TextError;
import dev.pp.text.error.TextErrorException;
import dev.pp.text.error.handler.TextErrorHandler;

public class NodeValidator {

    public static void validateTree (
        @NotNull DocumentNode documentNode,
        @NotNull TextErrorHandler errorHandler ) throws TextErrorException {

        TextError initialLastError = errorHandler.lastError();

        NodeValidationContext context = new NodeValidationContext ( errorHandler );
        documentNode.forEachNodeInTree ( true, node -> node.validate ( context ) );

        errorHandler.throwIfNewErrors ( initialLastError );
    }
}
