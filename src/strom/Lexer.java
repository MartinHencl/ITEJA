package strom;

import strom.tokenClass.Token;
import strom.tokenClass.TokenType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class Lexer {
    private ArrayList<Token> tokenList;

    Lexer() {
        tokenList = new ArrayList<>();
    }

    public ArrayList<Token> getTokenList() {
        return tokenList;
    }

    void createListOfTokensForPL0(String code) {
        code = code.toLowerCase();
        final String regex = "(?=[,;])|\\s+";
        String tempWord[] = code.split(regex);

        int lineNumber = 0;
        int columnNumber = 0;
        for (String oneWord : tempWord) {
            if (TokenType.isKeywordByString(oneWord)) {
                Token<String> keywordToken = new Token<>(TokenType.getEnumOfKeywordByString(oneWord), oneWord, lineNumber, columnNumber);
                tokenList.add(keywordToken);
            } else if (TokenType.isOperatorByString(oneWord)) {
                Token<String> operatorToken = new Token<>(TokenType.getEnumOfOperatorByString(oneWord), oneWord, lineNumber, columnNumber);
                tokenList.add(operatorToken);
            } else if (TokenType.isNumberByString(oneWord)) {
                if (!oneWord.contains(".")) {
                    Token<Integer> numberToken = new Token<>(TokenType.getEnumOfNumberByString(oneWord), Integer.valueOf(oneWord), lineNumber, columnNumber);
                    tokenList.add(numberToken);
                } else {
                    Token<Double> numberToken = new Token<>(TokenType.getEnumOfNumberByString(oneWord), Double.valueOf(oneWord), lineNumber, columnNumber);
                    tokenList.add(numberToken);
                }
            } else if (TokenType.isDelimiterByString(oneWord)) {
                columnNumber--;     //  odeberu mezeru
                Token<String> delimiterToken = new Token<>(TokenType.getDelimiterByString(oneWord), oneWord, lineNumber, columnNumber);
                tokenList.add(delimiterToken);
            } else {
                Token<String> variableToken = new Token<>(TokenType.T_IDENTIFIER_GENERAL, oneWord, lineNumber, columnNumber);
                tokenList.add(variableToken);
            }
            columnNumber += oneWord.length();       //  slovo + mezera
        }
    }

    String readFile(String path, Charset encoding) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, encoding);
    }

    void printTokenList() {
        for (int i = 0; i < tokenList.size(); i++) {
            Token token = tokenList.get(i);
            System.out.printf("[%03d]: %-22s | %-10s | %03d | %03d", i, token.getTypeOfToken().toString(), token.getValueOfToken().toString(), token.getRowNumber(), token.getColumnNumber());
            System.out.println();
        }
    }
}
