package dev.pmlc.data.nodespec.block.code;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.code.InsertSourceCodeNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class InsertSourceCodeNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "insert_code" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Insert Source Code",
        "Insert source code stored in an external file.",
        "[insert_code file=resources/source_code/hello_world.ppl]" );

    public static final @NotNull ParameterSpec<Path> FILE_ATTRIBUTE = new ParameterSpec.Builder<> (
        "file", CommonDataTypes.FILE_PATH )
        .documentation ( "File",
            """
                Absolute or relative path to the source code file.
                In case of a relative path, it is relative to the working directory at the time of running the application.""",
            "file = code_examples/hello.ppl" )
        .build();

    public static final @NotNull ParameterSpec<Pattern> FROM_REGEX_ATTRIBUTE = new ParameterSpec.Builder<> (
        "from_regex", CommonDataTypes.REGEX_OR_NULL )
        .defaultValue ( null )
        .documentation ( "From Regex",
            """
                If this parameter is not defined (set to null by default) then the source code inserted into the document starts at the beginning of the file content.
                A regular expression can be assigned to this parameter. In that case the source code inserted into the document starts at the first match of the regular expression in the source code file.

                If parameter 'include_from_regex' is set to 'true/yes' (default value), then the string that matches the regex is included in the document's source code.
                If parameter 'include_from_regex' is set to 'false/no' then the string that matches the regex is not included in the document's source code.""",
            """
                from_regex = "function foo ( name string )"
                from_regex = "[ \\t]*// start insert.*\\r?\\n" include_from_regex = no
                """ )
        .build();

    public static final @NotNull ParameterSpec<Pattern> TO_REGEX_ATTRIBUTE = new ParameterSpec.Builder<> (
        "to_regex", CommonDataTypes.REGEX_OR_NULL )
        .defaultValue ( null )
        .documentation ( "To Regex",
            """
                If this parameter is not defined (set to null by default) then the source code inserted into the document ends at the end of the file content.
                A regular expression can be assigned to this parameter. In that case the source code inserted into the document ends at the first match of the regular expression in the source code file.

                If parameter 'from_regex' is also defined, then the search for this regex starts after the match of 'from_regex'.
                If parameter 'include_to_regex' is set to 'true/yes' (default value), then the string that matches the regex is included in the document's source code.
                If parameter 'include_to_regex' is set to 'false/no' then the string that matches the regex is not included in the document's source code.""",
            """
                to_regex = "\\\\}\\r?\\n"
                to_regex = "\\\\s*// end insert" include_to_regex = no
                """ )
        .build();

    public static final @NotNull ParameterSpec<Boolean> INCLUDE_FROM_REGEX_ATTRIBUTE = new ParameterSpec.Builder<> (
        "include_from_regex", CommonDataTypes.BOOLEAN )
        .defaultValue ( true )
        .documentation ( "Include From Regex",
            "Please refer to the description of parameter 'from_regex' for an explanation of this parameter.",
            """
                from_regex = "// start insert.*\\r?\\n" include_from_regex = no
                """ )
        .build();

    public static final @NotNull ParameterSpec<Boolean> INCLUDE_TO_REGEX_ATTRIBUTE = new ParameterSpec.Builder<> (
        "include_to_regex", CommonDataTypes.BOOLEAN )
        .defaultValue ( true )
        .documentation ( "Include To Regex",
            "Please refer to the description of parameter 'to_regex' for an explanation of this parameter.",
            """
                to_regex = "\\s*// end insert" include_to_regex = no
                """ )
        .build();

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.mutableListWithIdAttribute()
        .add ( FILE_ATTRIBUTE )
        .add ( SourceCodeNodeSpec.LANGUAGE_ATTRIBUTE )
        .add ( SourceCodeNodeSpec.USE_HIGHLIGHTER_ATTRIBUTE )
        .add ( FROM_REGEX_ATTRIBUTE )
        .add ( TO_REGEX_ATTRIBUTE )
        .add ( INCLUDE_FROM_REGEX_ATTRIBUTE )
        .add ( INCLUDE_TO_REGEX_ATTRIBUTE )
        .copyToImmutable();

    public static final @NotNull String HTML_TAG = "pre";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "code" );

    public static final @NotNull PMLNodeSpec<Void, InsertSourceCodeNode> NODE = PMLNodeSpecCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, false, DOCUMENTATION,
        InsertSourceCodeNode::new, PMLNodesHandler::insertSourceCode, HTML_TAG, CSS_CLASS );
}
