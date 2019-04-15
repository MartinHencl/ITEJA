package strom.tokenClass;

public enum TokenType {
    T_NOT_TOKEN(" "),

    // KEYWORD
    T_CONST("const"),            //  klicove slovo pred nazvem identifikatoru, ktery bude konstantni
    T_VAR("var"),              //  klicove slovo pred nazvem identifikatoru
    T_IF("if"),
    T_WHILE("while"),
    T_BEGIN("begin"),
    T_END("end"),
    T_PROCEDURE("procedure"),
    T_DO("do"),
    T_THEN("then"),
    T_CALL("call"),
    T_WRITE("write"),
    T_WRITELN("writeln"),
    T_ODD("odd"),

    //  OPERATOR
    T_PLUS("+"),
    T_MINUS("-"),
    T_MULTIPLY("*"),
    T_DIVIDE("/"),
    T_EQUAL("="),
    T_NOT_EQUAL("#"),
    T_SET(":="),
    T_SMALLER("<"),
    T_BIGGER(">"),
    T_SMALLER_EQUAL("<="),
    T_BIGGER_EQUAL(">="),

    //  ETC
    T_BRACKET_LEFT("("),
    T_BRACKET_RIGHT(")"),
    T_NUMBER_GENERAL(" "),            //  [0-9]}
    T_IDENTIFIER_GENERAL(" "),    //  [a-z]{[a-z0-9]}
    T_SEMICOLON(";"),
    T_COLON(","),
    T_EOF(".");

    private final String value;

    private TokenType(String s) {
        value = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because value.equals(null) returns false
        return value.equals(otherName);
    }

    public String toString() {
        return this.name();
    }

    public static TokenType fromString(String value) throws IllegalArgumentException {
        for (TokenType b : TokenType.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unknown enum value :" + value);
    }

    public static boolean isOperatorByString(String value) {
        return value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/") ||
                value.equals("=") || value.equals(":=") || value.equals("<") || value.equals(">") ||
                value.equals("<=") || value.equals(">=");
    }

    public static TokenType getEnumOfOperatorByString(String value) {
        switch (value) {
            case "+":
                return T_PLUS;
            case "-":
                return T_MINUS;
            case "*":
                return T_MULTIPLY;
            case "/":
                return T_DIVIDE;
            case "=":
                return T_EQUAL;
            case "#":
                return T_NOT_EQUAL;
            case ":=":
                return T_SET;
            case "<":
                return T_SMALLER;
            case ">":
                return T_BIGGER;
            case "<=":
                return T_SMALLER_EQUAL;
            case ">=":
                return T_BIGGER_EQUAL;
            default:
                return T_NOT_TOKEN;
        }
    }

    public static boolean isKeywordByString(String value) {
        return value.equals("const") || value.equals("var") || value.equals("if") || value.equals("while") ||
                value.equals("begin") || value.equals("end") || value.equals("procedure") || value.equals("do") ||
                value.equals("then") || value.equals("call") || value.equals("write") || value.equals("writeln") ||
                value.equals("odd");
    }

    public static TokenType getEnumOfKeywordByString(String value) {
        switch (value) {
            case "const":
                return T_CONST;
            case "var":
                return T_VAR;
            case "if":
                return T_IF;
            case "while":
                return T_WHILE;
            case "begin":
                return T_BEGIN;
            case "end":
                return T_END;
            case "procedure":
                return T_PROCEDURE;
            case "do":
                return T_DO;
            case "then":
                return T_THEN;
            case "call":
                return T_CALL;
            case "write":
                return T_WRITE;
            case "writeln":
                return T_WRITELN;
            case "odd":
                return T_ODD;
            default:
                return T_NOT_TOKEN;
        }
    }

    public static boolean isNumberByString(String value) {
        try {
            Double.valueOf(value);
            return true;
        } catch (NumberFormatException e) {
            //throw new NumberFormatException("Not a number!");
            return false;
        }
    }

    public static TokenType getEnumOfNumberByString(String value) {
        try {
            Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Not a number!");
        }
        return T_NUMBER_GENERAL;
    }

    public static boolean isDelimiterByString(String value) {
        return value.equals(";") || value.equals(",") || value.equals(".");
    }

    public static TokenType getDelimiterByString(String value) {
        switch (value) {
            case ";":
                return T_SEMICOLON;
            case ",":
                return T_COLON;
            case ".":
                return T_EOF;
            default:
                return T_NOT_TOKEN;
        }
    }
}
