package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.Expression;
import com.inter.utils.adt.IDictionary;

public class AssignmentStatement extends Statement {
    private String key;
    private Expression e;

    public AssignmentStatement(String key, Expression e) {
        this.key = key;
        this.e = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<String, Integer> symbolTable = state.getSymbolTable();
        symbolTable.put(key, e.evaluate(state.getSymbolTable()));
        return state;
    }
}
