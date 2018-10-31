package com.inter.utils.adt;

import java.util.HashMap;
import java.util.Map;

public class Dictionary<K, V> implements IDictionary<K, V> {
    private Map<K, V> map;

    public Dictionary() {
        this.map = new HashMap<>();
    }

    @Override
    public void put(K key, V e) {
        map.put(key, e);
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public V remove(K key) {
        return map.remove(key);
    }

    @Override
    public String toString() {
        String hashCode = Integer.toString(System.identityHashCode(this));
        StringBuilder line = new StringBuilder();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            line.append(key).append(" => ").append(value).append(",\n");
        }
        return "Dict@" + hashCode + ": {" + line.toString() + "};\n";
    }
}
