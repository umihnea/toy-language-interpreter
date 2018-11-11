package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.files.FileData;
import com.inter.utils.adt.IDictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStatement extends Statement {

    private String key;  // key identifies a new entry in the symbol table for the file descriptor
    private String filename;

    public OpenRFileStatement(String key, String filename) {
        this.filename = filename;
        this.key = key;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<Integer, FileData> fileTable = state.getFileTable();
        IDictionary<String, Integer> symbolTable = state.getSymbolTable();

        int tableKey = fileTable.size() + 1;

        for (FileData entry : fileTable.values()) { // filenames in the FileTable must be distinct
            if (entry.getFilename().equals(this.filename))
                throw new InterpreterException(String.format("openRFile: File \"%s\" already opened.", filename));
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            FileData fileData = new FileData(tableKey, filename, br);
            fileTable.put(tableKey, fileData);
            symbolTable.put(this.key, tableKey);
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
