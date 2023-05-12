package dev.pmlc.parser;

import dev.pmlc.data.nodespec.PMLNodeSpecs;
import dev.pmlc.data.user_defined_node.UDNDefinition;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.DebugUtils;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableParameterSpecs;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.scripting.bindings.ScriptingBinding;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.Map;

public class NodesBinding implements ScriptingBinding {


    private final @NotNull PMLNodeSpecs nodeSpecs;


    public NodesBinding ( @NotNull PMLNodeSpecs nodeSpecs ) {
        this.nodeSpecs = nodeSpecs;
    }


    public @NotNull String bindingName() {
        return "nodes";
    }

    public void add (
        @NotNull String nodeName,
        boolean isInlineNode,
        @Nullable ParameterSpecs<String> attributes,
        @Nullable String nodeStartScript,
        @Nullable String nodeEndScript,
        @Nullable SimpleDocumentation documentation ) {

        UDNDefinition definition = new UDNDefinition (
            nodeName, isInlineNode, attributes, nodeStartScript, nodeEndScript, documentation );
        nodeSpecs.add ( definition.toNodeSpec() );
    }

/*
    public void add (
        @NotNull String nodeName,
        boolean isInlineNode,
        @Nullable String nodeStartScript,
        @Nullable String nodeEndScript ) {

        DebugUtils.writeNameValue ( "nodeName", nodeName );

        add ( nodeName, isInlineNode, null, nodeStartScript, nodeEndScript, null );
    }
 */

    public void addInlineNode (
        @NotNull String nodeName,
        @Nullable Object[] attributes, // com.oracle.truffle.polyglot.PolyglotList (extends AbstractList) is provides by Javascript
        @Nullable String nodeStartScript,
        @Nullable String nodeEndScript ) {

        add ( nodeName, true, JSAttributesToParameterSpecs ( attributes ), nodeStartScript, nodeEndScript, null );
    }

    public ParameterSpecs<String> JSAttributesToParameterSpecs ( @Nullable Object[] JSAttributes ) {

        if ( JSAttributes == null ) return null;

        MutableParameterSpecs<String> specs = new MutableParameterSpecs<>();

        for ( Object JSAttribute : JSAttributes ) {
            // TODO check type
            Map map = (Map) JSAttribute;
            // TODO check exists
            String name = (String) map.get ( "name" );
            DebugUtils.writeNameValue ( "name", name );
            boolean hasDefaultValue = map.containsKey ( "default" );
            String defaultValue = null;
            if ( hasDefaultValue ) {
                // TODO check type or null
                defaultValue = (String) map.get ( "default" );
                DebugUtils.writeNameValue ( "defaultValue", defaultValue );
            }

            final String def = defaultValue;
            specs.add ( ParameterSpec.ofString ( name, hasDefaultValue ? () -> def : null ) );
        }

        return specs.makeImmutableOrNull();
    }
}
