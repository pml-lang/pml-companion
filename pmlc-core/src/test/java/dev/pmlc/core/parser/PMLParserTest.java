package dev.pmlc.core.parser;

import dev.pmlc.core.data.formalnode.block.FormalDocumentNode;
import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.error.TextErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PMLParserTest {

    @Test
    public void testParseString() throws Exception {

        @NotNull DocumentNode documentNode = PMLParser.parseString ( "[doc]" );
        Assertions.assertEquals ( FormalDocumentNode.NAME.toString(), documentNode.getName() );

        assertThrows ( TextErrorException.class, () -> PMLParser.parseString ( "[foo]" ) );
    }
}
