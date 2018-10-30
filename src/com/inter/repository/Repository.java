package com.inter.repository;

import com.inter.model.ProgramState;

/*
Single-state repository.
 */

public class Repository implements IRepository {

    private ProgramState programState;

    public Repository() {
        this.programState = new ProgramState();
    }

    @Override
    public ProgramState getCurrentState() {
        return programState;
    }

    @Override
    public void setCurrentState(ProgramState p) {
        this.programState = p;
    }
}
