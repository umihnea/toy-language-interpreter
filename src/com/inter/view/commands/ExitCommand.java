package com.inter.view.commands;

import com.inter.exceptions.ExitSignal;

public class ExitCommand extends Command {

    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() throws ExitSignal {
        throw new ExitSignal();
    }
}
