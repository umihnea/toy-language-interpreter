package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.ConstantExpression;
import com.inter.model.expressions.Expression;
import com.inter.utils.adt.IDictionary;

public class HeapAllocStatement extends Statement {
    private String key;
    private Expression expression;
    private static int index;

    public HeapAllocStatement(String key, Expression expr) {
        this.key = key;
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<Integer, Integer> heap = state.getHeap();
        int address = index + 1;
        index++;
        heap.put(address, this.expression.evaluate(state.getSymbolTable(), state.getHeap()));
        return new AssignmentStatement(this.key, new ConstantExpression(address)).execute(state);
    }

    @Override
    public String toString() {
        return String.format("new(%s, %s)", this.key, this.expression);
    }
}
