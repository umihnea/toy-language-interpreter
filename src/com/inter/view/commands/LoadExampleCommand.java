package com.inter.view.commands;

import com.inter.controller.Controller;
import com.inter.exceptions.InterpreterException;
import com.inter.model.statements.Statement;

public class LoadExampleCommand extends Command {

    private Controller controller;

    public LoadExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws InterpreterException {
        controller.setProgram(this.getExample());
        System.out.println("Loaded example command.");
    }

    private Statement getExample() {
        /*
        TODO: this returns an actual program (nested Statement)
         */
        return null;
    }

}
