package com.inter.controller;

import com.inter.exceptions.InterpreterException;
import com.inter.model.ProgramState;
import com.inter.model.expressions.ConstantExpression;
import com.inter.model.files.FileData;
import com.inter.model.statements.PrintStatement;
import com.inter.model.statements.Statement;
import com.inter.repository.Repository;
import com.inter.utils.adt.Dictionary;
import com.inter.utils.adt.List;
import com.inter.utils.adt.Stack;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenFileManagerTest {
    private ProgramState s;

    @Before
    public void setUp() {
        Stack<Statement> stack = new Stack<>();
        Dictionary<String, Integer> symbolTable = new Dictionary<>();
        Dictionary<Integer, FileData> fileTable = new Dictionary<>();
        Dictionary<Integer, Integer> heap = new Dictionary<>();
        List<String> output = new List<>();

        this.s = new ProgramState(stack, symbolTable, output, fileTable, heap);
    }

    @Test
    public void testOpenFileManager() throws FileNotFoundException, InterpreterException {
        s.getFileTable().put(1, new FileData(1, "a.txt", new BufferedReader(new FileReader("a.txt"))));
        s.getFileTable().put(2, new FileData(2, "b.txt", new BufferedReader(new FileReader("b.txt"))));
        s.getFileTable().put(3, new FileData(3, "c.txt", new BufferedReader(new FileReader("c.txt"))));

        s.getSymbolTable().put("a", 1);
        s.getSymbolTable().put("b", 0);
        s.getSymbolTable().put("c", 3);

        s.getStack().push(new PrintStatement(new ConstantExpression(10)));

        Repository repo = new Repository();
        repo.setCurrentState(s);

        Controller ctrl = new Controller(repo);

        System.out.println(s.getFileTable()); // (1, "a.txt"), (2, "b.txt"), (3, "c.txt")
        s = ctrl.stepOnce(s);
        System.out.println(s.getFileTable()); // (1, "a.txt"), (3, "c.txt")
    }
}