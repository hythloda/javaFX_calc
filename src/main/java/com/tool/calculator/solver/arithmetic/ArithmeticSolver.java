package com.tool.calculator.solver.arithmetic;

import com.tool.calculator.exception.DivideByZeroException;
import com.tool.calculator.exception.MathException;
import com.tool.calculator.exception.SyntaxErrorException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ArithmeticSolver {
    private String equation;
    private BigDecimal answer;
    private final List<BigDecimal> numbers = new ArrayList<>();
    private final List<Operator> operators = new ArrayList<>();
    public ArithmeticSolver(String equation) {
        this.equation = equation;
    }

    public void solve() throws MathException {
       // System.out.println("at the beginning " + equation);
        simplifyPlusAndMinusSign();
        simplifyPercentageSign();

        extractEquationToNumbersAndOperators();
       // System.out.println("numbers: " + numbers);
       // System.out.println("operator: " + operators);
        calculate();
    }

    private void simplifyPlusAndMinusSign() {
        for (int i = 0; i < equation.length() - 1; i++) {
            char firstChar = equation.charAt(i);
            char secondChar = equation.charAt(i + 1);

            if (Operator.isCalculationOperator(firstChar) && Operator.isCalculationOperator(secondChar)) {
                String str = new String(new char[]{firstChar, secondChar});
                // 0, 1, 2, i-1, i, i+1, i+2, ....
                String former = equation.substring(0, i);   // [0, i)
                String latter = equation.substring(i + 2);  // [i+2, end]

                if (str.equals("+-") || str.equals("-+")) {
                    equation = former + "-" + latter;
                    i = -1;
                } else if (str.equals("++") || str.equals("--")) {
                    equation = former + "+" + latter;
                    i = -1;
                }
            }
        }
    }

    /**
     * convert % to ÷100
     */
    private void simplifyPercentageSign() {
        for (int i = 0; i < equation.length(); i++) {
            if (equation.charAt(i) == '%') {
                String former = equation.substring(0, i);   // [0, i)
                String latter = equation.substring(i + 1);  // [i+1, end]
                equation = former + "÷100" + latter;
            }
        }
    }

    private void extractEquationToNumbersAndOperators() throws SyntaxErrorException {
        //System.out.println("extracting " + equation);

        boolean isNegative = false;
        Integer numStartIndex = null;

        for (int i = 0; i < equation.length(); i++) {
            char oneChar = equation.charAt(i);
            if (Character.isDigit(oneChar) || oneChar == '.') {
                if (numStartIndex == null) {
                    numStartIndex = i;
                }
            } else if (Operator.isCalculationOperator(oneChar)) {       //the char is an operator
                if (i == 0) {
                    if (oneChar == '-') {
                        isNegative = true;
                        continue;
                    } else if (oneChar == '+') {
                        continue;
                    } else if (Operator.isTimesOrDivide(oneChar)) {
                        throw new SyntaxErrorException();
                    }
                }
                if (i != 0) {
                    char previousChar = equation.charAt(i - 1);
                    if (Operator.isCalculationOperator(previousChar)) {
                        if (oneChar == '-') {
                            isNegative = true;
                            continue;
                        } else if (oneChar == '+') {
                            continue;
                        } else {
                            throw new SyntaxErrorException();
                        }
                    }
                }
                assert numStartIndex != null;
                BigDecimal number;
                try {
                    number = new BigDecimal(equation.substring(numStartIndex, i));
                } catch (NumberFormatException e) {
                    throw new SyntaxErrorException();
                }
                numbers.add((isNegative) ? (number.multiply(new BigDecimal(-1))) : number);
                isNegative = false;
                numStartIndex = null;
                operators.add(Operator.getOperator(oneChar));
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (numStartIndex == null) {
            throw new SyntaxErrorException();
        }
        BigDecimal number;
        try {
            number = new BigDecimal(equation.substring(numStartIndex));
        } catch (NumberFormatException e) {
            throw new SyntaxErrorException();
        }
        numbers.add((isNegative) ? (number.multiply(new BigDecimal(-1))) : number);
    }

    private void calculation(List<Operator> allowedOperator) throws DivideByZeroException {
        for (int i = 0; i < operators.size(); i++) {
            Operator operator = operators.get(i);
            if (allowedOperator.contains(operator)) {
                numbers.set(i, operator.calculate(numbers.get(i), numbers.get(i + 1)));
                numbers.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }
    }

    private void storeAns() {
        if (numbers.size() != 1) {
            throw new IllegalStateException("program have bug(s)...");
        }
        answer = numbers.get(0);
    }

    private void calculate() throws DivideByZeroException {
        calculation(List.of(Operator.times, Operator.divide));
        calculation(List.of(Operator.add, Operator.minus));
        storeAns();
    }

    public String getAnswer() {
        return answer.toString();
    }
}
