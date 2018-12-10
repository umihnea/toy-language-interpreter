package com.inter.repository;

import com.inter.model.ProgramState;

import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> l);

    void logState(ProgramState p);
}
