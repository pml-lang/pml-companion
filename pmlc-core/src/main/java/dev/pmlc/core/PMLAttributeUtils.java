package dev.pmlc.core;

import dev.pp.basics.annotations.NotNull;

public class PMLAttributeUtils {

    public static boolean isHTMLAttributeName ( @NotNull String name ) {

        return name.startsWith ( PMLConstants.HTML_ATTRIBUTE_PREFIX ) &&
            name.length() > PMLConstants.HTML_ATTRIBUTE_PREFIX_LENGTH;
    }

    public static @NotNull String removeHTMLAttributePrefix ( @NotNull String prefixedName ) {

        assert prefixedName.length() > PMLConstants.HTML_ATTRIBUTE_PREFIX_LENGTH;

        return prefixedName.substring ( PMLConstants.HTML_ATTRIBUTE_PREFIX_LENGTH );
    }
}
