package com.inter.view.commands;

import com.inter.controller.Controller;
import com.inter.exceptions.InterpreterException;

import java.util.concurrent.ExecutionException;

public class StepOnceCommand extends Command {

    private Controller controller;

    public StepOnceCommand(String key, String description, Controller controller) { //, Boolean displayFlag) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws InterpreterException, ExecutionException, InterruptedException {
        if (!controller.hasProgram())
            throw new InterpreterException("No program loaded.");

        controller.stepOnce();
        System.out.println(controller.getRepository());
    }
}
