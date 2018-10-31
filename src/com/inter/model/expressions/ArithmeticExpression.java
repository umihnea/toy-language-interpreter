package com.inter.model.expressions;

import com.inter.exceptions.EvaluationException;
import com.inter.utils.adt.IDictionary;

public class ArithmeticExpression extends Expression {

    private Expression a;
    private Expression b;
    private char operand;

    public ArithmeticExpression(char operand, Expression a, Expression b) {
        this.operand = operand;
        this.a = a;
        this.b = b;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> table) throws EvaluationException {
        int A = a.evaluate(table);
        int B = b.evaluate(table);

        switch (operand) {
            case '+':
                return A + B;
            case '-':
                return A - B;
            case '*':
                return A * B;
            case '/':
                if (B == 0) throw new EvaluationException("Division by 0.");
                return A / B;
            case '%':
                if (B == 0) throw new EvaluationException("Division by 0.");
                return A % B;
            default:
                throw new EvaluationException(String.format("Symbol %c not recognized.", operand));
        }
    }
}
