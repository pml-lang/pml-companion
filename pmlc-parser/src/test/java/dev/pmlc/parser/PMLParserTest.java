package dev.pmlc.parser;

import dev.pmlc.data.nodespec.block.DocumentNodeSpec;
import dev.pmlc.data.node.block.DocumentNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.TextErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PMLParserTest {

    @Test
    public void testParseString() throws Exception {

        @NotNull DocumentNode documentNode = PMLParser.parseString ( "[doc]" );
        assertEquals ( DocumentNodeSpec.NAME.toString(), documentNode.getName() );

        assertThrows ( TextErrorException.class, () -> PMLParser.parseString ( "[foo]" ) );
    }
}
