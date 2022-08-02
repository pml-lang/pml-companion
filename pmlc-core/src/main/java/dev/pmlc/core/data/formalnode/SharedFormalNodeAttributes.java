package dev.pmlc.core.data.formalnode;

import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.list.FormalParameters;

public class SharedFormalNodeAttributes {

    public static final @NotNull String ID_ATTRIBUTE_NAME = "id";

    public static final @NotNull FormalParameter<String> ID_ATTRIBUTE = new FormalParameter.Builder<> (
        ID_ATTRIBUTE_NAME, CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "Node Identifier",
            """
                A unique identifier for the node.

                An id can be used to:
                - identify a node so that an internal link can be done with an 'xref' (cross reference) node.
                - identify a node so that it can be styled individually with CSS
                - create an HTML anchor so that it can be accessed with the # (hash) sign (e.g. writing id=foo will enable you to have an HTML link ending with #foo.

                An identifier must start with a letter or an underscore, and can be followed by any number of letters, digits, underscores, dots, and hyphens. Note for programmers: The regex of an identifier is: [a-zA-Z_][a-zA-Z0-9_\\.-]*. Identifiers are case-sensitive. The following identifiers are all different: name, Name, and NAME.
                """,
            "id = basic_concept" )
        .build();

    public static @NotNull FormalParameters listWithIdAttribute() {
        return new FormalParameters().add ( ID_ATTRIBUTE );
    }
}
