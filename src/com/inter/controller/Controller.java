package com.inter.controller;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.files.FileData;
import com.inter.model.statements.Statement;
import com.inter.repository.IRepository;
import com.inter.utils.DictionaryCollector;
import com.inter.utils.adt.Dictionary;
import com.inter.utils.adt.IDictionary;
import com.inter.utils.adt.List;
import com.inter.utils.adt.Stack;

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

    public ProgramState runToCompletion(ProgramState state) throws InterpreterException {
        this.repository.logState(state);

        while (!state.getStack().isEmpty()) {
            state = state.stepOnce();

            state.setHeap(garbageCollect(state.getSymbolTable().values(), state.getHeap())); /* Call to garbage collector */
            state.setFileTable(manageOpenFiles(state.getSymbolTable().values(), state.getFileTable())); /* Call to open file manager */
            this.repository.logState(state);
        }

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
                .collect(new DictionaryCollector<>());
    }

    private IDictionary<Integer, FileData> manageOpenFiles(Collection<Integer> symbolTableValues, IDictionary<Integer, FileData> fileTable) {
        /*
        manageOpenFiles - closes any open file which is not linked to from the symbol table

        Example
        ST: a=1, b=0, c=3
        FT: (1, a.txt), (2, b.txt)
        Obs. (2, b.txt) doesn't have any entry in ST "linking" to it, so we will close the file and remove it from FT

        Input
            symbolTableValues - the collection of all symbol table values
            fileTable - a reference to the open files table
        Output
            the modified file table
        */
        fileTable.entrySet().stream() // Close all files which do not have links in the symbol table
                .filter(e -> !symbolTableValues.contains(e.getKey()))
                .forEach(e -> e.getValue().close());

        return fileTable.entrySet().stream() // Return the list of all files, keeping only those with correspondences in the symbol table
                .filter(e -> symbolTableValues.contains(e.getKey()))
                .collect(new DictionaryCollector<>());
    }
}
