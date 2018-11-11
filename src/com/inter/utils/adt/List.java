package com.inter.utils.adt;

import com.inter.exceptions.CollectionException;

import java.util.LinkedList;

public class List<T> implements IList<T> {
    private java.util.List<T> collection;

    public List() {
        this.collection = new LinkedList<>();
    }

    @Override
    public void append(T e) {
        collection.add(e);
    }

    @Override
    public T get(int index) throws CollectionException {
        try {
            T e = collection.get(index);
            return e;
        } catch (IndexOutOfBoundsException obe) {
            throw new CollectionException(String.format("ADTs->List->get(): Non-existent index %d.", index));
        }
    }

    @Override
    public T removeAt(int index) throws CollectionException {
        if (index <= 0 || index > collection.size())
            throw new CollectionException(String.format("ADTs->List->removeAt(): Non-existent index %d.", index));
        return collection.remove(index);
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public String toString() {
        int index = 0;
        StringBuilder line = new StringBuilder();
        for (T e : collection) {
            if (index + 1 < collection.size())
                line.append(String.format("%s, ", e.toString()));
            else
                line.append(e.toString());
            index++;
        }
        return String.format("[%s]", line.toString());
    }
}
