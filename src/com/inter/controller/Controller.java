package com.inter.controller;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.files.FileData;
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
        Dictionary<Integer, FileData> fileTable = new Dictionary<>();
        List<String> output = new List<>();

        stack.push(s);

        ProgramState initial = new ProgramState(stack, symbolTable, output, fileTable);
        this.repository.setCurrentState(initial);
    }

    public boolean hasProgram() {
        return (this.program != null);
    }

    public ProgramState getCurrentProgramState() {
        return this.repository.getCurrentState();
    }

    public void setCurrentProgramState(ProgramState newState) {
        this.repository.setCurrentState(newState);
    }

    public ProgramState stepOnce(ProgramState state) throws InterpreterException {
        IStack<Statement> stack = state.getStack();
        if (stack.isEmpty())
            throw new InterpreterException("Stack is empty.");

        Statement currentStatement = stack.pop();
        this.repository.log(state);
        return currentStatement.execute(state);
    }

    public ProgramState runToCompletion(ProgramState state) throws InterpreterException {
        while (!state.getStack().isEmpty())
            state = this.stepOnce(state);
        return state;
    }

}
