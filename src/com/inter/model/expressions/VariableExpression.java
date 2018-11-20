package com.inter.model.expressions;

import com.inter.exceptions.EvaluationException;
import com.inter.utils.adt.IDictionary;

public class VariableExpression extends Expression {
    private String key;

    public VariableExpression(String key) {
        this.key = key;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> table, IDictionary<Integer, Integer> hash) throws EvaluationException {
        Integer value = table.get(this.key);
        if (value == null)
            throw new EvaluationException(String.format("Unknown variable '%s'.", this.key));
        return value;
    }

    @Override
    public String toString() {
        return key;
    }
}
