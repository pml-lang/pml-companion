package dev.pmlc.data.nodespec.block.table;

import dev.pmlc.data.nodespec.PMLNodeSpecCreator;
import dev.pp.basics.annotations.NotNull;

public class TableBodyNodeSpec {

    public static final @NotNull String HTML_TAG = "tbody";

    public static final @NotNull String CSS_CLASS = PMLNodeSpecCreator.prefixedHTMLClassName ( "table-body" );
}
