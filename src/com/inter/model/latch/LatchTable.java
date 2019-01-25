package com.inter.model.latch;

import java.util.*;

public class LatchTable implements ILatchTable {
    private Map<Integer, Integer> synchronizedHashMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void put(int key, int value) {
        synchronizedHashMap.put(key, value);
    }

    @Override
    public int get(int key) {
        return synchronizedHashMap.get(key);
    }

    @Override
    public boolean containsKey(int key) {
        return synchronizedHashMap.containsKey(key);
    }

    @Override
    public Collection<Integer> values() {
        return synchronizedHashMap.values();
    }

    @Override
    public Set<Map.Entry<Integer, Integer>> entrySet() {
        return synchronizedHashMap.entrySet();
    }

    @Override
    public int size() {
        return synchronizedHashMap.size();
    }

    @Override
    public String toString() {
        int index = 0;
        StringBuilder line = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : synchronizedHashMap.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();

            line.append(String.format("(%s: %s)", key, value));

            if (index + 1 < synchronizedHashMap.size()) line.append(", ");
            index++;
        }
        return "{" + line.toString() + "}";
    }
}
