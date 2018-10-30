package com.inter.repository;

import com.inter.model.ProgramState;

public interface IRepository {
    ProgramState getCurrentState();

    void setCurrentState(ProgramState p);
}
