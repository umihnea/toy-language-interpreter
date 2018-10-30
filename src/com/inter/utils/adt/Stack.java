package com.inter.utils.adt;

import com.inter.exceptions.CollectionException;

import java.util.LinkedList;

public class Stack<T> implements IStack<T> {

    private LinkedList<T> list;

    public Stack() {
        this.list = new LinkedList<>();
    }

    @Override
    public T pop() throws CollectionException {
        if (isEmpty()) throw new CollectionException("Stack.pop(): Stack is empty.");
        return list.removeFirst();
    }

    @Override
    public T top() {
        if (isEmpty()) throw new CollectionException("Stack.top(): Stack is empty.");
        return list.peekFirst();
    }

    @Override
    public void push(T e) {
        list.addFirst(e);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        String hashCode = Integer.toString(System.identityHashCode(this));
        StringBuilder line = new StringBuilder();
        for (T e : list)
            line.append(e.toString()).append(",\n");
        return "Stack#" + hashCode + ": [ " + line.toString() + "];";
    }
}
