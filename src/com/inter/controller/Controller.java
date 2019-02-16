package com.inter.controller;

import com.inter.model.ProgramState;
import com.inter.model.files.FileData;
import com.inter.model.latch.ILatchTable;
import com.inter.model.latch.LatchTable;
import com.inter.model.statements.Statement;
import com.inter.repository.IRepository;
import com.inter.utils.DictionaryCollector;
import com.inter.utils.adt.Dictionary;
import com.inter.utils.adt.IDictionary;
import com.inter.utils.adt.List;
import com.inter.utils.adt.Stack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Controller {

    private IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository r) {
        this.repository = r;
    }

    public void setProgram(Statement s) {
        Stack<Statement> stack = new Stack<>();
        Dictionary<String, Integer> symbolTable = new Dictionary<>();
        Dictionary<Integer, FileData> fileTable = new Dictionary<>();
        Dictionary<Integer, Integer> heap = new Dictionary<>();
        List<String> output = new List<>();
        ILatchTable latchTable = new LatchTable();

        stack.push(s);

        ProgramState initial = new ProgramState(stack, symbolTable, output, fileTable, heap, latchTable);
        ArrayList<ProgramState> initialList = new ArrayList<>();
        initialList.add(initial);
        this.repository.setProgramList(initialList);
    }

    public boolean hasProgram() {
        return (!this.repository.getProgramList().isEmpty());
    }

    public IRepository getRepository() {
        return this.repository;
    }

    private ArrayList<ProgramState> removeCompleted(ArrayList<ProgramState> states) {
        return (ArrayList<ProgramState>) states.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void stepOnce() throws InterruptedException, ExecutionException {
        /*
        This is a wrapper for the stepOnceForList method.
        This is called from StepOnceCommand in View to step only once.
         */
        stepOnceForList((ArrayList<ProgramState>) repository.getProgramList());
    }

    private void stepOnceForList(ArrayList<ProgramState> states) throws InterruptedException {
        /* NON-LAB REQUIREMENT: Instantiate a new executor PER STEP
         * TODO: maybe find a smarter way to do this?
         */
        executor = Executors.newFixedThreadPool(2);

        /* Log each state before step */
        states.forEach(s -> repository.logState(s));

        /* callableList is the list of tasks run by the executor, each is associated with a program */
        ArrayList<Callable<ProgramState>> callableList = (ArrayList<Callable<ProgramState>>) states
                .stream()
                .map(
                        /* On call, the task will execute stepOnce() on the state it was assigned
                         * and will return null OR a new program (if it executes a ForkStatement). */
                        (ProgramState s) -> (Callable<ProgramState>) (() -> {
                            return s.stepOnce();
                        })
                )
                .collect(Collectors.toList());
        /* NB! No function is actually called and states are not yet updated at this point */

        /* Execute Callables
         * 1. executor invokes all Callables => a list of Future<ProgramState>
         * 2. each future is mapped to (future.get()) -- NB! future.get() is blocking
         * 3. non-nulls are collected => newStates now contains all new programs created by forking
         */
        ArrayList<ProgramState> newStates = (ArrayList<ProgramState>) executor
                .invokeAll(callableList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(s -> s != null)
                .collect(Collectors.toList());


        /* Append the new program states */
        states.addAll(newStates);

        /* Log updated states */
        states.forEach(s -> repository.logState(s));

        /* NON-LAB REQUIRED: Remove completed */
        states = removeCompleted(states);

        /* NON-LAB REQUIRED: Call to garbage collector and open file manager */
        states.forEach(s -> garbageCollect(s.getSymbolTable().values(), s.getHeap()));
        states.forEach(s -> manageOpenFiles(s.getSymbolTable().values(), s.getFileTable()));

        /* Save to repository */
        repository.setProgramList(states);

        executor.shutdownNow();
    }

    public void runToCompletion() throws InterruptedException {
        /* stepAll() */
        ArrayList<ProgramState> stateList = removeCompleted((ArrayList<ProgramState>) repository.getProgramList());

        while (!stateList.isEmpty()) {
            stepOnceForList(stateList);
            stateList = removeCompleted(stateList);
        }

        repository.setProgramList(stateList);
    }


    private IDictionary<Integer, Integer> garbageCollect(Collection<Integer> symbolTableValues, IDictionary<Integer, Integer> heapTable) {
        //garbageCollect (conservativeGarbageCollector) removes heap entries which are no longer linked from symbol table
        //
        //Input
        //    symbolTableValues - the collection of all symbol table values
        //    heapTable - a reference to the heap table
        //Output
        //    the modified heap table
        return heapTable.entrySet()
                .stream()
                .filter(e -> symbolTableValues.contains(e.getKey()))
                .collect(new DictionaryCollector<>());
    }

    private IDictionary<Integer, FileData> manageOpenFiles(Collection<Integer> symbolTableValues, IDictionary<Integer, FileData> fileTable) {
        //manageOpenFiles - closes any open file which is not linked to from the symbol table
        //Example
        //  ST: a=1, b=0, c=3
        //  FT: (1, a.txt), (2, b.txt)
        //Obs. (2, b.txt) doesn't have any entry in ST "linking" to it, so we will close the file and remove it from FT
        //
        //Input
        //  symbolTableValues - the collection of all symbol table values
        //  fileTable - a reference to the open files table
        //Output
        //  the modified file table
        fileTable.entrySet().stream() // Close all files which do not have links in the symbol table
                .filter(e -> !symbolTableValues.contains(e.getKey()))
                .forEach(e -> e.getValue().close());

        return fileTable.entrySet().stream() // Return the list of all files, keeping only those with correspondences in the symbol table
                .filter(e -> symbolTableValues.contains(e.getKey()))
                .collect(new DictionaryCollector<>());
    }
}
