package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.files.FileData;
import com.inter.utils.adt.IDictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStatement extends Statement {
    private String key;
    private String filename;

    public OpenRFileStatement(String key, String filename) {
        this.filename = filename;
        this.key = key;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<String, FileData> fileTable = state.getFileTable();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            FileData fileData = new FileData(br);
            fileTable.put(key, fileData);
        } catch (FileNotFoundException e) {
            throw new InterpreterException(String.format("openRFile: File \"%s\" not found.", filename));
        }

        return state;
    }

    @Override
    public String toString() {
        return String.format("openRFile(%s, \"%s\")", this.key, this.filename);
    }
}
