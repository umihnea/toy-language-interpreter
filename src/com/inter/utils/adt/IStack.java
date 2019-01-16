package com.inter.utils.adt;

import java.util.LinkedList;

public interface IStack<T> {
    T pop();

    T top();

    void push(T e);

    boolean isEmpty();

    LinkedList<T> getContainer();
}
