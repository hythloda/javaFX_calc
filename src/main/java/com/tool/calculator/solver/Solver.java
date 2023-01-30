package com.tool.calculator.solver;


import com.tool.calculator.exception.MathException;
import com.tool.calculator.exception.SyntaxErrorException;
import com.tool.calculator.solver.arithmetic.ArithmeticSolver;
import com.tool.calculator.solver.arithmetic.Operator;

public class Solver {
    private final char openParentheses = '(';
    private final char closeParentheses = ')';
    private String equation;
    private String answer;
    private MathException exception = null;
    public Solver(String equation) {
        this.equation = equation;
        solve();
    }
    private void solve() {
        if (equation.equals("")) {
            answer = "";
            return;
        }
        addingTimesSign();
        try {
            calculate();
        } catch (MathException e) {
            exception = e;
        }
    }

    /**
     * convert a(b) to a×(b) or (a)(b) to (a)×(b)
     */
    private void addingTimesSign() {
        for (int i = 1; i < equation.length(); i++) {
            char oneChar = equation.charAt(i);
            if (oneChar == openParentheses) {
                char previousChar = equation.charAt(i - 1);
                if (Character.isDigit(previousChar) || previousChar == closeParentheses) {
                    String former = equation.substring(0, i);   //[0, i)
                    String latter = equation.substring(i);      //[i, end]
                    equation = former + '×' + latter;
                }
            }
        }
    }
    private void calculate() throws MathException {
        if (!isCorrectUseOfParenthesis()) {
            throw new SyntaxErrorException();
        }
        killAllParenthesis();
        ArithmeticSolver solver = new ArithmeticSolver(equation);
        solver.solve();
        answer = solver.getAnswer();
    }
    private boolean isCorrectUseOfParenthesis() {
        char lastChar = equation.charAt(equation.length() - 1);
        if (lastChar == openParentheses) {
            return false;
        }
        int numOfOpenParenthesis = 0;
        int numOfCloseParenthesis = 0;
        boolean openParenthesisAppear = false;
        for (int i = 0; i < equation.length(); i++) {
            char oneChar = equation.charAt(i);
            if (oneChar == openParentheses) {
                openParenthesisAppear = true;
                numOfOpenParenthesis++;
            } else if (oneChar == closeParentheses) {
                if (!openParenthesisAppear) {
                    return false;
                }
                if (i != equation.length() - 1) {
                    char nextChar = equation.charAt(i + 1);
                    if (!Operator.isCalculationOperator(nextChar)) {
                        return false;
                    }
                }
                char previousChar = equation.charAt(i - 1);
                if (previousChar == openParentheses) {
                    return false;
                }
                numOfCloseParenthesis++;
                openParenthesisAppear = false;
            }
        }
        int diff = numOfOpenParenthesis - numOfCloseParenthesis;
        for (int i = 0; i < diff; i++) {
            equation = equation.concat(")");
        }
        return true;
    }
    private void killAllParenthesis() throws MathException {
        int indexOfOpenParenthesis = -1;
        for (int i = 0; i < equation.length(); i++) {
            char oneChar = equation.charAt(i);
            if (oneChar == openParentheses) {
                indexOfOpenParenthesis = i;
            } else if (oneChar == closeParentheses) {
                assert indexOfOpenParenthesis != -1;
                String arithmeticEquation = equation.substring(indexOfOpenParenthesis + 1, i);

                ArithmeticSolver solver = new ArithmeticSolver(arithmeticEquation);
                solver.solve();
                String answer = solver.getAnswer();
                String former = equation.substring(0, indexOfOpenParenthesis);
                String latter = equation.substring(i + 1);
                equation = former + answer + latter;
                indexOfOpenParenthesis = -1;
                i = -1;
            }
        }
    }
    public String getAnswer() {
        if (exception == null) {
            return answer;
        }
        return exception.getWarning();
    }
}
