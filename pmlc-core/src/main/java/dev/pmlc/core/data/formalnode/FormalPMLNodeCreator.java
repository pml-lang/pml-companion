package dev.pmlc.core.data.formalnode;

import dev.pdml.core.data.node.name.NodeName;
import dev.pdml.ext.extensions.types.PDMLTextBlockType;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalPMLNodeCreator {

    public static final @NotNull String HTML_CLASS_PREFIX = "pml-";

    public static @NotNull String prefixedHTMLClassName ( @NotNull String HTMLClassName ) {
        return HTML_CLASS_PREFIX + HTMLClassName;
    }

    public static <NODE extends PMLBlockNode> FormalPMLNode<Void, NODE> createForBlockNode (
        @NotNull NodeName name,
        @Nullable FormalParameters attributes,
        boolean HTMLAttributesAllowed,
        boolean blockChildNodesAllowed,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull FormalPMLNode.InitialNodeCreator<NODE> defaultNodeCreator,
        FormalPMLNode.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        boolean hasOnlyAttributes = hasOnlyAttributes ( attributes,
            HTMLAttributesAllowed,
            blockChildNodesAllowed,
            false,
            false );

        return new FormalPMLNode<> (
            name, null, false,
            attributes, hasOnlyAttributes, false, HTMLAttributesAllowed,
            blockChildNodesAllowed, false, false,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    public static <NODE extends PMLBlockNode> FormalPMLNode<Void, NODE> createForBlockNodeWithInlineChildNodes (
        @NotNull NodeName name,
        @Nullable FormalParameters attributes,
        boolean HTMLAttributesAllowed,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull FormalPMLNode.InitialNodeCreator<NODE> defaultNodeCreator,
        FormalPMLNode.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        return new FormalPMLNode<> (
            name, null, false,
            attributes, false, false, HTMLAttributesAllowed,
            false, true, false,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    public static <NODE extends PMLInlineNode> FormalPMLNode<Void, NODE> createForInlineNode (
        @NotNull NodeName name,
        @Nullable FormalParameters attributes,
        boolean HTMLAttributesAllowed,
        boolean inlineChildNodesAllowed,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull FormalPMLNode.InitialNodeCreator<NODE> defaultNodeCreator,
        FormalPMLNode.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        boolean hasOnlyAttributes = hasOnlyAttributes ( attributes,
        HTMLAttributesAllowed,
        false,
        inlineChildNodesAllowed,
        false );

        return new FormalPMLNode<> (
            name, null,true,
            attributes, hasOnlyAttributes, false, HTMLAttributesAllowed,
            false, inlineChildNodesAllowed,false,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    public static <NODE extends PMLBlockNode> FormalPMLNode<String, NODE> createForRawTextBlockNode (
        @NotNull NodeName name,
        @Nullable FormalParameters attributes,
        boolean HTMLAttributesAllowed,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull FormalPMLNode.InitialNodeCreator<NODE> defaultNodeCreator,
        FormalPMLNode.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        return new FormalPMLNode<> (
            name, new PDMLTextBlockType(), false,
            attributes, false, false, HTMLAttributesAllowed,
            false, false, true,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    public static <NODE extends PMLInlineNode> FormalPMLNode<String, NODE> createForRawTextInlineNode (
        @NotNull NodeName name,
        @Nullable FormalParameters attributes,
        boolean HTMLAttributesAllowed,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull FormalPMLNode.InitialNodeCreator<NODE> defaultNodeCreator,
        FormalPMLNode.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        return new FormalPMLNode<> (
            name, new PDMLTextBlockType(), true,
            attributes, false, false, HTMLAttributesAllowed,
            false, false, true,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    private static boolean hasOnlyAttributes (
        @Nullable FormalParameters attributes,
        boolean HTMLAttributesAllowed,
        boolean blockChildNodesAllowed,
        boolean inlineChildNodesAllowed,
        boolean isRawTextNode ) {

        return ( attributes != null || HTMLAttributesAllowed ) &&
            ! blockChildNodesAllowed &&
            ! inlineChildNodesAllowed &&
            ! isRawTextNode;
    }
}
