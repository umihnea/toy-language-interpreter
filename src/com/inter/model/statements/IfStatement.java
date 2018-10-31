package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.Expression;
import com.inter.utils.adt.IDictionary;
import com.inter.utils.adt.IStack;

public class IfStatement extends Statement {
    private Expression e;
    private Statement thenBranch;
    private Statement elseBranch;

    public IfStatement(Expression e, Statement thenBranch, Statement elseBranch) {
        this.e = e;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<String, Integer> symbolTable = state.getSymbolTable();
        IStack<Statement> stack = state.getStack();

        int value = e.evaluate(symbolTable);
        if (value != 0) stack.push(thenBranch);
        else stack.push(elseBranch);

        return state;
    }

    @Override
    public String toString() {
        return "IF (" + e.toString() + ") THEN\n(" + thenBranch.toString() + ")\nELSE\n(" + elseBranch.toString() + ")";
    }
}
