package com.inter.model.latch;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.Expression;
import com.inter.model.statements.Statement;

public class LatchAwaitStatement extends Statement {
    private String key;
    private Expression expression;
    private static int index;

    public LatchAwaitStatement(String key, Expression expr) {
        this.key = key;
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {

        return null;
    }

    @Override
    public String toString() {
        return String.format("new(%s, %s)", this.key, this.expression);
    }
}
