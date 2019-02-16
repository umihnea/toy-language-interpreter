package com.inter.model.expressions;

import com.inter.exceptions.EvaluationException;
import com.inter.utils.adt.IDictionary;

public class BooleanExpression extends Expression {
    private String operand;
    private Expression lhs;
    private Expression rhs;

    public BooleanExpression(String operand, Expression lhs, Expression rhs) {
        this.operand = operand;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symTable, IDictionary<Integer, Integer> heapTable) throws EvaluationException {
        int a = lhs.evaluate(symTable, heapTable);
        int b = rhs.evaluate(symTable, heapTable);
        boolean r;

        switch (operand) {
            case "==":
                r = (a == b);
                break;
            case "!=":
                r = (a != b);
                break;
            case "<":
                r = (a < b);
                break;
            case ">":
                r = (a > b);
                break;
            case "<=":
                r = (a <= b);
                break;
            case ">=":
                r = (a >= b);
                break;
            default:
                throw new EvaluationException(String.format("%s: undefined operand \"%s\"", this, operand));
        }

        return (r) ? 1 : 0;
    }

    public BooleanExpression getInverse() throws EvaluationException {
        String inverseOperand;
        switch (operand) {
            case "==":
                inverseOperand = "!=";
                break;
            case "!=":
                inverseOperand = "==";
                break;
            case "<":
                inverseOperand = ">=";
                break;
            case ">":
                inverseOperand = "<=";
                break;
            case "<=":
                inverseOperand = ">";
                break;
            case ">=":
                inverseOperand = "<";
                break;
            default:
                throw new EvaluationException(String.format("%s: undefined operand \"%s\"", this, operand));
        }
        return new BooleanExpression(inverseOperand, lhs, rhs);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", lhs, operand, rhs);
    }
}
