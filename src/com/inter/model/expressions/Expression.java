package com.inter.model.expressions;

import com.inter.exceptions.EvaluationException;
import com.inter.utils.adt.IDictionary;

abstract public class Expression {
    public abstract int evaluate(IDictionary<String, Integer> table) throws EvaluationException;
}
