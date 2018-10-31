package com.inter.view.commands;

import com.inter.controller.Controller;
import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;

public class RunProgramCommand extends Command {

    private Controller controller;

    public RunProgramCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws InterpreterException {
        ProgramState state = this.controller.getCurrentProgramState();

        if (!controller.hasProgram())
            throw new InterpreterException("No program loaded.");
        else if (state.getStack().isEmpty())
            throw new InterpreterException("Program already done.");

        state = controller.runToCompletion(state);

        controller.setCurrentProgramState(state);
        controller.setProgram(null);

        System.out.println("Program finished. Output:");
        System.out.println(state.getBuffer());
    }
}
