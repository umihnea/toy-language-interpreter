package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.Expression;
import com.inter.utils.adt.IList;

public class PrintStatement extends Statement {

    private Expression e;

    public PrintStatement(Expression e) {
        this.e = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IList<String> out = state.getBuffer();

        int value = e.evaluate(state.getSymbolTable(), state.getHeap());
        out.append(Integer.toString(value));

        return null;
    }

    @Override
    public String toString() {
        return "print(" + e.toString() + ")";
    }
}
