package com.inter.model.statements;

import com.inter.controller.Controller;
import com.inter.exceptions.InterpreterException;
import com.inter.model.expressions.ConstantExpression;
import com.inter.repository.Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AssignmentStatementTest {
    private Controller ctrl;

    @Before
    public void setUp() throws Exception {
        Repository repo = new Repository();
        this.ctrl = new Controller(repo);
    }

    @Test
    public void testExecute() throws InterpreterException {
        this.ctrl.setProgram(new AssignmentStatement("a", new ConstantExpression(10)));
//        this.ctrl.stepOnce(this.ctrl.getCurrentProgramState());
//        Assert.assertEquals(10, Integer.toUnsignedLong(this.ctrl.getCurrentProgramState().getSymbolTable().get("a")));
//        Assert.assertNull(this.ctrl.getCurrentProgramState().getSymbolTable().get("b"));
    }

    @Test
    public void testToString() {
        Assert.assertEquals("a=10", new AssignmentStatement("a", new ConstantExpression(10)).toString());
    }
}