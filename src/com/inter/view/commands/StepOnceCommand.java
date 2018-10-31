package com.inter.view.commands;

import com.inter.controller.Controller;
import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;

public class StepOnceCommand extends Command {

    private Controller controller;

    public StepOnceCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws InterpreterException {
        ProgramState state = this.controller.getCurrentProgramState();
        ProgramState newState = controller.stepOnce(state);
        this.controller.setCurrentProgramState(newState);
        System.out.println("Stepped once.");
    }
}
