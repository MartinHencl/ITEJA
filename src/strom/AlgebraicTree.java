package strom;

import strom.tokenClass.Token;
import strom.tokenClass.TokenType;

import java.util.ArrayList;

public class AlgebraicTree {

    String algebraicOperation = "2+5*4-8/2";    //  18
    Tree<String> binaryTree;
    ArrayList<Token> listOfTokens;
    Tree<Token> tokenTree;

    public AlgebraicTree() {
        binaryTree = new Tree();
        listOfTokens = new ArrayList<>();
        tokenTree = new Tree<>();
    }

    public AlgebraicTree(String algebraicOperation) {
        this.algebraicOperation = algebraicOperation;
        binaryTree = new Tree();
        listOfTokens = new ArrayList<>();
        tokenTree = new Tree<>();
    }

    private String loadNumberWhole(int indexOfTreeMainOperation ) {
        String tempNumber = "";
        int tempIndexCounter = indexOfTreeMainOperation;
        while (Character.isDigit(algebraicOperation.charAt(tempIndexCounter))) {
            tempNumber += algebraicOperation.charAt(tempIndexCounter++);
            if (tempIndexCounter >= algebraicOperation.length()) {
                break;
            }
        }
        return tempNumber;
    }

    private String getNumber(int indexOfTreeMainOperation ) {
        String tempNumber = "";     //  bez minus na zacatku
        if (Character.isDigit(algebraicOperation.charAt(indexOfTreeMainOperation))) {
            tempNumber = loadNumberWhole(indexOfTreeMainOperation);
            return tempNumber;
        }
        return "";    // trochu hnus
    }

    private boolean characterIsOperator(int indexOfTreeMainOperation ) {
        if (indexOfTreeMainOperation < algebraicOperation.length()) {
            char tempChar = algebraicOperation.charAt(indexOfTreeMainOperation);
            if ((tempChar == '+') || (tempChar == '-') || (tempChar == '*') || (tempChar == '/')) {
                return true;
            }
        }
        return false;
    }

    private char getOperator(int indexOfTreeMainOperation ) {
        char tempOperator = ' ';
        if (characterIsOperator(indexOfTreeMainOperation)) {
            tempOperator = algebraicOperation.charAt(indexOfTreeMainOperation);
            return tempOperator;
        }
        return '\0';    // trochu hnus
    }

    public void createTreeFromExpression() {
        int indexOfTreeMainOperation = 0;
        String number = getNumber(indexOfTreeMainOperation);
        indexOfTreeMainOperation += number.length();
        char operator = getOperator(indexOfTreeMainOperation);
        indexOfTreeMainOperation += 1;
        binaryTree.add(String.valueOf(operator), "root");
        while (indexOfTreeMainOperation < algebraicOperation.length()) {
            binaryTree.add(number, "left");
            number = getNumber(indexOfTreeMainOperation);
            indexOfTreeMainOperation += number.length();
            operator = getOperator(indexOfTreeMainOperation);
            indexOfTreeMainOperation += 1;
            if (operator == '\0') {
                binaryTree.add(String.valueOf(number), "right");
                break;
            }
            binaryTree.add(String.valueOf(operator), "right");
            binaryTree.setActiveNodeFromRightNode();
        }
    }

    public void createListOfTokens() {
        int indexOfLexer = 0;
        while (indexOfLexer < algebraicOperation.length()) {
            char tempChar = algebraicOperation.charAt(indexOfLexer);    //  TODO DALSI VARIANTA: ROZSEKAT TO PODLE BILYCH ZNAKU
            if (Character.isDigit(tempChar)) {
                Token<Integer> tempToken = new Token<>();                //  TODO JAK TO PREKOPAT ABY TADY SEL INTEGER A DOLE CHAR (A JINDY I DALSI TYP)
                tempToken.setTypeOfToken(TokenType.T_NUMBER_GENERAL);
                String tempNumberString = getNumber(indexOfLexer);
                tempToken.setValueOfToken(Integer.valueOf(tempNumberString));
                indexOfLexer += tempNumberString.length();
                listOfTokens.add(tempToken);
            } else if (characterIsOperator(indexOfLexer)) {
                Token<Character> tempToken = new Token<>();
                tempToken.setTypeOfToken(TokenType.getEnumOfOperatorByString(String.valueOf(tempChar)));
                tempToken.setValueOfToken(tempChar);
                indexOfLexer += 1;
                listOfTokens.add(tempToken);
            } else {
                System.out.println("Co tam sakra je!?");
                break;
            }
        }
    }

    public void createTreeFromTokens() {
        int indexListMain = 0;
        Token tokenNumber = listOfTokens.get(indexListMain++);
        Token tokenOperator = listOfTokens.get(indexListMain++);
        tokenTree.add(tokenOperator, "root");
        while (indexListMain < listOfTokens.size()) {
            tokenTree.add(tokenNumber, "left");
            tokenNumber = listOfTokens.get(indexListMain++);
            if (indexListMain >= listOfTokens.size()) {
                tokenTree.add(tokenNumber, "right");
                break;
            }
            tokenOperator = listOfTokens.get(indexListMain++);
            tokenTree.add(tokenOperator, "right");
            tokenTree.setActiveNodeFromRightNode();
        }
    }
}
