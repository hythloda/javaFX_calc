package com.tool.calculator.exception;

public class DivideByZeroException extends MathException {
    @Override
    public String getWarning() {
        return "Whoops! Divided by Zero";
    }
}
