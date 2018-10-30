package com.inter.utils.adt;

public interface IList<T> {
    void append(T e);

    T get(int index);

    T removeAt(int index);

    int size();
}
