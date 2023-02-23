package dev.pmlc.data.nodespec;

import dev.pdml.data.node.NodeName;
import dev.pdml.parser.nodespec.PdmlNodeSpec;
import dev.pdml.parser.nodespec.PdmlType;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pmlc.data.node.PMLNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class PMLNodeSpec<TYPE, NODE extends PMLNode> extends PdmlNodeSpec<TYPE> {


    public interface InitialNodeCreator<NODE> {

        NODE createNode();
    }

    public interface NodeHandler<NODE> {

        void handleNode ( @NotNull PMLNodesHandler handler, NODE node ) throws Exception;
    }


    private final boolean isInlineNode;
    public boolean isInlineNode() { return isInlineNode; }

    private final boolean HTMLAttributesAllowed;
    public boolean isHTMLAttributesAllowed() { return HTMLAttributesAllowed; }

    private final boolean blockChildNodesAllowed;
    public boolean isBlockChildNodesAllowed() { return blockChildNodesAllowed; }

    private final boolean inlineChildNodesAllowed;
    public boolean isInlineChildNodesAllowed() { return inlineChildNodesAllowed; }

    private final boolean isRawTextNode;
    public boolean isRawTextNode() { return isRawTextNode; }

    private final @NotNull InitialNodeCreator<NODE> initialNodeCreator;
    public @NotNull InitialNodeCreator<NODE> getInitialNodeCreator () { return initialNodeCreator; }

    private final @NotNull NodeHandler<NODE> nodeHandler;
    public @NotNull NodeHandler<NODE> getNodeHandler() { return nodeHandler; }

    private final @NotNull String HTMLTag;
    public @NotNull String getHTMLTag() { return HTMLTag; }

    private final @NotNull String CSSClass;
    public @NotNull String getCSSClass () { return CSSClass; }


    public PMLNodeSpec (
        @NotNull NodeName name,
        @Nullable PdmlType<TYPE> type,
        boolean isInlineNode,
        @Nullable ParameterSpecs<?> attributes,
        boolean hasOnlyAttributes,
        boolean hasAllAttributesOnTagLine,
        boolean HTMLAttributesAllowed,
        boolean blockChildNodesAllowed,
        boolean inlineChildNodesAllowed,
        boolean isRawTextNode,
        @NotNull InitialNodeCreator<NODE> initialNodeCreator,
        @NotNull NodeHandler<NODE> nodeHandler,
        @NotNull String HTMLTag,
        @NotNull String CSSClass,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        super ( name, type,
            attributes, hasOnlyAttributes, hasAllAttributesOnTagLine,
            documentation );

        this.isInlineNode = isInlineNode;
        this.HTMLAttributesAllowed = HTMLAttributesAllowed;
        this.blockChildNodesAllowed = blockChildNodesAllowed;
        this.inlineChildNodesAllowed = inlineChildNodesAllowed;
        this.isRawTextNode = isRawTextNode;
        this.initialNodeCreator = initialNodeCreator;
        this.nodeHandler = nodeHandler;
        this.HTMLTag = HTMLTag;
        this.CSSClass = CSSClass;
    }

    public NODE createInitialNode () {
        return initialNodeCreator.createNode();
    }

    public void handleNode ( @NotNull PMLNodesHandler nodesHandler, NODE node ) throws Exception {
        nodeHandler.handleNode ( nodesHandler, node );
    }
}
