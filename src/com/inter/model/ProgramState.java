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

    private IDictionary<String, FileData> fileTable;

    public ProgramState(IStack<Statement> stack, IDictionary<String, Integer> symbolTable, IList<String> out,
                        IDictionary<String, FileData> fileTable) {
        this.stack = stack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
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

    public IDictionary<String, FileData> getFileTable() {
        return fileTable;
    }

//    public void setStack(IStack<Statement> stack) {
//        this.stack = stack;
//    }
//
//    public void setSymbolTable(IDictionary<String, Integer> symbolTable) {
//        this.symbolTable = symbolTable;
//    }
//
//    public void setOut(IList<String> out) {
//        this.out = out;
//    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String hashCode = Integer.toString(System.identityHashCode(this));
        sb.append("\t").append(stack).append("\t").append(symbolTable).append("\t").append(out);
        return "ProgramState@" + hashCode + " = {\n" + sb.toString() + "}\n";
    }
}
