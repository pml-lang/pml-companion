package dev.pmlc.core.parser;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

import java.util.ArrayList;
import java.util.List;

public class PMLWhitespaceHelper {

    public static class TextOrWhitespaceSegment {

        public final @NotNull String string;
        public final @Nullable TextLocation location;

        private TextOrWhitespaceSegment ( @NotNull String string, @Nullable TextLocation location ) {
            assert string != null && ! string.isEmpty();

            this.string = string;
            this.location = location;
        }
    }

    public static class WhitespaceSegment extends TextOrWhitespaceSegment {

        public WhitespaceSegment ( @NotNull String string, @Nullable TextLocation location ) {
            super ( string, location );
        }

        public boolean isParagraphBreak() {

            int newLinesCount = 0;
            for ( int i = 0; i < string.length(); i++ ) {
                if ( string.charAt ( i ) == '\n' ) newLinesCount++;
                if ( newLinesCount >= 2 ) return true;
            }
            return false;
        }
    }

    public static class TextSegment extends TextOrWhitespaceSegment {

        public TextSegment ( @NotNull String string, @Nullable TextLocation location ) {
            super ( string, location );
        }

        public @NotNull String replaceNewLinesWithSpace() {

            StringBuilder sb = new StringBuilder();
            for ( int i = 0; i < string.length(); i++ ) {
                char c = string.charAt ( i );
                switch ( c ) {
                    case '\n' -> sb.append ( ' ' );
                    case '\r' -> {}
                    default -> sb.append ( c );
                }
            }
            return sb.toString();
        }
    }

    public static @NotNull List<TextOrWhitespaceSegment> createTextOrWhitespaceSegments (
        @NotNull TextToken textToken ) {

//        return createTextOrWhitespaceSegments ( textToken.getText(), textToken.getLocation() );
        List<TextOrWhitespaceSegment> segments = new ArrayList<>();
        int currentIndex = 0;
        String string = textToken.getText();
        TextLocation startLocation = textToken.getLocation();

        while ( currentIndex < string.length() ) {

            // TODO startLocation should be redefined for each segment

            TextOrWhitespaceSegment segment;
            if ( isAtWhitespaceSegment ( string, currentIndex ) ) {
                segment = createWhitespaceSegment ( string, currentIndex, startLocation );
            } else {
                segment = createTextSegment ( string, currentIndex, startLocation );
            }
            segments.add ( segment );

            currentIndex = currentIndex + segment.string.length();
        }

        return segments;
    }
/*
    public static @NotNull List<TextOrWhitespaceSegment> createTextOrWhitespaceSegments (
        @NotNull String string,
        @Nullable TextLocation startLocation ) {
        assert ! string.isEmpty();

        List<TextOrWhitespaceSegment> segments = new ArrayList<>();
        int currentIndex = 0;

        while ( currentIndex < string.length() ) {

            // TODO location should be redefined for each segment

            TextOrWhitespaceSegment segment;
            if ( isAtWhitespaceSegment ( string, currentIndex ) ) {
                segment = createWhitespaceSegment ( string, currentIndex, startLocation );
            } else {
                segment = createTextSegment ( string, currentIndex, startLocation );
            }
            segments.add ( segment );

            currentIndex = currentIndex + segment.string.length();
        }

        return segments;
    }

 */

     /**
     * Check if we are positioned at 2 or more whitespace characters (\r\n counts as one character)
     */
     protected static boolean isAtWhitespaceSegment ( @NotNull String string, int index ) {

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

    protected static WhitespaceSegment createWhitespaceSegment (
        final @NotNull String string,
        final int startIndex,
        final @Nullable TextLocation location ) {

        int index;
        for ( index = startIndex; index < string.length(); index ++ ) {
            if ( ! isWhitespaceCharacter ( string.charAt ( index ) ) ) break;
        }

        return new WhitespaceSegment ( string.substring ( startIndex, index ), location );
    }

    protected static TextSegment createTextSegment (
        final @NotNull String string,
        final int startIndex,
        final @Nullable TextLocation location ) {

        int index;
        for ( index = startIndex; index < string.length(); index ++ ) {
            if ( isAtWhitespaceSegment ( string, index ) ) break;
        }

        return new TextSegment ( string.substring ( startIndex, index ), location );
    }

    private static boolean isWhitespaceCharacter ( char character ) {

        return character <= ' ';
    }
}
