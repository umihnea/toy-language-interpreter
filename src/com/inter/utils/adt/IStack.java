package com.inter.utils.adt;

public interface IStack<T> {
    T pop();

    T top();

    void push(T e);

    boolean isEmpty();
}
