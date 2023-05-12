package dev.pmlc.data.user_defined_node;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.node.PMLNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pmlc.data.node.inline.InlineUDN;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

public record UDNDefinition (
    @NotNull String name,
    boolean isInlineNode,
    @Nullable ParameterSpecs<String> attributes,
    @Nullable String nodeStartScript,
    @Nullable String nodeEndScript,
    @Nullable SimpleDocumentation documentation ) {


    public @NotNull PMLNodeSpec<Void, ? extends PMLNode> toNodeSpec() {

        if ( isInlineNode ) {
            return toInlineNodeSpec();
        } else {
            // TODO return toBlockNodeSpec();
            return null;
        }
    }

    public @NotNull PMLNodeSpec<Void, InlineUDN> toInlineNodeSpec() {

        assert isInlineNode;

        return PMLNodeSpecCreator.createForInlineNode (
            new NodeName ( name ),
            attributes,
            true,
            true,
            documentation == null ? null : () -> documentation,
            () -> new InlineUDN ( this ),
            PMLNodesHandler::inlineUDN,
            "span",
            "pml-udn" );
    }
}
