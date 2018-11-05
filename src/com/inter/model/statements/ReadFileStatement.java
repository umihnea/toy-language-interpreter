package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.files.FileData;
import com.inter.utils.adt.IDictionary;

public class ReadFileStatement extends Statement {

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<Integer, FileData> fileTable = state.getFileTable();

        return state;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
