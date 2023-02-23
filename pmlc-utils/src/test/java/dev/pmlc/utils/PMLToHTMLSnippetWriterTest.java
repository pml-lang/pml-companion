package dev.pmlc.utils;

import dev.pp.basics.utilities.string.StringConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PMLToHTMLSnippetWriterTest {

    @Test
    public void testGetHTMLSnippet() throws Exception {

        String input = "This is [i important].";
        String output = "<p class=\"pml-paragraph\">This is <i class=\"pml-italic\">important</i>.</p>" + StringConstants.OS_NEW_LINE;
        assertEquals ( output, PMLToHTMLSnippetWriter.getHTMLSnippet ( input ) );
    }
}
