package dev.pmlc.core.parser;

import dev.pdml.core.parser.PDMLParser;
import dev.pdml.core.parser.PDMLParserOptions;
import dev.pdml.core.reader.PDMLReader;
import dev.pdml.core.reader.PDMLReaderImpl;
import dev.pdml.core.reader.PDMLReaderOptions;
import dev.pdml.core.reader.extensions.PDMLExtensionsHandler;
import dev.pdml.ext.commands.SharedDefaultOptions;
import dev.pdml.ext.extensions.PDMLExtensionsHandlerImpl;
import dev.pdml.ext.extensions.scripting.bindings.DocBinding;
import dev.pdml.ext.utilities.parser.PDMLParserOptionsBuilder;
import dev.pmlc.core.data.formalnode.FormalNodeRegistry;
import dev.pmlc.core.data.node.PMLNode;
import dev.pmlc.core.data.node.block.DocumentNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.TextFileIO;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.scripting.env.ScriptingEnvironment;
import dev.pp.scripting.env.ScriptingEnvironmentImpl;
import dev.pp.text.error.TextError;
import dev.pp.text.error.handler.TextErrorHandler;
import dev.pp.text.resource.File_TextResource;
import dev.pp.text.resource.TextResource;

import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;

public class PMLParser {

    public static @NotNull DocumentNode parseFile ( @NotNull Path file ) throws Exception {

        try ( Reader reader = TextFileIO.getUTF8FileReader ( file ) ) {
            return parseReader ( reader, new File_TextResource ( file ) );
        }
    }

    public static @NotNull DocumentNode parseString ( @NotNull String string ) throws Exception {

        try ( Reader reader = new StringReader ( string ) ) {
            return parseReader ( reader, null );
        }
    }

    public static @NotNull DocumentNode parseStdin() throws Exception {

        return parseReader ( OSIO.standardInputUTF8Reader(), null );
    }

    public static @NotNull DocumentNode parseReader (
        @NotNull Reader PMLCodeReader,
        @Nullable TextResource resource ) throws Exception {

        return parseReader ( PMLCodeReader, resource, SharedDefaultOptions.createErrorHandler () );
    }

    public static @NotNull DocumentNode parseReader (
        @NotNull Reader PMLCodeReader,
        @Nullable TextResource resource,
        @NotNull TextErrorHandler errorHandler ) throws Exception { // IOException, InvalidTextException {

        @Nullable TextError initialLastError = errorHandler.lastError();
        DocumentNode documentNode = parseReaderWithoutThrowingIfNonCancellingErrorDetected ( PMLCodeReader,  resource, errorHandler );
        errorHandler.throwIfNewErrors ( initialLastError );

        return documentNode;
    }

    private static @NotNull DocumentNode parseReaderWithoutThrowingIfNonCancellingErrorDetected (
        @NotNull Reader PMLCodeReader,
        @Nullable TextResource resource,
        @NotNull TextErrorHandler errorHandler ) throws Exception { // IOException, InvalidTextException {

        PDMLExtensionsHandler extensionsHandler = new PDMLExtensionsHandlerImpl();

        ScriptingEnvironment scriptingEnvironment = new ScriptingEnvironmentImpl ( true );

        PDMLReaderOptions readerOptions = new PDMLReaderOptions ( errorHandler, extensionsHandler, scriptingEnvironment );
        PDMLReader PDMLReader = new PDMLReaderImpl ( PMLCodeReader, resource, readerOptions );

        DocBinding docBinding = new DocBinding ( PDMLReader );
        scriptingEnvironment.addBinding ( docBinding.bindingName(), docBinding );

        PMLParserEventHandler eventHandler = new PMLParserEventHandler ( errorHandler, PDMLReader );

        PDMLParserOptions<PMLNode, DocumentNode> parserOptions = new PDMLParserOptionsBuilder<> ( eventHandler )
            .errorHandler ( errorHandler )
            .extensionsHandler ( extensionsHandler )
            .scriptingEnvironment ( scriptingEnvironment )
            // TODO? include only formal nodes that define a type
            .formalNodes ( FormalNodeRegistry.getAll() )
            .build();

        PDMLParser<PMLNode, DocumentNode> PDMLParser = new PDMLParser<>();
        PDMLParser.parsePDMLReader ( PDMLReader, parserOptions );
        return eventHandler.getResult();
    }
}
