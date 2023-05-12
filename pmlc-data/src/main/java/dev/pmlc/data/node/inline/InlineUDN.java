package dev.pmlc.data.node.inline;

import dev.pmlc.data.node.PMLNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.user_defined_node.UDNDefinition;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.scripting.ScriptingUtils;
import dev.pp.text.utilities.html.HTMLWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InlineUDN extends PMLInlineNode {


    public interface Script {
        // String write ( Parameters<String> stringAttributes, HTMLWriter writer ) throws IOException;
        String write ( Map<String,String> stringAttributes, HTMLWriter writer ) throws IOException;
    }

    public class Example implements Script {
        public String write ( Map<String,String> stringAttributes, HTMLWriter writer ) throws IOException {
            writer.write ( "<strong>" );
            return "<strong>";
        }
    }


    private final @NotNull UDNDefinition definition;

    private @Nullable Parameters<String> stringAttributes = null;

    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, InlineUDN> getNodeSpec() {
        return definition.toInlineNodeSpec();
    }


    public InlineUDN ( @NotNull UDNDefinition definition ) {

        super();

        this.definition = definition;
    }


    @Override
    @SuppressWarnings ( "unchecked" )
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        super.setAttributes ( parameters );

        if ( parameters != null ) {
            stringAttributes = (Parameters<String>) parameters;
        }
    }

    public void writeHTML ( @NotNull HTMLWriter writer, @NotNull PMLNodesHandler nodesHandler ) throws Exception {

        writeHTML ( definition.nodeStartScript(), definition.nodeEndScript(),
            stringAttributes, writer, nodesHandler, getChildNodes() );
    }

    protected static void writeHTML (
        @Nullable String nodeStartScript,
        @Nullable String nodeEndScript,
        @Nullable Parameters<String> stringAttributes,
        @NotNull HTMLWriter writer,
        @NotNull PMLNodesHandler nodesHandler,
        @Nullable List<? extends PMLNode> childNodes ) throws Exception {

        executeScript ( nodeStartScript, stringAttributes, writer );
        nodesHandler.handleChildNodes ( childNodes );
        executeScript ( nodeEndScript, stringAttributes, writer );
    }

    protected static void executeScript (
        @Nullable String script,
        @Nullable Parameters<String> stringAttributes,
        @NotNull HTMLWriter writer ) throws Exception {

        if ( script == null ) return;

        // TODO use caching (org.graalvm.polyglot.Source)

        Map<String, Object> bindings = new HashMap<>();
        bindings.put ( "attributes", stringAttributes == null ? null : stringAttributes.toStringMap() );
        bindings.put ( "writer", writer );

        @Nullable Object returnValue = ScriptingUtils.evaluateJavascriptExpression ( script, bindings, true );
        if ( returnValue instanceof String s ) {
            writer.write ( s );
        }

        // TODO
        // compile handlerScript to class
        // call class with helper as input
    }
}
