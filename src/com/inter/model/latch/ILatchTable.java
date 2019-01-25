package com.inter.model.latch;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface ILatchTable {
    void put(int key, int value);

    int get(int key);

    boolean containsKey(int key);

    Collection<Integer> values();

    Set<Map.Entry<Integer, Integer>> entrySet();

    int size();
}
