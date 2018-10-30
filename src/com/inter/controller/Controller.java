package com.inter.controller;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.statements.Statement;
import com.inter.repository.IRepository;


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

    public void stepOnce() throws InterpreterException {
        if (this.program == null) throw new InterpreterException("No program loaded.");

        ProgramState p = repository.getCurrentState();
        p.flag++;
        System.out.println("ctrl: program state flag after one step: " + p.flag);
        // ...
        repository.setCurrentState(p);
    }

    public void runToCompletion() throws InterpreterException {
        if (this.program == null) throw new InterpreterException("No program loaded.");

        ProgramState p = repository.getCurrentState();
        p.flag = 1000;
        System.out.println("ctrl: program state flag after running: " + p.flag);
        // ...
        repository.setCurrentState(p);
        this.setProgram(null);
    }

}
