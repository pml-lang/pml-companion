package dev.pmlc.data.node.validator;

import dev.pmlc.data.node.block.DocumentNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.message.TextError;
import dev.pp.text.inspection.TextErrorException;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;

public class NodeValidator {

    public static void validateTree (
        @NotNull DocumentNode documentNode,
        @NotNull TextInspectionMessageHandler errorHandler ) throws TextErrorException {

        TextError initialLastError = errorHandler.lastError();

        NodeValidatorContext context = new NodeValidatorContext ( errorHandler );
        documentNode.forEachNodeInTree ( true, node -> node.validate ( context ) );

        errorHandler.throwIfNewErrors ( initialLastError );
    }
}
