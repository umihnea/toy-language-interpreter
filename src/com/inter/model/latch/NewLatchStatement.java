package com.inter.model.latch;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.ConstantExpression;
import com.inter.model.expressions.Expression;
import com.inter.model.statements.AssignmentStatement;
import com.inter.model.statements.Statement;

public class NewLatchStatement extends Statement {
    private String key;
    private Expression expression;
    private static int latchIndex;

    public NewLatchStatement(String key, Expression e) {
        this.key = key;
        this.expression = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        ILatchTable latchTable = state.getLatchTable();
        int latchId = latchIndex + 1;
        latchIndex++;
        latchTable.put(latchId, this.expression.evaluate(state.getSymbolTable(), state.getHeap()));
        return new AssignmentStatement(this.key, new ConstantExpression(latchId)).execute(state);
    }

    @Override
    public String toString() {
        return String.format("NEW-LATCH(%s, %s)", this.key, this.expression);
    }
}
