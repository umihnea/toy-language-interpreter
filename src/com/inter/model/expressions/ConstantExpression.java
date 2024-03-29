package com.inter.model.expressions;

import com.inter.exceptions.EvaluationException;
import com.inter.utils.adt.IDictionary;

public class ConstantExpression extends Expression {
    private int k;

    public ConstantExpression(int k) {
        this.k = k;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> table, IDictionary<Integer, Integer> hash) throws EvaluationException {
        return k;
    }

    @Override
    public String toString() {
        return Integer.toString(k);
    }
}
