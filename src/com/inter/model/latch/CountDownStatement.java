package com.inter.model.latch;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.statements.Statement;

public class CountDownStatement extends Statement {
    private String key;

    public CountDownStatement(String key) {
        this.key = key;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        int latchValue;

        synchronized (state.getLatchTable()) {
            Integer latchId = state.getSymbolTable().get(key);

            if (latchId == null)
                throw new InterpreterException(String.format("%s: undefined variable %s", this, key));

            if (!state.getLatchTable().containsKey(latchId))
                return null;

            latchValue = state.getLatchTable().get(latchId);

            if (latchValue > 0) {
                state.getLatchTable().put(latchId, latchValue - 1);
                state.getBuffer().append(String.format("Program#%s counted down Latch#%d", state.getId(), latchId));
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("COUNT-DOWN(%s)", this.key);
    }
}