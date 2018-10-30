package com.inter.view.commands;

import com.inter.controller.Controller;
import com.inter.exceptions.InterpreterException;

public class StepOnceCommand extends Command {

    private Controller controller;

    public StepOnceCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws InterpreterException {
        controller.stepOnce();
        System.out.println("Stepped once.");
    }
}
