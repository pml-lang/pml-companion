package dev.pmlc.data.nodespec.block.code;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pmlc.data.node.block.code.SourceCodeNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class SourceCodeNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "code" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Source Code",
        "A block of source code.",
        """
            [code
                ~~~
                repeat 3 times
                    write_line ( "Hello" )
                .
                ~~~
            ]
            """ );

    public static final @NotNull ParameterSpec<String> LANGUAGE_ATTRIBUTE = new ParameterSpec.Builder<> (
        "lang", CommonDataTypes.STRING_OR_NULL )
        .defaultValue ( null )
        .documentation ( "Language",
            """
                The name of the language used in the source code.
                This value is used primarily for syntax highlighting.
                Specify 'auto' to request automatic language detection (works only if this feature is supported by the highlighter).
                Specify 'text' for simple plain text (without highlighting).
                To get the list of supported languages please consult the documentation of the syntax highlighter used. Some frequently used values are: c, css, java, javascript, html.""",
            "lang = java" )
        .build();

    public static final @NotNull ParameterSpec<Boolean> USE_HIGHLIGHTER_ATTRIBUTE = new ParameterSpec.Builder<> (
        "highlight", CommonDataTypes.BOOLEAN )
        .defaultValue ( true )
        .documentation ( "Use Source Code Syntax Highlighter",
            "Specifies whether the source code should be highlighted or not.",
            "highlight = no" )
        .build();

    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.mutableListWithIdAttribute()
        .add ( LANGUAGE_ATTRIBUTE )
        .add ( USE_HIGHLIGHTER_ATTRIBUTE )
        .copyToImmutable();

    public static final @NotNull String HTML_TAG = "pre";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "code" );

    public static final @NotNull PMLNodeSpec<String, SourceCodeNode> NODE = PMLNodeSpecCreator.createForRawTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        SourceCodeNode::new, PMLNodesHandler::sourceCode, HTML_TAG, CSS_CLASS );
}
