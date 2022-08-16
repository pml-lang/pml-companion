package dev.pmlc.core.data.formalnode.block.code;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.code.SourceCodeNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalSourceCodeNode {

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

    public static final @NotNull FormalParameter<String> LANGUAGE_ATTRIBUTE = new FormalParameter.Builder<> (
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

    public static final @NotNull FormalParameter<Boolean> USE_HIGHLIGHTER_ATTRIBUTE = new FormalParameter.Builder<> (
        "highlight", CommonDataTypes.BOOLEAN )
        .defaultValue ( true )
        .documentation ( "Use Source Code Syntax Highlighter",
            "Specifies whether the source code should be highlighted or not.",
            "highlight = no" )
        .build();

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute()
        .add ( LANGUAGE_ATTRIBUTE )
        .add ( USE_HIGHLIGHTER_ATTRIBUTE );

    public static final @NotNull String HTML_TAG = "pre";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "code" );

    public static final @NotNull FormalPMLNode<String, SourceCodeNode> NODE = FormalPMLNodeCreator.createForRawTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        SourceCodeNode::new, PMLNodesHandler::sourceCode, HTML_TAG, CSS_CLASS );
}
