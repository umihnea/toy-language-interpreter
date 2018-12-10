package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.Expression;

public class WhileStatement extends Statement {
    private Expression e;
    private Statement block;    // the code inside the while

    public WhileStatement(Expression e, Statement block) {
        this.e = e;
        this.block = block;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        int condition = e.evaluate(state.getSymbolTable(), state.getHeap());

        if (condition == 0) // the block doesn't execute
            return state;

        // push the block and re-push the while on the stack
        state.getStack().push(this);
        state.getStack().push(block);

        return null;
    }

    @Override
    public String toString() {
        return String.format("while (%s) { %s }", e, block);
    }
}
