package dev.pmlc.core.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PMLParserEventHandlerHelperTest {

    @Test
    void isAtWhitespaceSegment() {

        assertTrue ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "  ", 0 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( " ", 0 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( " a", 0 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( " [", 0 ) );
        assertTrue ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "a  ", 1 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "a b", 1 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "a", 0 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( " a", 0 ) );
        assertTrue ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "  a", 0 ) );
        assertTrue ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "\n\n", 0 ) );
        assertTrue ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( " \n", 0 ) );
        assertTrue ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "\n ", 0 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "\r\n", 0 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "\r\na", 0 ) );
        assertTrue ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "\r\n ", 0 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "\n", 0 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "\na", 0 ) );
        assertFalse ( PMLParserEventHandlerHelper.isAtWhitespaceSegment ( "\r\n[", 0 ) );
    }

    @Test
    void createWhitespaceSegment() {

        String string = "  ";
        PMLParserEventHandlerHelper.WhitespaceSegment segment =
            PMLParserEventHandlerHelper.createWhitespaceSegment ( string, 0 );
        assertEquals ( string, segment.string );
        assertEquals ( 0, segment.newLinesCount );
        assertEquals ( 2, segment.charsConsumedCount );
        assertFalse ( segment.isParagraphBreak() );

        string = "123 \n  \r\n 456";
        segment = PMLParserEventHandlerHelper.createWhitespaceSegment ( string, 3 );
        assertEquals ( " \n  \r\n ", segment.string );
        assertEquals ( 2, segment.newLinesCount );
        assertEquals ( 7, segment.charsConsumedCount );
        assertTrue ( segment.isParagraphBreak() );
    }

    @Test
    void createStandardTextSegment() {

        String string = "abc";
        PMLParserEventHandlerHelper.TextSegment segment =
            PMLParserEventHandlerHelper.createTextSegment ( "abc", 0 );
        assertEquals ( "abc", segment.string );
        assertEquals ( 3, segment.charsConsumedCount );

        segment = PMLParserEventHandlerHelper.createTextSegment ( "  abc 123\n456  789", 2 );
        assertEquals ( "abc 123 456", segment.string );
        assertEquals ( 11, segment.charsConsumedCount );

        segment = PMLParserEventHandlerHelper.createTextSegment ( "  abc 123\r\n456  789", 2 );
        assertEquals ( "abc 123 456", segment.string );
        assertEquals ( 12, segment.charsConsumedCount );

        segment = PMLParserEventHandlerHelper.createTextSegment ( "  abc 123\r\n 456  789", 2 );
        assertEquals ( "abc 123", segment.string );
        assertEquals ( 7, segment.charsConsumedCount );
    }

    @Test
    void createTextSegments() {

        List<PMLParserEventHandlerHelper.TextOrWhitespaceSegment> segments =
            PMLParserEventHandlerHelper.createTextOrWhitespaceSegments ( "a" );
        assertEquals ( 1, segments.size() );
        assertEquals ( "a", segments.get ( 0 ).string );

        segments = PMLParserEventHandlerHelper.createTextOrWhitespaceSegments ( "abc 123\n456\r\n789" );
        assertEquals ( 1, segments.size() );
        assertEquals ( "abc 123 456 789", segments.get ( 0 ).string );

        segments = PMLParserEventHandlerHelper.createTextOrWhitespaceSegments ( " \n " );
        assertEquals ( 1, segments.size() );
        assertEquals ( " \n ", segments.get ( 0 ).string );

        segments = PMLParserEventHandlerHelper.createTextOrWhitespaceSegments ( "para 1\n\npara 2\n34 \r\n  \r\npara 3" );
        assertEquals ( 5, segments.size() );
        assertEquals ( "para 1", segments.get ( 0 ).string );
        assertEquals ( "\n\n", segments.get ( 1 ).string );
        assertEquals ( "para 2 34", segments.get ( 2 ).string );
        assertEquals ( " \r\n  \r\n", segments.get ( 3 ).string );
        assertEquals ( "para 3", segments.get ( 4 ).string );
    }
}
