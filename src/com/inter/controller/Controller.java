package com.inter.controller;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.statements.Statement;
import com.inter.repository.IRepository;
import com.inter.utils.adt.IStack;


public class Controller {

    private IRepository repository;
    private Statement program;

    public Controller(IRepository r) {
        this.repository = r;
        this.program = null;
    }

    public void setProgram(Statement s) {
        this.program = s;
    }

    public ProgramState stepOnce(ProgramState state) throws InterpreterException {
        IStack<Statement> stack = state.getStack();
        if (stack.isEmpty()) throw new InterpreterException("Stack is empty.");

        Statement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void runToCompletion() throws InterpreterException {
        if (this.program == null) throw new InterpreterException("No program loaded.");

        ProgramState p = repository.getCurrentState();
        // ...
        repository.setCurrentState(p);
        this.setProgram(null);
    }

}
