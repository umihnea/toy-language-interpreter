package com.inter.view.commands;

import com.inter.controller.Controller;
import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;

public class StepOnceCommand extends Command {

    private Controller controller;
    private Boolean displayFlag = false;

    public StepOnceCommand(String key, String description, Controller controller, Boolean displayFlag) {
        super(key, description);
        this.controller = controller;
        this.displayFlag = displayFlag;
    }

//    public StepOnceCommand(String key, String description, Controller controller) {
//        super(key, description);
//        this.controller = controller;
//    }

    @Override
    public void execute() throws InterpreterException {
        ProgramState state = this.controller.getCurrentProgramState();

        if (!controller.hasProgram())
            throw new InterpreterException("No program loaded.");
        else if (state.getStack().isEmpty())
            throw new InterpreterException("Program already done.");

        state = controller.stepOnce(state);
        this.controller.setCurrentProgramState(state);
        System.out.println("Stepped once.");

        if (displayFlag)
            System.out.println(state);

        if (state.getStack().isEmpty()) {
            System.out.println("Program finished. Output:");
            System.out.println(state.getBuffer());
        }
    }
}
