package com.inter.view.commands;

import com.inter.controller.Controller;
import com.inter.exceptions.InterpreterException;

public class RunProgramCommand extends Command {

    private Controller controller;

    public RunProgramCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws InterpreterException {
        controller.runToCompletion();
        System.out.println("Finished program.");
    }
}
