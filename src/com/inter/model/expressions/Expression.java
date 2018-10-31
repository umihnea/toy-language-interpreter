package com.inter.model.expressions;

import com.inter.exceptions.InterpreterException;
import com.inter.utils.adt.IDictionary;

public abstract class Expression {
    abstract int evaluate(IDictionary<String, Integer> table) throws InterpreterException;
}
