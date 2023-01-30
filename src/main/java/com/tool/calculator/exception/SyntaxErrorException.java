package com.tool.calculator.exception;

public class SyntaxErrorException extends MathException {
    @Override
    public String getWarning() {
        return "Syntax Error";
    }
}
