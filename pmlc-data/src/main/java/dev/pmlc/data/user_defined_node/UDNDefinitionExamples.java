package dev.pmlc.data.user_defined_node;

import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableParameterSpecs;

public class UDNDefinitionExamples {

    public static final @NotNull UDNDefinition STRONG = new UDNDefinition (
        "strong", true, null, "\"<strong>\"", "\"</strong>\"", null );

    public static final @NotNull UDNDefinition EM = new UDNDefinition (
        "em", true,
        new MutableParameterSpecs<String>().add ( ParameterSpec.ofString ( "a1", () -> "default a1" ) ).makeImmutable (),
        """
                const c = "<" + "em" + ">";
                c
                """, """
                // "</em>"
                writer.write ( "</em>" );
                writer.write ( attributes.get ( "a1" ) );
                // null;
                """, null );
}
