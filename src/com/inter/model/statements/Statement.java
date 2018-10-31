package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;

abstract public class Statement {
    public abstract ProgramState execute(ProgramState state) throws InterpreterException;
}
