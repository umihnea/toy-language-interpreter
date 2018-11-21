package com.inter.model.expressions;

import com.inter.exceptions.EvaluationException;
import com.inter.utils.adt.IDictionary;

public class ArithmeticExpression extends Expression {

    private Expression lhs;
    private Expression rhs;
    private char operand;

    public ArithmeticExpression(char operand, Expression lhs, Expression rhs) {
        this.operand = operand;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> table, IDictionary<Integer, Integer> heap) throws EvaluationException {
        int a = lhs.evaluate(table, heap);
        int b = rhs.evaluate(table, heap);

        switch (operand) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new EvaluationException(String.format("%s: division by 0", this));
                return a / b;
            case '%':
                if (b == 0) throw new EvaluationException(String.format("%s: division by 0", this));
                return a % b;
            default:
                throw new EvaluationException(String.format("%s: unknown operand \"%c\"", this, operand));
        }
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", lhs, operand, rhs);
    }
}
