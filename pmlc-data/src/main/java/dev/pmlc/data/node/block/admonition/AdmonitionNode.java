package dev.pmlc.data.node.block.admonition;

import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.admonition.AdmonitionLabelNodeSpec;
import dev.pmlc.data.nodespec.block.admonition.AdmonitionNodeSpec;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.DebugUtils;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.inspection.message.TextError;
import dev.pp.text.token.TextToken;

public class AdmonitionNode extends PMLBlockNode {


    private @Nullable AdmonitionLabelNode labelNode = null;
    public @Nullable AdmonitionLabelNode getLabelNode() { return labelNode; }


    public AdmonitionNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, AdmonitionNode> getNodeSpec () { return AdmonitionNodeSpec.NODE; }

    public void onNodeParsed() throws Exception {

        if ( blockChildNodes != null ) {
            // move the label node from the child nodes to field 'labelNode'
            for ( int i = 0; i < blockChildNodes.size(); i++ ) {
                PMLBlockNode blockNode = blockChildNodes.get ( i );
                if ( blockNode.getName().equals ( AdmonitionLabelNodeSpec.NAME.qualifiedName() ) ) {
                    labelNode = (AdmonitionLabelNode) blockNode;
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

        ParameterSpec<String> removedAttribute = AdmonitionNodeSpec.LABEL_ATTRIBUTE__REMOVED;
        if ( stringAttributes != null && stringAttributes.containsSpecName ( removedAttribute ) ) {
            errorHandler.handleMessage ( new TextError (
                "Attribute '" + removedAttribute.getName() + "' has been removed in node '" +
                    AdmonitionNodeSpec.NAME + "' (since version 4.0.0). The label must be defined with node '" +
                    AdmonitionLabelNodeSpec.NAME + "'.",
                "INVALID_ATTRIBUTE",
                stringAttributes.nameToken ( removedAttribute.getName() ) ) );
        }

        return super.stringToTypedAttributes ( stringAttributes, startToken, errorHandler );
    }
}
