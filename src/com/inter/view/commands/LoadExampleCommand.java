package com.inter.view.commands;

import com.inter.controller.Controller;
import com.inter.exceptions.ExitSignal;
import com.inter.exceptions.InterpreterException;
import com.inter.model.expressions.ArithmeticExpression;
import com.inter.model.expressions.ConstantExpression;
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
            default:
                return null;
        }
    }

}
