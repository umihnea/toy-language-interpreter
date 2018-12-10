package com.inter.repository;

import com.inter.exceptions.RepositoryException;
import com.inter.model.ProgramState;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Repository implements IRepository {

    private List<ProgramState> stateList;
    private FileWriter writer;

    public Repository() {
        this.stateList = new ArrayList<>();
        initializeLogFile();
    }

    private void initializeLogFile() {
        try {
            writer = new FileWriter("log.txt", true);
        } catch (IOException e) {
            throw new RepositoryException(String.format("From repo: %s", e.getMessage()));
        }
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.stateList;
    }

    @Override
    public void setProgramList(List<ProgramState> lst) {
        this.stateList = lst;
    }

    @Override
    public void logState(ProgramState p) {
        /*
        Input: ProgramState p
        Logs the contents of given ProgramState p to the log file
         */
        try {
            this.writer.write(String.valueOf(p));
            this.writer.flush();
        } catch (IOException e) {
            throw new RepositoryException(String.format("From repo.log(): %s", e.getMessage()));
        }
    }
}
