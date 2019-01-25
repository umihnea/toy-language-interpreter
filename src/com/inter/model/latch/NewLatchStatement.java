package com.inter.model.latch;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.Expression;
import com.inter.model.statements.Statement;

public class NewLatchStatement extends Statement {
    private String key;
    private Expression expr;

    public NewLatchStatement(String key, Expression expr) {
        this.key = key;
        this.expr = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        return null;
    }

    @Override
    public String toString() {
        return String.format("writeHeap(%s, %s)", this.key, this.expr);
    }
}
