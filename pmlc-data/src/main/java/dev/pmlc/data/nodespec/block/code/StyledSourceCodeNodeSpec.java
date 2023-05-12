package dev.pmlc.data.nodespec.block.code;

import dev.pdml.data.node.NodeName;
import dev.pmlc.data.node.block.code.StyledSourceCodeNode;
import dev.pmlc.data.node.handler.PMLNodesHandler;
import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pmlc.data.nodespec.SharedNodeSpecAttributes;
import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class StyledSourceCodeNodeSpec {

    public static final @NotNull NodeName NAME = new NodeName ( "scode" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Styled Source Code",
        """
            A block of styled source code.
            The source code can contain inline nodes to style it (e.g. [b a = 0]).
            Text must be escaped if necessary (e.g. instead of '[1]' you must write '\\[1\\]')
            """,
        """
            [code
                ~~~
                if list\\[index\\] = "foo" then
                    [b a = 0] // statement 'a = 0' is rendered in bold
                end
                ~~~
            ]
            """ );

    // TODO use attributes LANGUAGE_ATTRIBUTE and USE_HIGHLIGHTER_ATTRIBUTE?
    public static final @NotNull ParameterSpecs<?> ATTRIBUTES = SharedNodeSpecAttributes.mutableListWithIdAttribute()
        .add ( SourceCodeNodeSpec.LANGUAGE_ATTRIBUTE )
        .add ( SourceCodeNodeSpec.USE_HIGHLIGHTER_ATTRIBUTE )
        .copyToImmutable();

    public static final @NotNull String HTML_TAG = "pre";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "code" );

    public static final @NotNull PMLNodeSpec<String, StyledSourceCodeNode> NODE = PMLNodeSpecCreator.createForStyledTextBlockNode (
        NAME, ATTRIBUTES, true, DOCUMENTATION,
        StyledSourceCodeNode::new, PMLNodesHandler::styledSourceCode, HTML_TAG, CSS_CLASS );
}
