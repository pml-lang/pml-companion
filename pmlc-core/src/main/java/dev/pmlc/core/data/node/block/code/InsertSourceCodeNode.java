package dev.pmlc.core.data.node.block.code;

import dev.pmlc.core.data.formalnode.FormalPMLNode;
import dev.pmlc.core.data.formalnode.block.code.FormalInsertSourceCodeNode;
import dev.pmlc.core.data.formalnode.block.code.FormalSourceCodeNode;
import dev.pmlc.core.data.node.block.PMLBlockNode;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.TextFileIO;
import dev.pp.parameters.parameter.Parameters;
import dev.pp.text.error.TextErrorException;
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
    public @NotNull FormalPMLNode<Void, InsertSourceCodeNode> getFormalNode() { return FormalInsertSourceCodeNode.NODE; }

    @Override
    public void createAttributes ( @Nullable Parameters parameters ) throws Exception {

        super.createAttributes ( parameters );

        assert parameters != null;

        filePath = parameters.getNonNull ( FormalInsertSourceCodeNode.FILE_ATTRIBUTE );
        TextFileUtils.checkIsExistingFile (
            filePath, parameters.getValueToken ( FormalInsertSourceCodeNode.FILE_ATTRIBUTE.getName() ) );

        language = parameters.getNullable ( FormalSourceCodeNode.LANGUAGE_ATTRIBUTE );
        useHighlighter = parameters.getNonNull ( FormalSourceCodeNode.USE_HIGHLIGHTER_ATTRIBUTE );
        fromRegex = parameters.getNullable ( FormalInsertSourceCodeNode.FROM_REGEX_ATTRIBUTE );
        toRegex = parameters.getNullable ( FormalInsertSourceCodeNode.TO_REGEX_ATTRIBUTE );
        includeFromRegex = parameters.getNonNull ( FormalInsertSourceCodeNode.INCLUDE_FROM_REGEX_ATTRIBUTE );
        includeToRegex = parameters.getNonNull ( FormalInsertSourceCodeNode.INCLUDE_TO_REGEX_ATTRIBUTE );

        rawText = defineCode ( parameters );
    }

    private @NotNull String defineCode ( @NotNull Parameters parameters ) throws IOException, TextErrorException {

        @Nullable String fileContent = TextFileIO.readTextFromUTF8File ( filePath );
        @NotNull String code = fileContent == null ? "" : fileContent;

        if ( fromRegex == null && toRegex == null ) return code;

        if ( fromRegex != null ) {

            Matcher matcher = fromRegex.matcher ( code );
            if ( ! matcher.find() ) throw new TextErrorException (
                "INVALID_FROM_REGEX",
                "Regex '" + fromRegex + "' could not be found in file '" + filePath + "'",
                parameters.getNameToken ( FormalInsertSourceCodeNode.FROM_REGEX_ATTRIBUTE.getName() ) );

            int startIndex = includeFromRegex ? matcher.start() : matcher.end();
            if ( startIndex >= code.length() ) throw new TextErrorException (
                "INVALID_FROM_REGEX",
                "There is no more code after regex '" + fromRegex + "' in file '" + filePath + "'",
                parameters.getNameToken ( FormalInsertSourceCodeNode.FROM_REGEX_ATTRIBUTE.getName() ) );

            code = code.substring ( startIndex );
        }

        if ( toRegex != null ) {

            Matcher matcher = toRegex.matcher ( code );
            if ( ! matcher.find() ) throw new TextErrorException (
                "INVALID_TO_REGEX",
                "Regex '" + toRegex + "' could not be found in file '" + filePath + "'",
                parameters.getNameToken ( FormalInsertSourceCodeNode.TO_REGEX_ATTRIBUTE.getName() ) );

            int endIndex = includeToRegex ? matcher.end() : matcher.start();
            if ( endIndex < 0 ) throw new TextErrorException (
                "INVALID_TO_REGEX",
                "There is no code before regex '" + toRegex + "' in file '" + filePath + "'",
                parameters.getNameToken ( FormalInsertSourceCodeNode.TO_REGEX_ATTRIBUTE.getName() ) );

            code = code.substring ( 0, endIndex );
        }

        return TextIndent.removeSmallestIndent ( code, true );
    }
}
