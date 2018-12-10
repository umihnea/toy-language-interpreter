package com.inter.model.expressions;

import com.inter.controller.Controller;
import com.inter.exceptions.InterpreterException;
import com.inter.model.statements.AssignmentStatement;
import com.inter.repository.Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VariableExpressionTest {
    private Controller ctrl;

    @Before
    public void setUp() throws Exception {
        Repository repo = new Repository();
        this.ctrl = new Controller(repo);
    }

    @Test
    public void evaluate() throws InterpreterException {
        this.ctrl.setProgram(new AssignmentStatement("a", new ConstantExpression(10)));

        this.ctrl.stepOnce(this.ctrl.getCurrentProgramState());

        Assert.assertEquals(10, new VariableExpression("a").evaluate(
                this.ctrl.getCurrentProgramState().getSymbolTable(),
                this.ctrl.getCurrentProgramState().getHeap()
        ));
    }

    @Test
    public void testToString() {
        Assert.assertEquals("b", new VariableExpression("b").toString());
    }
}