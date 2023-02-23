package dev.pmlc.data.node.block.code;

import dev.pmlc.data.nodespec.PMLNodeSpec;
import dev.pmlc.data.nodespec.block.code.InsertSourceCodeNodeSpec;
import dev.pmlc.data.nodespec.block.code.SourceCodeNodeSpec;
import dev.pmlc.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.TextFileIO;
import dev.pp.parameters.parameters.ParameterValueGetter;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.text.inspection.TextErrorException;
import dev.pp.text.utilities.file.TextFileUtils;
import dev.pp.text.utilities.text.TextIndent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertSourceCodeNode extends PMLBlockNode {

    private @NotNull Path filePath = Path.of ( "dummy.txt" );

    private @Nullable String language = null;
    public @Nullable String getLanguage() { return language; }

    private boolean useHighlighter = true;
    public boolean getUseHighlighter() {
        return useHighlighter;
    }

    private @Nullable Pattern fromRegex = null;
    private @Nullable Pattern toRegex = null;

    private boolean includeFromRegex = true;
    private boolean includeToRegex = true;


    public InsertSourceCodeNode () { super(); }


    @SuppressWarnings ( "unchecked" )
    public @NotNull PMLNodeSpec<Void, InsertSourceCodeNode> getNodeSpec () { return InsertSourceCodeNodeSpec.NODE; }

    @Override
    public void setAttributes ( @Nullable Parameters<?> parameters ) throws Exception {

        super.setAttributes ( parameters );

        assert parameters != null;

        ParameterValueGetter pg = new ParameterValueGetter ( parameters );

        filePath = pg.nonNull ( InsertSourceCodeNodeSpec.FILE_ATTRIBUTE );
        TextFileUtils.checkIsExistingFile (
            filePath, parameters.valueToken ( InsertSourceCodeNodeSpec.FILE_ATTRIBUTE.getName() ) );

        language = pg.nullable ( SourceCodeNodeSpec.LANGUAGE_ATTRIBUTE );
        useHighlighter = pg.nonNull ( SourceCodeNodeSpec.USE_HIGHLIGHTER_ATTRIBUTE );
        fromRegex = pg.nullable ( InsertSourceCodeNodeSpec.FROM_REGEX_ATTRIBUTE );
        toRegex = pg.nullable ( InsertSourceCodeNodeSpec.TO_REGEX_ATTRIBUTE );
        includeFromRegex = pg.nonNull ( InsertSourceCodeNodeSpec.INCLUDE_FROM_REGEX_ATTRIBUTE );
        includeToRegex = pg.nonNull ( InsertSourceCodeNodeSpec.INCLUDE_TO_REGEX_ATTRIBUTE );

        rawText = defineCode ( parameters );
    }

    private @NotNull String defineCode ( @NotNull Parameters<?> parameters ) throws IOException, TextErrorException {

        @Nullable String fileContent = TextFileIO.readTextFromUTF8File ( filePath );
        @NotNull String code = fileContent == null ? "" : fileContent;

        if ( fromRegex == null && toRegex == null ) return code;

        if ( fromRegex != null ) {

            Matcher matcher = fromRegex.matcher ( code );
            if ( ! matcher.find() ) throw new TextErrorException (
                "Regex '" + fromRegex + "' could not be found in file '" + filePath + "'",
                "INVALID_FROM_REGEX",
                parameters.nameToken ( InsertSourceCodeNodeSpec.FROM_REGEX_ATTRIBUTE.getName() ) );

            int startIndex = includeFromRegex ? matcher.start() : matcher.end();
            if ( startIndex >= code.length() ) throw new TextErrorException (
                "There is no more code after regex '" + fromRegex + "' in file '" + filePath + "'",
                "INVALID_FROM_REGEX",
                parameters.nameToken ( InsertSourceCodeNodeSpec.FROM_REGEX_ATTRIBUTE.getName() ) );

            code = code.substring ( startIndex );
        }

        if ( toRegex != null ) {

            Matcher matcher = toRegex.matcher ( code );
            if ( ! matcher.find() ) throw new TextErrorException (
                "Regex '" + toRegex + "' could not be found in file '" + filePath + "'",
                "INVALID_TO_REGEX",
                parameters.nameToken ( InsertSourceCodeNodeSpec.TO_REGEX_ATTRIBUTE.getName() ) );

            int endIndex = includeToRegex ? matcher.end() : matcher.start();
            if ( endIndex < 0 ) throw new TextErrorException (
                "There is no code before regex '" + toRegex + "' in file '" + filePath + "'",
                "INVALID_TO_REGEX",
                parameters.nameToken ( InsertSourceCodeNodeSpec.TO_REGEX_ATTRIBUTE.getName() ) );

            code = code.substring ( 0, endIndex );
        }

        return TextIndent.removeSmallestIndent ( code, true );
    }
}
