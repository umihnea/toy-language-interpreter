package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.Expression;

public class HeapWriteStatement extends Statement {
    private String key;
    private Expression expr;

    public HeapWriteStatement(String key, Expression expr) {
        this.key = key;
        this.expr = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Integer heap_address = state.getSymbolTable().get(key);
        if (heap_address == null)
            throw new InterpreterException(String.format("%s: undefined variable %s", this, key));

        Integer heap_value = state.getHeap().get(heap_address);
        if (heap_value == null)
            throw new InterpreterException(String.format("%s: address %s not alloc'd", this, heap_address));

        state.getHeap().put(heap_address, expr.evaluate(state.getSymbolTable(), state.getHeap()));

        return state;
    }

    @Override
    public String toString() {
        return String.format("writeHeap(%s, %s)", this.key, this.expr);
    }
}
