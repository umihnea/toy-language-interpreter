package com.inter.repository;

import com.inter.model.ProgramState;

import java.io.FileWriter;
import java.io.IOException;

/*
 * Single-state repository.
 */

public class Repository implements IRepository {

    private ProgramState programState;
    private FileWriter writer;

    public Repository() {
        this.programState = null;

        try {
            this.writer = new FileWriter("log.txt", true);
        } catch (IOException e) {
            throw new RepositoryException(String.format("From repo: %s", e.getMessage()));
        }
    }

    @Override
    public ProgramState getCurrentState() {
        return programState;
    }

    @Override
    public void setCurrentState(ProgramState p) {
        this.programState = p;
    }

    @Override
    public void log(ProgramState p) {
        try {
            this.writer.write(String.valueOf(p));
            this.writer.flush();
        } catch (IOException e) {
            throw new RepositoryException(String.format("From repo.log(): %s", e.getMessage()));
        }
    }
}
