package dev.pmlc.core.data.formalnode.block.table;

import dev.pmlc.core.data.formalnode.FormalPMLNodeCreator;
import dev.pp.basics.annotations.NotNull;

public class FormalTableBodyNode {

    public static final @NotNull String HTML_TAG = "tbody";

    public static final @NotNull String CSS_CLASS = FormalPMLNodeCreator.prefixedHTMLClassName ( "table-body" );
}
