package dev.pmlc.data.node.inline;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.LinkNodeSpec;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.inspection.message.TextError;
import dev.pp.text.token.TextToken;

public class LinkNode extends PMLInlineNode {


    private @NotNull String URL = "dummy";
    public @NotNull String getURL() { return URL; }


    public LinkNode() { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, LinkNode> getNodeSpec() { return LinkNodeSpec.NODE; }

    @Override
    public @Nullable Parameters<?> stringToTypedAttributes (
        @Nullable Parameters<String> stringAttributes,
        @Nullable TextToken startToken,
        @NotNull TextInspectionMessageHandler errorHandler ) {

        ParameterSpec<String> removedAttribute = LinkNodeSpec.TEXT_ATTRIBUTE__REMOVED;
        if ( stringAttributes != null && stringAttributes.containsSpecName ( removedAttribute ) ) {
            errorHandler.handleMessage ( new TextError (
                "Attribute '" + removedAttribute.getName() + "' has been removed in node '" +
                LinkNodeSpec.NODE.getName() + "' (since version 4.0.0). The text should be defined in a child node.",
                "INVALID_ATTRIBUTE",
                stringAttributes.nameToken ( removedAttribute.getName() ) ) );
        }

        return super.stringToTypedAttributes ( stringAttributes, startToken, errorHandler );
    }

    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        super.setAttributes ( parameters );

        assert parameters != null;
        URL = parameters.nonNullCastedValue ( LinkNodeSpec.URL_ATTRIBUTE );
    }

    @Override
    public void onNodeParsed() {
        if ( inlineChildNodes == null ) {
            addInlineChildNode ( new TextNode ( URL, null ) );
        }
    }
}
