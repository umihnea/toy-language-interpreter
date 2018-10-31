package com.inter.model.statements;

import com.inter.model.ProgramState;
import com.inter.model.expressions.Expression;
import com.inter.utils.adt.IList;

public class PrintStatement extends Statement {

    private Expression e;

    public PrintStatement(Expression e) {
        this.e = e;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IList<String> out = state.getBuffer();
        out.append(e.toString());
        return state;
    }
}
