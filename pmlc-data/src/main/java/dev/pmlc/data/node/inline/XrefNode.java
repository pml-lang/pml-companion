package dev.pmlc.data.node.inline;

import dev.pmlc.data.node.validator.NodeValidatorContext;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.inline.XrefNodeSpec;
import dev.pmlc.data.node.block.chapter.ChapterNode;
import dev.pmlc.data.node.block.DocumentNode;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.inspection.message.TextError;
import dev.pp.text.token.TextToken;

public class XrefNode extends PMLInlineNode {

    private @NotNull String referencedNodeId = "none";
    public @NotNull String getReferencedNodeId() { return referencedNodeId; }
    // public void setReferencedNodeId ( @NotNull String referencedNodeId ) { this.referencedNodeId = referencedNodeId; }

    // value is set in PMLParserEventHandler
    private @Nullable PMLBlockNode referencedNode = null;
    @Nullable public PMLBlockNode getReferencedNode() { return referencedNode; }
    public void setReferencedNode ( @NotNull PMLBlockNode referencedNode ) {
        this.referencedNode = referencedNode;
        setAutoContent ();
    }

    private void setAutoContent() {

        if ( inlineChildNodes != null ) return;

        assert referencedNode != null;

        @Nullable String title = null;
        if ( referencedNode instanceof ChapterNode chapterNode ) {
            title = chapterNode.getTitleText();
        } else if ( referencedNode instanceof DocumentNode documentNode ) {
            title = documentNode.getTitleText();
        }
        if ( title != null ) {
            addInlineChildNode ( new TextNode ( title, null ) );
        }
    }


    public XrefNode() { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, XrefNode> getNodeSpec() { return XrefNodeSpec.NODE; }

    @Override
    public @Nullable Parameters<?> stringToTypedAttributes (
        @Nullable Parameters<String> stringAttributes,
        @Nullable TextToken startToken,
        @NotNull TextInspectionMessageHandler errorHandler ) {

        ParameterSpec<String> removedAttribute = XrefNodeSpec.REFERENCED_NODE_ID_ATTRIBUTE__OLD_NAME;
        if ( stringAttributes != null && stringAttributes.containsSpecName ( removedAttribute ) ) {
            errorHandler.handleMessage ( new TextError (
                "Attribute '" + removedAttribute.getName() + "' in node '" +
                    XrefNodeSpec.NODE.getName() + "' has been renamed to '" + XrefNodeSpec.REFERENCED_NODE_ID_ATTRIBUTE.getName() + "' (since version 4.0.0). Please use the new name.",
                "INVALID_ATTRIBUTE",
                stringAttributes.nameToken ( removedAttribute.getName() ) ) );
        }

        return super.stringToTypedAttributes ( stringAttributes, startToken, errorHandler );
    }

    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        super.setAttributes ( parameters );

        assert parameters != null;
        referencedNodeId = parameters.nonNullCastedValue ( XrefNodeSpec.REFERENCED_NODE_ID_ATTRIBUTE );
    }

    public void validate ( @NotNull NodeValidatorContext context ) {
        context.checkHasChildren ( this );
    }
}
