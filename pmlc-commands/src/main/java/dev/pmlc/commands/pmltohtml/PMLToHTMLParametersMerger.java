package dev.pmlc.commands.pmltohtml;

import dev.pdml.shared.constants.CorePdmlConstants;
import dev.pdml.utils.parser.StringParametersPdmlParser;
import dev.pmlc.converter.pmltohtml.options.PMLToHTMLResources;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.character.CharChecks;
import dev.pp.parameters.parameters.*;
import dev.pp.text.inspection.TextErrorException;
import dev.pp.text.reader.stack.CharReaderWithInserts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PMLToHTMLParametersMerger {

    private static final @NotNull String OPTIONS_NODE_NAME = "options";
    private static final @NotNull String OPTIONS_START = CorePdmlConstants.NODE_START + OPTIONS_NODE_NAME;

/*
    public static @NotNull PMLToHTMLOptions createMergedOptions (
        @Nullable MutableOrImmutableParameters<String> CLIOptions,
        @NotNull CharReaderWithInserts documentReader ) throws TextErrorException, IOException {

        Parameters<?> parameters = createMergedParameters ( CLIOptions, documentReader );
        return createOptions ( parameters );
    }
*/

    @SuppressWarnings ( {"unchecked", "rawtypes"} )
    public static @NotNull Parameters createMergedParameters (
        @Nullable MutableOrImmutableParameters<String> CLIParameters,
        @NotNull CharReaderWithInserts documentReader ) throws TextErrorException, IOException {

        @Nullable Parameters<String> stringParameters = createMergedStringParameters ( CLIParameters, documentReader );

        @Nullable Parameters<?> parameters = ParametersCreator.createFromStringParameters (
            stringParameters, null, PMLToHTMLParameters.SPECS );
        assert parameters != null;
        return parameters;
    }

    private static @Nullable Parameters<String> createMergedStringParameters (
        @Nullable MutableOrImmutableParameters<String> CLIParameters,
        @NotNull CharReaderWithInserts documentReader ) throws TextErrorException, IOException {

        @Nullable Parameters<String> documentOptions = readDocumentOptions ( documentReader );
        @Nullable Parameters<String> sharedOptions = readSharedOptions();

        return createMergedStringParameters ( CLIParameters, documentOptions, sharedOptions );
    }

    private static @Nullable Parameters<String> createMergedStringParameters (
        @Nullable MutableOrImmutableParameters<String> CLIParameters,
        @Nullable Parameters<String> documentOptions,
        @Nullable Parameters<String> sharedOptions ) {

        List<MutableOrImmutableParameters<String>> parametersList = new ArrayList<>();

        if ( sharedOptions != null ) {
            parametersList.add ( sharedOptions );
        }
        if ( documentOptions != null ) {
            parametersList.add ( documentOptions );
        }
        if ( CLIParameters != null ) {
            parametersList.add ( CLIParameters );
        }

        @Nullable Parameters<String> mergedStringParameters = parametersList.isEmpty()
             ? null
             : ParametersUtils.merge ( parametersList, PMLToHTMLParameters.SPECS );
        return mergedStringParameters;
    }

    private static @Nullable Parameters<String> readDocumentOptions (
        @NotNull CharReaderWithInserts charReader ) throws TextErrorException, IOException {

        // skip whitespace
        charReader.skipWhile ( CharChecks::isSpaceOrTabOrNewLine );

        // check if at "[options"
        if ( ! charReader.isAtString ( OPTIONS_START ) ) return null;

        return StringParametersPdmlParser.parse ( charReader, true );
    }

    private static @Nullable Parameters<String> readSharedOptions() throws TextErrorException, IOException {

        Path path = PMLToHTMLResources.OPTIONS_FILE;
        if ( ! Files.exists ( path) ) return null;

        return StringParametersPdmlParser.parseFile ( path, true );
    }
}
