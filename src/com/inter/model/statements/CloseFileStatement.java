package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.Expression;
import com.inter.model.files.FileData;
import com.inter.utils.adt.IDictionary;

public class CloseFileStatement extends Statement {

    private Expression fileId;

    public CloseFileStatement(Expression fileId) {
        this.fileId = fileId;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<String, Integer> symbolTable = state.getSymbolTable();
        IDictionary<Integer, FileData> fileTable = state.getFileTable();

        int fileKey = fileId.evaluate(symbolTable, state.getHeap()); // throws on non-existent variable
        FileData fileData = fileTable.get(fileKey);
        if (fileData == null)   // handle non-existent file
            throw new InterpreterException(String.format("closeFile: non-existent file (fd = %d)", fileKey));
        fileData.close();
        fileTable.remove(fileKey);
        return state;
    }

    @Override
    public String toString() {
        return String.format("closeFile(%s)", this.fileId);
    }

}
