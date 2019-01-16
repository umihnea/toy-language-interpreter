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
        if (isEmpty()) throw new CollectionException("ADTs->Stack->pop(): Stack is empty.");
        return list.removeFirst();
    }

    @Override
    public T top() {
        if (isEmpty()) throw new CollectionException("ADTs->Stack->top(): Stack is empty.");
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
    public LinkedList<T> getContainer() {
        return list;
    }

    @Override
    public String toString() {
        int index = 0;
        StringBuilder line = new StringBuilder();
        for (T e : list) {
            if (index + 1 < list.size())
                line.append(String.format("%s, ", e.toString()));
            else
                line.append(e.toString());
            index++;
        }
        return String.format("[%s]", line);
    }
}
