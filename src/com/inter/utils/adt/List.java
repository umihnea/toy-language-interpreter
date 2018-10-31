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
        String hashCode = Integer.toString(System.identityHashCode(this));
        StringBuilder line = new StringBuilder();
        for (T e : collection)
            line.append("\t\t").append(e).append(",\n");
        return "List@" + hashCode + " = {\n" + line.toString() + "\t}\n";
    }
}
