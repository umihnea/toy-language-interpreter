package com.inter.utils.adt;

import com.inter.exceptions.CollectionException;

import java.util.LinkedList;

public class MyStack<T> implements IMyStack<T> {

    private LinkedList<T> list;

    public MyStack() {
        this.list = new LinkedList<>();
    }

    @Override
    public T pop() throws CollectionException {
        if (isEmpty()) throw new CollectionException("MyStack.pop(): Stack is empty.");
        return list.removeFirst();
    }

    @Override
    public T top() {
        if (isEmpty()) throw new CollectionException("MyStack.top(): Stack is empty.");
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
}
