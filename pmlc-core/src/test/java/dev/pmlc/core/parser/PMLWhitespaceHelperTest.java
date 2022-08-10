package dev.pmlc.core.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PMLWhitespaceHelperTest {

    @Test
    void isAtWhitespaceSegment() {

        assertTrue ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "  ", 0 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( " ", 0 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( " a", 0 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( " [", 0 ) );
        assertTrue ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "a  ", 1 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "a b", 1 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "a", 0 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( " a", 0 ) );
        assertTrue ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "  a", 0 ) );
        assertTrue ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "\n\n", 0 ) );
        assertTrue ( PMLWhitespaceHelper.isAtWhitespaceSegment ( " \n", 0 ) );
        assertTrue ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "\n ", 0 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "\r\n", 0 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "\r\na", 0 ) );
        assertTrue ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "\r\n ", 0 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "\n", 0 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "\na", 0 ) );
        assertFalse ( PMLWhitespaceHelper.isAtWhitespaceSegment ( "\r\n[", 0 ) );
    }

    @Test
    void createWhitespaceSegment() {

        String string = "  ";
        PMLWhitespaceHelper.WhitespaceSegment segment =
            PMLWhitespaceHelper.createWhitespaceSegment ( string, 0 );
        assertEquals ( string, segment.string );
        assertFalse ( segment.isParagraphBreak() );

        string = "123 \n  \r\n 456";
        segment = PMLWhitespaceHelper.createWhitespaceSegment ( string, 3 );
        assertEquals ( " \n  \r\n ", segment.string );
        assertTrue ( segment.isParagraphBreak() );
    }

    @Test
    void createStandardTextSegment() {

        PMLWhitespaceHelper.TextSegment segment =
            PMLWhitespaceHelper.createTextSegment ( "abc", 0 );
        assertEquals ( "abc", segment.string );

        segment = PMLWhitespaceHelper.createTextSegment ( "  abc 123\n456  789", 2 );
        assertEquals ( "abc 123\n456", segment.string );
        assertEquals ( "abc 123 456", segment.replaceNewLinesWithSpace() );

        segment = PMLWhitespaceHelper.createTextSegment ( "  abc 123\r\n456  789", 2 );
        assertEquals ( "abc 123\r\n456", segment.string );
        assertEquals ( "abc 123 456", segment.replaceNewLinesWithSpace() );

        segment = PMLWhitespaceHelper.createTextSegment ( "  abc 123\r\n 456  789", 2 );
        assertEquals ( "abc 123", segment.string );
        assertEquals ( "abc 123", segment.replaceNewLinesWithSpace() );
    }

    @Test
    void createTextSegments() {

        List<PMLWhitespaceHelper.TextOrWhitespaceSegment> segments =
            PMLWhitespaceHelper.createTextOrWhitespaceSegments ( "a" );
        assertEquals ( 1, segments.size() );
        assertEquals ( "a", segments.get ( 0 ).string );

        String text = "abc 123\n456\r\n789";
        segments = PMLWhitespaceHelper.createTextOrWhitespaceSegments ( text );
        assertEquals ( 1, segments.size() );
        PMLWhitespaceHelper.TextSegment textSegment = (PMLWhitespaceHelper.TextSegment) segments.get ( 0 );
        assertEquals ( text, textSegment.string );
        assertEquals ( "abc 123 456 789", textSegment.replaceNewLinesWithSpace() );

        segments = PMLWhitespaceHelper.createTextOrWhitespaceSegments ( " \n " );
        assertEquals ( 1, segments.size() );
        assertEquals ( " \n ", segments.get ( 0 ).string );

        segments = PMLWhitespaceHelper.createTextOrWhitespaceSegments ( "para 1\n\npara 2\n34 \r\n  \r\npara 3" );
        assertEquals ( 5, segments.size() );
        assertEquals ( "para 1", segments.get ( 0 ).string );
        assertEquals ( "\n\n", segments.get ( 1 ).string );
        textSegment = (PMLWhitespaceHelper.TextSegment) segments.get ( 2 );
        assertEquals ( "para 2\n34", textSegment.string );
        assertEquals ( "para 2 34", textSegment.replaceNewLinesWithSpace() );
        assertEquals ( " \r\n  \r\n", segments.get ( 3 ).string );
        assertEquals ( "para 3", segments.get ( 4 ).string );
    }
}
