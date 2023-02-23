package dev.pmlc.data.node.block.quote;

import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.quote.QuoteNodeSpec;
import dev.pmlc.data.nodespec.block.quote.QuoteSourceNodeSpec;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.DebugUtils;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.inspection.message.TextError;
import dev.pp.text.token.TextToken;

public class QuoteNode extends PMLBlockNode {


    private @Nullable QuoteSourceNode sourceNode = null;
    public @Nullable QuoteSourceNode getSourceNode() { return sourceNode; }


    public QuoteNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, QuoteNode> getNodeSpec() { return QuoteNodeSpec.NODE; }

    public void onNodeParsed() throws Exception {

        if ( blockChildNodes != null ) {
            // move the quote source node from the child nodes to field 'sourceNode'
            for ( int i = 0; i < blockChildNodes.size(); i++ ) {
                PMLBlockNode blockNode = blockChildNodes.get ( i );
                if ( blockNode.getName().equals ( QuoteSourceNodeSpec.NAME.qualifiedName() ) ) {
                    sourceNode = (QuoteSourceNode) blockNode;
                    blockChildNodes.remove ( i );
                    break;
                }
            }
        }
    }

    // Remove when no more needed
    @Override
    public @Nullable Parameters<?> stringToTypedAttributes (
        @Nullable Parameters<String> stringAttributes,
        @Nullable TextToken startToken,
        @NotNull TextInspectionMessageHandler errorHandler ) {

        ParameterSpec<String> removedAttribute = QuoteNodeSpec.SOURCE_ATTRIBUTE__REMOVED;
        if ( stringAttributes != null && stringAttributes.containsSpecName ( removedAttribute ) ) {
            errorHandler.handleMessage ( new TextError (
                "Attribute '" + removedAttribute.getName() + "' has been removed in node '" +
                    QuoteNodeSpec.NODE.getName() + "' (since version 4.0.0). The source must be defined with node '" + QuoteSourceNodeSpec.NAME + "'.",
                "INVALID_ATTRIBUTE",
                stringAttributes.nameToken ( removedAttribute.getName() ) ) );
        }

        return super.stringToTypedAttributes ( stringAttributes, startToken, errorHandler );
    }
}
