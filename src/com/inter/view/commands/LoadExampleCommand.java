package com.inter.view.commands;

import com.inter.controller.Controller;
import com.inter.exceptions.InterpreterException;
import com.inter.model.expressions.ArithmeticExpression;
import com.inter.model.expressions.ConstantExpression;
import com.inter.model.expressions.VariableExpression;
import com.inter.model.statements.AssignmentStatement;
import com.inter.model.statements.CompoundStatement;
import com.inter.model.statements.PrintStatement;
import com.inter.model.statements.Statement;

public class LoadExampleCommand extends Command {

    private Controller controller;

    public LoadExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws InterpreterException {
        controller.setProgram(this.getExample());
        System.out.println("Loaded example command.");
    }

    private Statement getExample() {
        return new CompoundStatement(
                new AssignmentStatement(
                        "a",
                        new ArithmeticExpression(
                                '+',
                                new ConstantExpression(2),
                                new ArithmeticExpression(
                                        '*',
                                        new ConstantExpression(3),
                                        new ConstantExpression(5)
                                )
                        )
                ),
                new CompoundStatement(
                        new AssignmentStatement("b",
                                new ArithmeticExpression(
                                        '+',
                                        new ArithmeticExpression(
                                                '-',
                                                new VariableExpression("a"),
                                                new ArithmeticExpression(
                                                        '/',
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

}
