package dev.pmlc.data.nodespec;

import dev.pdml.data.node.NodeName;
import dev.pdml.exttypes.PdmlTextBlockType;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pmlc.data.node.inline.PMLInlineNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class PMLNodeSpecCreator {

    public static final @NotNull String HTML_CLASS_PREFIX = "pml-";

    public static @NotNull String prefixedHTMLClassName ( @NotNull String HTMLClassName ) {
        return HTML_CLASS_PREFIX + HTMLClassName;
    }

    public static <NODE extends PMLBlockNode> PMLNodeSpec<Void, NODE> createForBlockNode (
        @NotNull NodeName name,
        @Nullable ParameterSpecs<?> attributes,
        boolean HTMLAttributesAllowed,
        boolean blockChildNodesAllowed,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull PMLNodeSpec.InitialNodeCreator<NODE> defaultNodeCreator,
        PMLNodeSpec.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        boolean hasOnlyAttributes = hasOnlyAttributes ( attributes,
            HTMLAttributesAllowed,
            blockChildNodesAllowed,
            false,
            false );

        return new PMLNodeSpec<> (
            name, null, false,
            attributes, hasOnlyAttributes, false, HTMLAttributesAllowed,
            blockChildNodesAllowed, false, false, false,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    public static <NODE extends PMLBlockNode> PMLNodeSpec<Void, NODE> createForBlockNodeWithInlineChildNodes (
        @NotNull NodeName name,
        @Nullable ParameterSpecs<?> attributes,
        boolean HTMLAttributesAllowed,
        boolean keepWhitespaceInText,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull PMLNodeSpec.InitialNodeCreator<NODE> defaultNodeCreator,
        PMLNodeSpec.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        return new PMLNodeSpec<> (
            name, null, false,
            attributes, false, false, HTMLAttributesAllowed,
            false, true, false, keepWhitespaceInText,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    public static <NODE extends PMLInlineNode> PMLNodeSpec<Void, NODE> createForInlineNode (
        @NotNull NodeName name,
        @Nullable ParameterSpecs<?> attributes,
        boolean HTMLAttributesAllowed,
        boolean inlineChildNodesAllowed,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull PMLNodeSpec.InitialNodeCreator<NODE> defaultNodeCreator,
        PMLNodeSpec.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        boolean hasOnlyAttributes = hasOnlyAttributes ( attributes,
        HTMLAttributesAllowed,
        false,
        inlineChildNodesAllowed,
        false );

        return new PMLNodeSpec<> (
            name, null,true,
            attributes, hasOnlyAttributes, false, HTMLAttributesAllowed,
            false, inlineChildNodesAllowed,false, false,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    public static <NODE extends PMLBlockNode> PMLNodeSpec<String, NODE> createForRawTextBlockNode (
        @NotNull NodeName name,
        @Nullable ParameterSpecs<?> attributes,
        boolean HTMLAttributesAllowed,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull PMLNodeSpec.InitialNodeCreator<NODE> defaultNodeCreator,
        PMLNodeSpec.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        return new PMLNodeSpec<> (
            name, new PdmlTextBlockType ( true ), false,
            attributes, false, false, HTMLAttributesAllowed,
            false, false, true, false,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    public static <NODE extends PMLBlockNode> PMLNodeSpec<String, NODE> createForStyledTextBlockNode (
        @NotNull NodeName name,
        @Nullable ParameterSpecs<?> attributes,
        boolean HTMLAttributesAllowed,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull PMLNodeSpec.InitialNodeCreator<NODE> defaultNodeCreator,
        PMLNodeSpec.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        return new PMLNodeSpec<> (
            name, new PdmlTextBlockType ( false ), false,
            attributes, false, false, HTMLAttributesAllowed,
            false, true, false, true,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    public static <NODE extends PMLInlineNode> PMLNodeSpec<String, NODE> createForRawTextInlineNode (
        @NotNull NodeName name,
        @Nullable ParameterSpecs<?> attributes,
        boolean HTMLAttributesAllowed,
        @Nullable Supplier<SimpleDocumentation> documentation,
        @NotNull PMLNodeSpec.InitialNodeCreator<NODE> defaultNodeCreator,
        PMLNodeSpec.NodeHandler<NODE> nodeWriter,
        @NotNull String HTMLTag,
        @NotNull String CSSClass ) {

        return new PMLNodeSpec<> (
            // name, new PdmlTextBlockType(), true,
            name, null, true,
            attributes, false, false, HTMLAttributesAllowed,
            false, false, true, false,
            defaultNodeCreator, nodeWriter, HTMLTag, CSSClass, documentation );
    }

    private static boolean hasOnlyAttributes (
        @Nullable ParameterSpecs<?> attributes,
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
