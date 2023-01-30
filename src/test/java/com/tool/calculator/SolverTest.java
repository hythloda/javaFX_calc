package com.tool.calculator;

import com.tool.calculator.exception.MathException;
import com.tool.calculator.exception.SyntaxErrorException;
import com.tool.calculator.solver.arithmetic.ArithmeticSolver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SolverTest {
    @Test
    void AddTest() throws MathException {
        ArithmeticSolver solver;
        solver = new ArithmeticSolver("+1");
        solver.solve();
        assertEquals(solver.getAnswer(), "1");

        solver = new ArithmeticSolver("1+1");
        solver.solve();
        assertEquals(solver.getAnswer(), "2");

        solver = new ArithmeticSolver("+1+2+++3");
        solver.solve();
        assertEquals(solver.getAnswer(), "6");

        solver = new ArithmeticSolver("1++2+3.3");
        solver.solve();
        assertEquals(solver.getAnswer(), "6.3");

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("1+2+");
            solver2.solve();
        });

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("+");
            solver2.solve();
        });

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("");
            solver2.solve();
        });
    }

    @Test
    void minusTest() throws MathException {
        ArithmeticSolver solver;
        solver = new ArithmeticSolver("-1");
        solver.solve();
        assertEquals(solver.getAnswer(), "-1");

        solver = new ArithmeticSolver("1-1");
        solver.solve();
        assertEquals(solver.getAnswer(), "0");

        solver = new ArithmeticSolver("10-9+-8");
        solver.solve();
        assertEquals(solver.getAnswer(), "-7");

        solver = new ArithmeticSolver("-10-5.5-1");
        solver.solve();
        assertEquals(solver.getAnswer(), "-16.5");

        solver = new ArithmeticSolver("--10-5.5-1");
        solver.solve();
        assertEquals(solver.getAnswer(), "3.5");

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("7-");
            solver2.solve();
        });

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("-");
            solver2.solve();
        });
    }

    @Test
    void timesTest() throws MathException {
        ArithmeticSolver solver;
        solver = new ArithmeticSolver("2×-3");
        solver.solve();
        assertEquals(solver.getAnswer(), "-6");

        solver = new ArithmeticSolver("3×0.5");
        solver.solve();
        assertEquals(solver.getAnswer(), "1.5");

        solver = new ArithmeticSolver("-1×-2×3×--4");
        solver.solve();
        assertEquals(solver.getAnswer(), "24");

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("×6");
            solver2.solve();
        });

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("89×");
            solver2.solve();
        });

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("×");
            solver2.solve();
        });
    }

    @Test
    void divideTest() throws MathException {
        ArithmeticSolver solver;
        solver = new ArithmeticSolver("10÷2");
        solver.solve();
        assertEquals(solver.getAnswer(), "5");

        solver = new ArithmeticSolver("-10÷4");
        solver.solve();
        assertEquals(solver.getAnswer(), "-2.5");

        solver = new ArithmeticSolver("+10÷-2÷-4");
        solver.solve();
        assertEquals(solver.getAnswer(), "1.25");

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("÷6");
            solver2.solve();
        });

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("89÷");
            solver2.solve();
        });

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("÷");
            solver2.solve();
        });
    }

    @Test
    void mixCalculationTest() throws MathException {
        ArithmeticSolver solver;
        solver = new ArithmeticSolver("1+2×3");
        solver.solve();
        assertEquals(solver.getAnswer(), "7");

        solver = new ArithmeticSolver("1+5÷2-1");
        solver.solve();
        assertEquals(solver.getAnswer(), "2.5");

        solver = new ArithmeticSolver("10÷2÷4");
        solver.solve();
        assertEquals(solver.getAnswer(), "1.25");

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("÷6");
            solver2.solve();
        });

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("89÷");
            solver2.solve();
        });

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver2 = new ArithmeticSolver("÷");
            solver2.solve();
        });
    }

    @Test
    void invalidTest() {
        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver = new ArithmeticSolver("1+÷6");
            solver.solve();
        });

        assertThrows(SyntaxErrorException.class, () -> {
            ArithmeticSolver solver = new ArithmeticSolver("12÷÷4");
            solver.solve();
        });
    }
}
