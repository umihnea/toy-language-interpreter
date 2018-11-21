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
                 * NEW(v, 20);
                 * NEW(a, 22);
                 * PRINT(v); */
                return new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(new HeapAllocStatement("v", new ConstantExpression(20)),
                                new CompoundStatement(new HeapAllocStatement("a", new ConstantExpression(22)),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                );
            case 5:
                /* v = 10;
                 * NEW(v, 20);
                 * NEW(a, 22);
                 * PRINT(100 + READ_HEAP(v));
                 * PRINT(100 + READ_HEAP(a)) */
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
            case 6:
                /* v = 10;
                 * NEW(v,20); NEW(a,22);
                 * WRITE_HEAP(a, 100-70);
                 * PRINT(a);            // 2
                 * PRINT(READ_HEAP(a)); // 30
                 */
                return new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(
                                new HeapAllocStatement("v", new ConstantExpression(20)),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new ConstantExpression(22)),
                                        new CompoundStatement(
                                                new HeapWriteStatement("a",
                                                        new ArithmeticExpression('-',
                                                                new ConstantExpression(100),
                                                                new ConstantExpression(70)
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("a")),
                                                        new PrintStatement(new ReadHeapExpression("a"))
                                                )
                                        )
                                )
                        )
                );
            case 7:
                /* v = 10;
                 * NEW(v,20); NEW(a,22);
                 * WRITE_HEAP(a, 30);
                 * PRINT(a);
                 * PRINT(READ_HEAP(a));
                 * a = 0;   // GC is called
                 * PRINT(READ_HEAP(a)); // fails
                 */
                return new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(
                                new HeapAllocStatement("v", new ConstantExpression(20)),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new ConstantExpression(22)),
                                        new CompoundStatement(
                                                new HeapWriteStatement("a",
                                                        new ConstantExpression(30)
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("a")),
                                                        new CompoundStatement(
                                                                new PrintStatement(new ReadHeapExpression("a")),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("a", new ConstantExpression(0)),
                                                                        new PrintStatement(new ReadHeapExpression("a"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );
            case 8:
                /* v = 6;
                 * WHILE (v-4) (
                 *    PRINT(v);
                 *    v = (v - 1);
                 * );
                 * PRINT(v);
                 */
                return new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(6)),
                        new CompoundStatement(new WhileStatement(
                                new ArithmeticExpression('-',
                                        new VariableExpression("v"),
                                        new ConstantExpression(4)
                                ),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v",
                                                new ArithmeticExpression('-',
                                                        new VariableExpression("v"),
                                                        new ConstantExpression(1)
                                                ))
                                )
                        ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                );
            default:
                return null;
        }
    }

}
