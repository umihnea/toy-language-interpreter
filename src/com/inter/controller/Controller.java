package com.inter.controller;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.files.FileData;
import com.inter.model.statements.Statement;
import com.inter.repository.IRepository;
import com.inter.utils.DictionaryCollector;
import com.inter.utils.adt.*;

import java.util.Collection;


public class Controller {

    private IRepository repository;
    private Statement program;

    public Controller(IRepository r) {
        this.repository = r;
        this.program = null;
    }

    public void setProgram(Statement s) {
        this.program = s;

        Stack<Statement> stack = new Stack<>();
        Dictionary<String, Integer> symbolTable = new Dictionary<>();
        Dictionary<Integer, FileData> fileTable = new Dictionary<>();
        Dictionary<Integer, Integer> heap = new Dictionary<>();
        List<String> output = new List<>();

        stack.push(s);

        ProgramState initial = new ProgramState(stack, symbolTable, output, fileTable, heap);
        this.repository.setCurrentState(initial);
    }

    public boolean hasProgram() {
        return (this.program != null);
    }

    public ProgramState getCurrentProgramState() {
        return this.repository.getCurrentState();
    }

    public void setCurrentProgramState(ProgramState newState) {
        this.repository.setCurrentState(newState);
    }

    public ProgramState stepOnce(ProgramState state) throws InterpreterException {
        IStack<Statement> stack = state.getStack();
        if (stack.isEmpty())
            throw new InterpreterException("Stack is empty.");

        Statement currentStatement = stack.pop();
        this.repository.log(state);

        /* Call to garbage collector */
//        IDictionary<Integer, Integer> collectedHeap = garbageCollect(state.getSymbolTable().values(), state.getHeap());
//        state.setHeap(collectedHeap);
        state.setHeap(garbageCollect(state.getSymbolTable().values(), state.getHeap()));
        System.out.println("dbg>" + state.getHeap().toString());

        return currentStatement.execute(state);
    }

    public ProgramState runToCompletion(ProgramState state) throws InterpreterException {
        while (!state.getStack().isEmpty())
            state = this.stepOnce(state);
        return state;
    }

    private IDictionary<Integer, Integer> garbageCollect(Collection<Integer> symbolTableValues, IDictionary<Integer, Integer> heapTable) {
        /*
        garbageCollect (conservativeGarbageCollector) removes heap entries which are no longer linked from symbol table

        Input
            symbolTableValues - the collection of all symbol table values
            heapTable - a reference to the heap table
        Output
            the modified heap table
        */
        return heapTable.entrySet()
                .stream()
                .filter(e -> symbolTableValues.contains(e.getKey()))
                .collect(new DictionaryCollector());
    }
}
