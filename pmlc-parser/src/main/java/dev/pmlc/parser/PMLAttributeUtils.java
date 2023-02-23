package dev.pmlc.parser;

import dev.pp.basics.annotations.NotNull;

public class PMLAttributeUtils {

    public static final @NotNull String HTML_ATTRIBUTE_PREFIX = "html_";

    public static final int HTML_ATTRIBUTE_PREFIX_LENGTH = HTML_ATTRIBUTE_PREFIX.length();
    // public static final @NotNull String HTML_CLASS_ATTRIBUTE_NAME = HTML_ATTRIBUTE_PREFIX + "class";

    public static boolean isHTMLAttributeName ( @NotNull String name ) {

        return name.startsWith ( HTML_ATTRIBUTE_PREFIX ) &&
            name.length() > HTML_ATTRIBUTE_PREFIX_LENGTH;
    }

    public static @NotNull String removeHTMLAttributePrefix ( @NotNull String prefixedName ) {

        assert prefixedName.length() > HTML_ATTRIBUTE_PREFIX_LENGTH;

        return prefixedName.substring ( HTML_ATTRIBUTE_PREFIX_LENGTH );
    }
}
