package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.BooleanExpression;

public class RepeatUntilStatement extends Statement {
    private BooleanExpression e;
    private Statement block;

    public RepeatUntilStatement(Statement block, BooleanExpression until) {
        this.e = until;
        this.block = block;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        System.err.println("DEBUG:" + e);
        BooleanExpression inverse = e.getInverse();
        System.err.println("DEBUG:" + inverse);

        // push the block
        state.getStack().push(block);

        // push the while with the inverted condition
        state.getStack().push(new WhileStatement(inverse, block));

        return null;
    }

    @Override
    public String toString() {
        return String.format("REPEAT { %s } UNTIL (%s)", block, e);
    }
}
