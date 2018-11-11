package com.inter.utils.adt;

import com.inter.exceptions.CollectionException;
import org.junit.Test;

import static org.junit.Assert.*;

public class StackTest {

    @Test
    public void pushAndPop() {
        IStack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        assertEquals(Integer.valueOf(3), s.pop());
        assertEquals(Integer.valueOf(2), s.pop());
        assertEquals(Integer.valueOf(1), s.pop());
        assertTrue(s.isEmpty());
    }

    @Test
    public void top() {
        IStack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        assertEquals(Integer.valueOf(3), s.top());
        assertEquals(Integer.valueOf(3), s.pop());
        assertEquals(Integer.valueOf(2), s.top());
        assertEquals(Integer.valueOf(2), s.pop());
        assertEquals(Integer.valueOf(1), s.top());
        assertFalse(s.isEmpty());   // s = [1]
    }

    @Test(expected = CollectionException.class)
    public void tooMuchPop() {
        IStack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        s.pop();
        s.pop();
        s.pop();
        assertTrue(s.isEmpty());
        s.pop();    // throws
    }

    @Test
    public void testToString() {
        IStack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        assertEquals("[3, 2, 1]", s.toString());
    }
}