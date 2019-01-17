package com.inter.model;

import com.inter.exceptions.CollectionException;
import com.inter.exceptions.InterpreterException;
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

    private static int uid_seed = 0;
    private int uid;

    public ProgramState(IStack<Statement> stack, IDictionary<String, Integer> symbolTable, IList<String> out,
                        IDictionary<Integer, FileData> fileTable, IDictionary<Integer, Integer> heap) {
        this.stack = stack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.uid = uid_seed;
        uid_seed++;
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

    public void setFileTable(IDictionary<Integer, FileData> updatedFileTable) {
        this.fileTable = updatedFileTable;
    }

    public IDictionary<Integer, Integer> getHeap() {
        return heap;
    }

    public void setHeap(IDictionary<Integer, Integer> newHeap) {
        this.heap = newHeap;
    }

    public boolean isNotCompleted() {
        return !this.stack.isEmpty();
    }

    public boolean isCompleted() {
        return this.stack.isEmpty();
    }

    public ProgramState stepOnce() throws InterpreterException {
        IStack<Statement> stack = getStack();
//        if (stack.isEmpty())
//            throw new InterpreterException("Stack is empty.");
        try {
            Statement currentStatement = stack.pop();
            return currentStatement.execute(this);
        } catch (CollectionException ce) {
            return null;
        }

//        return currentStatement.execute(this);
    }

    @Override
    public String toString() {
        String sb = String.format("\tstk: %s\n", stack) +
                String.format("\tsym-t: %s\n", symbolTable) +
                String.format("\tfile-t: %s\n", fileTable) +
                String.format("\theap: %s\n", heap) +
                String.format("\tout: %s\n", out);
        return String.format("Program#%s:\n{\n%s}\n", Integer.toString(uid), sb);
    }
}
