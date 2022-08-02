package dev.pmlc.core.parser;

import dev.pp.basics.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PMLParserEventHandlerHelper {

    public static class TextOrWhitespaceSegment {

        public final @NotNull String string;
        public final int charsConsumedCount;

        public TextOrWhitespaceSegment ( @NotNull String string, int charsConsumedCount ) {
            assert string != null && ! string.isEmpty();

            this.string = string;
            this.charsConsumedCount = charsConsumedCount;
        }
    }

    public static class WhitespaceSegment extends TextOrWhitespaceSegment {

        public final int newLinesCount;

        public WhitespaceSegment ( @NotNull String string, int newLinesCount, int charsConsumedCount ) {
            super ( string, charsConsumedCount );
            this.newLinesCount = newLinesCount;
        }

        public boolean isParagraphBreak() { return newLinesCount >= 2; }
    }

    public static class TextSegment extends TextOrWhitespaceSegment {

        public TextSegment ( @NotNull String string, int charsConsumedCount ) {
            super ( string, charsConsumedCount );
        }
    }


    private static boolean isWhitespaceCharacter ( char character ) {

        return character <= ' ';
    }

    public static boolean isAtWhitespaceSegment ( @NotNull String string, int index ) {

        // check if we are positioned at 2 or more whitespace characters (\r\n counts as one character)
        char currentChar = string.charAt ( index );
        char nextChar = 0;

        if ( currentChar == '\r' ) {
            // ignore the \n after \r
            if ( index < string.length() - 2 ) {
                nextChar = string.charAt ( index + 2 );
            }
        } else {
            if ( ! isWhitespaceCharacter ( currentChar ) ) return false;
            if  ( index < string.length() - 1 ) {
                nextChar = string.charAt ( index + 1 );
            }
        }

        if ( nextChar == 0 ) {
            return false;
        } else {
            return isWhitespaceCharacter ( nextChar );
        }
    }

    public static WhitespaceSegment createWhitespaceSegment (
        final @NotNull String string,
        final int startIndex ) {

        int index;
        int newLineCount = 0;
        for ( index = startIndex; index < string.length(); index ++ ) {
            char currentChar = string.charAt ( index );
            if ( ! isWhitespaceCharacter ( currentChar ) ) break;
            if ( currentChar == '\n' ) newLineCount++;
        }

        return new WhitespaceSegment (
            string.substring ( startIndex, index ),
            newLineCount,
            index - startIndex );
    }

    public static TextSegment createTextSegment (
        final @NotNull String string,
        final int startIndex ) {

        int index;
        StringBuilder sb = new StringBuilder();
        for ( index = startIndex; index < string.length(); index ++ ) {
            if ( isAtWhitespaceSegment ( string, index ) ) break;
            char currentChar = string.charAt ( index );
            if ( currentChar == '\r' ) continue; // ignore \r
            if ( isWhitespaceCharacter ( currentChar ) ) {
                sb.append ( ' ' ); // replace whitespace char with ' '
            } else {
                sb.append ( currentChar );
            }
        }

        return new TextSegment ( sb.toString(), index - startIndex );
    }

    public static @NotNull List<TextOrWhitespaceSegment> createTextOrWhitespaceSegments ( @NotNull String string ) {
        assert ! string.isEmpty();

        List<TextOrWhitespaceSegment> segments = new ArrayList<>();
        int currentIndex = 0;

        while ( currentIndex < string.length() ) {

            TextOrWhitespaceSegment segment;
            if ( isAtWhitespaceSegment ( string, currentIndex ) ) {
                segment = createWhitespaceSegment ( string, currentIndex );
            } else {
                segment = createTextSegment ( string, currentIndex );
            }
            segments.add ( segment );
            currentIndex = currentIndex + segment.charsConsumedCount;
        }

        return segments;
    }
}
