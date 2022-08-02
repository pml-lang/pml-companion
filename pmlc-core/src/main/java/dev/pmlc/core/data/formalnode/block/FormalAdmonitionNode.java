package dev.pmlc.core.data.formalnode.block;

import dev.pdml.core.data.node.name.NodeName;
import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pmlc.core.data.formalnode.SharedFormalNodeAttributes;
import dev.pmlc.core.data.node.block.AdmonitionNode;
import dev.pmlc.core.nodeshandler.PMLNodesHandler;
import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.formalParameter.FormalParameter;
import dev.pp.parameters.formalParameter.list.FormalParameters;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.function.Supplier;

public class FormalAdmonitionNode {

    public static final @NotNull NodeName NAME = new NodeName ( "admon" );

    public static final @NotNull Supplier<SimpleDocumentation> DOCUMENTATION = () -> new SimpleDocumentation (
        "Admonition",
        "A labeled piece of advice, such as a note, tip, warning, etc.",
        """
            [admon (label=Tip)
                We will have a look at some [i striking] examples later in the book.
            ]""" );

    public static final @NotNull FormalParameter<String> LABEL_ATTRIBUTE = new FormalParameter.Builder<> (
        "label", CommonDataTypes.STRING )
        .documentation ( "Label",
            "The admonition's label, such as 'Note', 'Tip', 'Warning', etc.",
            "label = Tip" )
        .build();

    public static final @NotNull FormalParameters ATTRIBUTES = SharedFormalNodeAttributes.listWithIdAttribute()
        .add ( LABEL_ATTRIBUTE );

    public static final @NotNull String HTML_TAG = "div";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "admonition" );

    public static final @NotNull FormalPMLNode<Void, AdmonitionNode> NODE = FormalPMLNodeCreator.createForBlockNode (
        NAME, ATTRIBUTES, true, true, DOCUMENTATION,
        AdmonitionNode::new, PMLNodesHandler::admonition, HTML_TAG, CSS_CLASS );
}
