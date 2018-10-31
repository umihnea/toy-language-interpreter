package com.inter.model.expressions;

import com.inter.exceptions.EvaluationException;
import com.inter.utils.adt.IDictionary;

public abstract class Expression {
    abstract int evaluate(IDictionary<String, Integer> table) throws EvaluationException;
}