package strom.tokenClass;

public class Token<V> {
    private TokenType typeOfToken;
    private V valueOfToken;
    private int rowNumber;
    private int columnNumber;

    public Token() {
        this.typeOfToken = TokenType.T_NOT_TOKEN;
        this.valueOfToken = (V) TokenType.T_NOT_TOKEN.toString();
        rowNumber = 0;
        columnNumber = 0;
    }

    public Token(TokenType tokenType, V tokenValue, int rowNumber, int columnNumber) {
        this.typeOfToken = tokenType;
        this.valueOfToken = tokenValue;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public TokenType getTypeOfToken() {
        return typeOfToken;
    }

    public void setTypeOfToken(TokenType typeOfToken) {
        this.typeOfToken = typeOfToken;
    }

    public V getValueOfToken() {
        return valueOfToken;
    }

    public void setValueOfToken(V valueOfToken) {
        this.valueOfToken = valueOfToken;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    @Override
    public String toString() {
        return valueOfToken.toString();
    }
}
