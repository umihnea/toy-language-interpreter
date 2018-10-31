package com.inter.controller;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.statements.Statement;
import com.inter.repository.IRepository;
import com.inter.utils.adt.Dictionary;
import com.inter.utils.adt.IStack;
import com.inter.utils.adt.List;
import com.inter.utils.adt.Stack;


public class Controller {

    private IRepository repository;
    private Statement program;

    public Controller(IRepository r) {
        this.repository = r;
        this.program = null;
    }

    public void setProgram(Statement s) {
        this.program = s;

        Stack<Statement> stack = new Stack<>();
        Dictionary<String, Integer> symbolTable = new Dictionary<>();
        List<String> output = new List<>();

        stack.push(s);

        ProgramState initial = new ProgramState(stack, symbolTable, output);
        this.repository.setCurrentState(initial);
    }

    public ProgramState getCurrentProgramState() {
        return this.repository.getCurrentState();
    }

    public void setCurrentProgramState(ProgramState newState) {
        this.repository.setCurrentState(newState);
    }

    public ProgramState stepOnce(ProgramState state) throws InterpreterException {
        IStack<Statement> stack = state.getStack();
        if (stack.isEmpty()) throw new InterpreterException("Stack is empty.");

        Statement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void runToCompletion() throws InterpreterException {
        if (this.program == null) throw new InterpreterException("No program loaded.");

        ProgramState state = repository.getCurrentState();
        while (!state.getStack().isEmpty()) {
            state = this.stepOnce(state);
        }
        repository.setCurrentState(state);
        this.setProgram(null);
    }

}
