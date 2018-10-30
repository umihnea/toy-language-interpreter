package com.inter.utils.adt;

public interface IMyStack<T> {
    T pop();

    T top();

    void push(T e);

    boolean isEmpty();
}
