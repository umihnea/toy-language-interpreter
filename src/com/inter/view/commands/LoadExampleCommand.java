package com.inter.view.commands;

import com.inter.controller.Controller;
import com.inter.exceptions.ExitSignal;
import com.inter.exceptions.InterpreterException;
import com.inter.model.expressions.ArithmeticExpression;
import com.inter.model.expressions.ConstantExpression;
import com.inter.model.expressions.ReadHeapExpression;
import com.inter.model.expressions.VariableExpression;
import com.inter.model.statements.*;

import java.util.Scanner;

public class LoadExampleCommand extends Command {

    private Controller controller;

    public LoadExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    private Statement loop() throws ExitSignal {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Choose a program (or 0, to exit):");
            System.out.print(">> ");
            int key = in.nextInt();

            if (key == 0)
                throw new ExitSignal();

            Statement program = selectProgram(key);

            if (program == null) {
                System.out.println("Program not implemented.");
                continue;
            }

            return program;
        }
    }

    @Override
    public void execute() throws InterpreterException {
        try {
            Statement program = this.loop();
            controller.setProgram(program);
            System.out.println("Loaded example command.");
        } catch (ExitSignal es) {
            System.out.println("Exited example submenu.");
        }
    }

    private Statement selectProgram(int index) {
        switch (index) {
            case 1: {
                return new CompoundStatement(
                        new AssignmentStatement("a",
                                new ArithmeticExpression('+',
                                        new ConstantExpression(2),
                                        new ArithmeticExpression('*',
                                                new ConstantExpression(3),
                                                new ConstantExpression(5)
                                        )
                                )
                        ),
                        new CompoundStatement(
                                new AssignmentStatement("b",
                                        new ArithmeticExpression('+',
                                                new ArithmeticExpression('-',
                                                        new VariableExpression("a"),
                                                        new ArithmeticExpression('/',
                                                                new ConstantExpression(4),
                                                                new ConstantExpression(2)
                                                        )
                                                ),
                                                new ConstantExpression(7)
                                        )
                                ),
                                new PrintStatement(
                                        new VariableExpression("b")
                                )
                        )
                );
            }
            case 2: {
                /* a = 2 - 2;
                 * IF(a) THEN(
                 *    v = 2
                 * ) ELSE(
                 *    v = 3
                 * );
                 * PRINT(v) */
                return new CompoundStatement(
                        new AssignmentStatement(
                                "a",
                                new ArithmeticExpression('-',
                                        new ConstantExpression(2),
                                        new ConstantExpression(2)
                                )),
                        new CompoundStatement(
                                new IfStatement(
                                        new VariableExpression("a"),
                                        new AssignmentStatement("v", new ConstantExpression(2)),
                                        new AssignmentStatement("v", new ConstantExpression(3))
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                );
            }
            case 3:
                /* OPENRFILE(in_fd, "in.txt")
                 * READ(in_fd, a)
                 * PRINT(a)
                 * READ(in_fd, a)
                 * PRINT(a) */
                return new CompoundStatement(
                        new OpenRFileStatement("in_fd", "in.txt"),
                        new CompoundStatement(
                                new ReadFileStatement(new VariableExpression("in_fd"), "a"),
                                new CompoundStatement(
                                        new PrintStatement(new VariableExpression("a")),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("in_fd"), "a"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("a")),
                                                        new CloseFileStatement(new VariableExpression("in_fd"))
                                                )
                                        )
                                )
                        )
                );
            case 4:
                /* v = 10;
                 * new(v, 20);
                 * new(a, 22);
                 * print(v); */
                return new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(new HeapAllocStatement("v", new ConstantExpression(20)),
                                new CompoundStatement(new HeapAllocStatement("a", new ConstantExpression(22)),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                );
            case 5:
                /* v=10;
                 * new(v,20);new(a,22);
                 * print(100+rH(v));
                 * print(100+rH(a)) */
                return new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(
                                new HeapAllocStatement("v", new ConstantExpression(20)),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new ConstantExpression(22)),
                                        new CompoundStatement(
                                                new PrintStatement(
                                                        new ArithmeticExpression('+',
                                                                new ConstantExpression(100),
                                                                new ReadHeapExpression("v")
                                                        )
                                                ),
                                                new PrintStatement(
                                                        new ArithmeticExpression('+',
                                                                new ConstantExpression(100),
                                                                new ReadHeapExpression("a")
                                                        )
                                                )
                                        )
                                )
                        )
                );
            default:
                return null;
        }
    }

}
