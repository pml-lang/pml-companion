package dev.pmlc.parser;

import dev.pdml.ext.PdmlExtensionsHandlerImpl;
import dev.pdml.extscripting.bindings.DocBinding;
import dev.pdml.parser.PdmlParser;
import dev.pdml.parser.PdmlParserOptions;
import dev.pdml.reader.PdmlReader;
import dev.pdml.reader.PdmlReaderImpl;
import dev.pdml.reader.PdmlReaderOptions;
import dev.pdml.reader.extensions.PdmlExtensionsHandler;
import dev.pdml.utils.SharedDefaultOptions;
import dev.pdml.utils.parser.PdmlParserOptionsBuilder;
import dev.pmlc.data.nodespec.NodeSpecRegistry;
import dev.pmlc.data.node.PMLNode;
import dev.pmlc.data.node.block.DocumentNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.TextFileIO;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.scripting.env.ScriptingEnvironment;
import dev.pp.scripting.env.ScriptingEnvironmentImpl;
import dev.pp.text.inspection.message.TextError;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.reader.stack.CharReaderWithInserts;
import dev.pp.text.reader.stack.CharReaderWithInsertsImpl;
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

        return parseReader ( PMLCodeReader, resource, null, null, SharedDefaultOptions.createErrorHandler () );
    }

    public static @NotNull DocumentNode parseReader (
        @NotNull Reader PMLCodeReader,
        @Nullable TextResource resource,
        @Nullable Integer lineOffset,
        @Nullable Integer columnOffset,
        @NotNull TextInspectionMessageHandler errorHandler ) throws Exception { // IOException, InvalidTextException {

        return parseReader ( CharReaderWithInsertsImpl.createAndAdvance ( PMLCodeReader, resource, lineOffset, columnOffset ), errorHandler );
    }

    public static @NotNull DocumentNode parseReader (
        @NotNull CharReaderWithInserts PMLCodeReader,
        @NotNull TextInspectionMessageHandler errorHandler ) throws Exception { // IOException, InvalidTextException {

        @Nullable TextError initialLastError = errorHandler.lastError();
        DocumentNode documentNode = parseReaderWithoutThrowingIfNonCancellingErrorDetected ( PMLCodeReader, errorHandler );
        errorHandler.throwIfNewErrors ( initialLastError );

        return documentNode;
    }

    private static @NotNull DocumentNode parseReaderWithoutThrowingIfNonCancellingErrorDetected (
        @NotNull CharReaderWithInserts PMLCodeReader,
        @NotNull TextInspectionMessageHandler errorHandler ) throws Exception { // IOException, InvalidTextException {

        PdmlExtensionsHandler extensionsHandler = new PdmlExtensionsHandlerImpl ();

        ScriptingEnvironment scriptingEnvironment = new ScriptingEnvironmentImpl ( true );

        PdmlReaderOptions readerOptions = new PdmlReaderOptions ( errorHandler, extensionsHandler, scriptingEnvironment );
        PdmlReader PDMLReader = new PdmlReaderImpl ( PMLCodeReader, readerOptions );

        DocBinding docBinding = new DocBinding ( PDMLReader );
        scriptingEnvironment.addBinding ( docBinding.bindingName(), docBinding );

        PMLParserEventHandler eventHandler = new PMLParserEventHandler ( errorHandler );

        PdmlParserOptions<PMLNode, DocumentNode> parserOptions = new PdmlParserOptionsBuilder<> ( eventHandler )
            .errorHandler ( errorHandler )
            .extensionsHandler ( extensionsHandler )
            .scriptingEnvironment ( scriptingEnvironment )
            // TODO? include only node specs that define a type
            .nodeSpecs ( NodeSpecRegistry.getAll() )
            .allowStandardAttributesStartSyntax ( true )
            .allowAlternativeAttributesStartSyntax ( true )
            .build();

        PdmlParser<PMLNode, DocumentNode> PDMLParser = new PdmlParser<>();
        PDMLParser.parsePDMLReader ( PDMLReader, parserOptions );

        return eventHandler.getResult();
    }
}
