package com.inter.model;

import com.inter.model.files.FileData;
import com.inter.model.statements.Statement;
import com.inter.utils.adt.IDictionary;
import com.inter.utils.adt.IList;
import com.inter.utils.adt.IStack;

public class ProgramState {
    private IStack<Statement> stack;
    private IDictionary<String, Integer> symbolTable;
    private IList<String> out;

    private IDictionary<Integer, FileData> fileTable;
    private IDictionary<Integer, Integer> heap;

    public ProgramState(IStack<Statement> stack, IDictionary<String, Integer> symbolTable, IList<String> out,
                        IDictionary<Integer, FileData> fileTable, IDictionary<Integer, Integer> heap) {
        this.stack = stack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
    }

    public IStack<Statement> getStack() {
        return stack;
    }

    public IDictionary<String, Integer> getSymbolTable() {
        return symbolTable;
    }

    public IList<String> getBuffer() {
        return out;
    }

    public IDictionary<Integer, FileData> getFileTable() {
        return fileTable;
    }

    public IDictionary<Integer, Integer> getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String hashCode = Integer.toString(System.identityHashCode(this));
        sb.append(String.format("\tstk: %s\n", stack))
                .append(String.format("\tsym-t: %s\n", symbolTable))
                .append(String.format("\tfile-t: %s\n", fileTable))
                .append(String.format("\theap: %s\n", heap))
                .append(String.format("\tout: %s\n", out));

        return String.format("PS@%s:\n{\n%s}\n", hashCode, sb.toString());
    }
}
