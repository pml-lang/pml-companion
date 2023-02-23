package dev.pmlc.data.nodespec;

import dev.pdml.shared.constants.CorePdmlConstants;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.DataType;
import dev.pp.datatype.nonunion.scalar.impls.string.StringDataType;
import dev.pp.datatype.nonunion.scalar.impls.string.StringMatchesRegexValidator;
import dev.pp.datatype.union.NullablePairDataType;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableParameterSpecs;
import dev.pp.parameters.parameterspecs.ParameterSpecs;

public class SharedNodeSpecAttributes {

    public static final @NotNull String ID_ATTRIBUTE_NAME = "id";

    public static final @NotNull DataType<String> ID_DATA_TYPE =  new NullablePairDataType<> ( new StringDataType (
        "id",
        new StringMatchesRegexValidator (
            CorePdmlConstants.NAME_PATTERN,
            "INVALID_ID",
            (value, pattern) -> "'" + value + "' is an invalid identifier. An identifier must start with a letter or an underscore (_), and can be followed by any number of letters, digits, underscores (_), dots (.), and hyphens (-)." ),
        null ) );

    public static final @NotNull ParameterSpec<String> ID_ATTRIBUTE = new ParameterSpec.Builder<> (
        ID_ATTRIBUTE_NAME, ID_DATA_TYPE )
        .defaultValue ( null )
        .documentation ( "Node Identifier",
            """
                A unique identifier for the node.

                An id can be used to:
                - identify a node so that an internal link can be done with an 'xref' (cross reference) node.
                - identify a node so that it can be styled individually with CSS
                - create an HTML anchor so that it can be accessed with the # (hash) sign (e.g. writing id=foo will enable you to have an HTML link ending with #foo.

                An identifier must start with a letter or an underscore (_), and can be followed by any number of letters, digits, underscores (_), dots (.), and hyphens (-).. Note for programmers: The regex of an identifier is: [a-zA-Z_][a-zA-Z0-9_\\.-]*. Identifiers are case-sensitive. The following identifiers are all different: name, Name, and NAME.
                """,
            "id = basic_concept" )
        .build();

    public static @NotNull MutableParameterSpecs mutableListWithIdAttribute() {
        return new MutableParameterSpecs().add ( ID_ATTRIBUTE );
    }

    public static @NotNull ParameterSpecs listWithIdAttribute() {
        return mutableListWithIdAttribute().copyToImmutable();
    }
}
