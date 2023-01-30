package com.tool.calculator.solver.arithmetic;

import com.tool.calculator.exception.DivideByZeroException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Operator {
    add, minus, times, divide;

    public static boolean isCalculationOperator(char c) {
        return (c == '+' || c == '-' || c == '×' || c == '÷');
    }

    public static boolean isTimesOrDivide(char c) {
        return (c == '×' || c == '÷');
    }

    public static Operator getOperator(char c) {
        return switch (c) {
            case '+' -> add;
            case '-' -> minus;
            case '×' -> times;
            case '÷' -> divide;
            default -> throw new IllegalArgumentException();
        };
    }

    public BigDecimal calculate(BigDecimal a, BigDecimal b) throws DivideByZeroException {
        return switch (this) {
            case add -> a.add(b);
            case minus -> a.subtract(b);
            case times -> a.multiply(b);
            case divide -> division(a, b);
        };
    }

    private BigDecimal division(BigDecimal a, BigDecimal b) throws DivideByZeroException {
        if (b.equals(BigDecimal.ZERO)) {
            throw new DivideByZeroException();
        }

        try {
            return a.divide(b);
        } catch (ArithmeticException e) {
            return a.divide(b, 8, RoundingMode.HALF_UP);
        }
    }
}
