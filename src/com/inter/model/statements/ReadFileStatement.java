package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.Expression;
import com.inter.model.files.FileData;
import com.inter.utils.adt.IDictionary;

public class ReadFileStatement extends Statement {

    private Expression fileId;
    private String readIntoKey;

    public ReadFileStatement(Expression fileId, String key) {
        this.fileId = fileId;
        this.readIntoKey = key;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<String, Integer> symbolTable = state.getSymbolTable();
        IDictionary<Integer, FileData> fileTable = state.getFileTable();

        // Resolve file
        int fileKey = fileId.evaluate(symbolTable); // throws on non-existent variable
        FileData fileData = fileTable.get(fileKey);
        if (fileData == null)   // handle non-existent file
            throw new InterpreterException(String.format("readFile: non-existent file (fd = %d)", fileKey));

        // Read into variable
        if (!fileData.hasNextInt())
            throw new InterpreterException("readFile: end-of-file reached");
        symbolTable.put(this.readIntoKey, fileData.nextInt());

        // Update the file (so we don't read twice)
        fileTable.put(fileKey, fileData);

        return state;
    }

    @Override
    public String toString() {
        return String.format("readFile(file:%s, %s)", this.fileId, this.readIntoKey);
    }

}
