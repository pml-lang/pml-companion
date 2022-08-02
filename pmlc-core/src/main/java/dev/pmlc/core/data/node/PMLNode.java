package dev.pmlc.core.data.node;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.node.inline.PMLInlineNode;
import dev.pmlc.core.data.node.inline.TextNode;
import dev.pmlc.core.data.node.validation.NodeValidationContext;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.list.Parameters;
import dev.pp.text.token.TextToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class PMLNode {


    protected @Nullable PMLNode parentNode = null;
    public @Nullable PMLNode getParentNode() { return parentNode; }
    public void setParentNode ( @NotNull PMLNode parentNode ) { this.parentNode = parentNode; }

    protected @Nullable TextToken startToken = null;
    public @Nullable TextToken getStartToken() { return startToken; }
    public void setStartToken ( @Nullable TextToken startToken ) { this.startToken = startToken; }

    protected @Nullable Map<String, String> HTMLAttributes;
    public @Nullable Map<String, String> getHTMLAttributes() { return HTMLAttributes; }
    public void setHTMLAttributes ( @Nullable Map<String, String> HTMLAttributes ) { this.HTMLAttributes = HTMLAttributes; }
    public boolean hasHTMLAttributes() { return HTMLAttributes != null && ! HTMLAttributes.isEmpty(); }

    protected @Nullable List<PMLInlineNode> inlineChildNodes = null;
    public @Nullable List<PMLInlineNode> getInlineChildNodes () { return inlineChildNodes; }
    public void setInlineChildNodes ( @Nullable List<PMLInlineNode> inlineChildNodes ) { this.inlineChildNodes = inlineChildNodes; }
    public boolean hasInlineChildNodes() { return inlineChildNodes != null && ! inlineChildNodes.isEmpty(); }

    protected @Nullable String rawText = null;
    public @Nullable String getRawText() { return rawText; }
    public void setRawText ( @Nullable String rawText ) { this.rawText = rawText; }


    protected PMLNode() { this ( null ); }

    protected PMLNode ( @Nullable Map<String, String> HTMLAttributes ) {
        this.HTMLAttributes = HTMLAttributes;
    }


    public abstract @NotNull <N extends PMLNode> FormalPMLNode<?, N> getFormalNode();

    public @NotNull String getName() { return getFormalNode().getName().toString(); }

    /**
     * Callback method called after attributes have been parsed
     * @param parameters the node's attributes
     * @throws Exception
     */
    public abstract void createAttributes ( @Nullable Parameters parameters ) throws Exception;

    /**
     * Callback method called after all child nodes have been parsed, but before subsequent siblings will be parsed.
     * @throws Exception
     */
    public void createData() throws Exception {}

    public void validate ( @NotNull NodeValidationContext context ) {}

    public void addInlineChildNode ( @NotNull PMLInlineNode node ) {

        if ( inlineChildNodes == null ) inlineChildNodes = new ArrayList<> ();
        inlineChildNodes.add ( node );
    }


    // Direct child nodes

    public abstract @Nullable List<? extends PMLNode> getChildNodes();

    public void forEachChildNode ( @NotNull Consumer<PMLNode> consumer ) {

        @Nullable List<? extends PMLNode> childNodes = getChildNodes();
        if ( childNodes == null ) return;

        for ( PMLNode childNode : childNodes ) {
            consumer.accept ( childNode );
        }
    }

    public <N extends PMLNode> void forEachChildNodeWithName (
        @NotNull String nodeName,
        @NotNull Consumer<N> consumer ) {

        @Nullable List<? extends PMLNode> childNodes = getChildNodes();
        if ( childNodes == null ) return;

        for ( PMLNode childNode : childNodes ) {
            if ( childNode.getName().equals ( nodeName ) ) {
                @SuppressWarnings ("unchecked")
                N childNode_ = (N) childNode;
                consumer.accept ( childNode_ );
            }
        }
    }

    public @Nullable PMLNode findFirstChildNode ( @NotNull Predicate<PMLNode> predicate ) {

        @Nullable List<? extends PMLNode> childNodes = getChildNodes();
        if ( childNodes == null ) return null;

        for ( PMLNode childNode : childNodes ) {
            if ( predicate.test ( childNode ) ) return childNode;
        }

        return null;
    }

    public @Nullable <N extends PMLNode> N findFirstChildNodeByName ( @NotNull String nodeName ) {

        @SuppressWarnings ("unchecked")
        N result = (N) findFirstChildNode ( childNode -> childNode.getName().equals ( nodeName ) );

        return result;
    }

    public @Nullable <N extends PMLNode> List<N> findChildNodesByName ( @NotNull String nodeName ) {

        List<N> childNodes = new ArrayList<>();
        forEachChildNodeWithName ( nodeName, (Consumer<N>) childNodes::add );
        return childNodes.isEmpty() ? null : childNodes;
    }


    // Child nodes in tree

    public void forEachNodeInTree ( boolean includeThis, @NotNull Consumer<PMLNode> consumer ) {

        if ( includeThis ) consumer.accept ( this );

        forEachNodeInTree ( getChildNodes(), consumer );
    }

    protected void forEachNodeInTree (
        @Nullable List<? extends PMLNode> childNodes,
        @NotNull Consumer<PMLNode> consumer ) {

        if ( childNodes == null ) return;

        for ( PMLNode node : childNodes ) {
            consumer.accept ( node );
            forEachNodeInTree ( node.getChildNodes(), consumer );
        }
    }

    public @Nullable PMLNode findFirstChildNodeInTree ( boolean includeThis, @NotNull Predicate<PMLNode> predicate ) {

        if ( includeThis ) {
            if ( predicate.test ( this ) ) return this;
        }

        @Nullable List<? extends PMLNode> childNodes = getChildNodes();
        if ( childNodes == null ) return null;

        for ( PMLNode childNode : childNodes ) {
            PMLNode found = childNode.findFirstChildNodeInTree ( true, predicate );
            if ( found != null ) return found;
        }

        return null;
    }

    public @Nullable String getTextInTree() {

        StringBuilder sb = new StringBuilder();

        forEachNodeInTree ( true, node -> {
            if ( node instanceof TextNode textNode ) {
                sb.append ( textNode.getText() );
            }
        });

        return sb.isEmpty() ? null : sb.toString();
    }
}
