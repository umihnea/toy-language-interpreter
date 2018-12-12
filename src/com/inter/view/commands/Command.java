package com.inter.view.commands;

import com.inter.exceptions.InterpreterException;

import java.util.concurrent.ExecutionException;

abstract public class Command {
    private String key;
    private String description;

    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    abstract public void execute() throws InterpreterException, InterruptedException, ExecutionException;

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}