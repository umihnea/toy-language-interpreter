package com.inter.model.statements;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.files.FileData;
import com.inter.model.latch.ILatchTable;
import com.inter.utils.adt.Dictionary;
import com.inter.utils.adt.List;
import com.inter.utils.adt.Stack;

public class ForkStatement extends Statement {
    private Statement forkProgram;

    public ForkStatement(Statement fp) {
        this.forkProgram = fp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Stack<Statement> stack = new Stack<>();
        Dictionary<String, Integer> symbolTable = new Dictionary<>(state.getSymbolTable());
        Dictionary<Integer, FileData> fileTable = (Dictionary<Integer, FileData>) state.getFileTable();
        Dictionary<Integer, Integer> heap = (Dictionary<Integer, Integer>) state.getHeap();
        List<String> output = (List<String>) state.getBuffer();
        ILatchTable latchTable = state.getLatchTable();
        stack.push(forkProgram);

        return new ProgramState(stack, symbolTable, output, fileTable, heap, latchTable);
    }

    @Override
    public String toString() {
        return String.format("fork(%s)", forkProgram);
    }
}
