package com.inter.model.statements;

import com.inter.model.ProgramState;
import com.inter.utils.adt.IStack;

public class CompoundStatement extends Statement {

    private Statement first, second;

    public CompoundStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<Statement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return state;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";\n" + second.toString() + ")";
    }
}
