package com.inter.model.latch;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.statements.Statement;

public class LatchAwaitStatement extends Statement {
    private String key;

    public LatchAwaitStatement(String key) {
        this.key = key;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        int latchValue;

        synchronized (state.getLatchTable()) {
            Integer latchId = state.getSymbolTable().get(key);

        if (latchId == null) {
            System.out.println("miss-in-symtable error at " + latchId);
            throw new InterpreterException(String.format("%s: undefined variable %s", this, key));
        }

        if (!state.getLatchTable().containsKey(latchId)) {
            System.out.println("no-key error at " + latchId);
            throw new InterpreterException(String.format("%s: latch %s not alloc'd", this, latchId));
        }

            latchValue = state.getLatchTable().get(latchId);
        }

        if (latchValue == 0)
            return null;

        state.getStack().push(this);
        return null;
    }

    @Override
    public String toString() {
        return String.format("AWAIT(%s)", this.key);
    }
}
