package com.inter.utils.adt;

import com.inter.exceptions.CollectionException;

import java.util.Collection;
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
    public V get(K key) throws CollectionException {
        V value = map.get(key);
        if (value == null)
            throw new CollectionException(String.format("Trying to get undefined key \"%s\".", key));
        return value;
    }

    @Override
    public V remove(K key) throws CollectionException {
        V removed = map.remove(key);
        if (removed == null)
            throw new CollectionException(String.format("Trying to remove undefined key \"%s\".", key));
        return removed;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public String toString() {
        int index = 0;
        StringBuilder line = new StringBuilder();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();

            line.append(String.format("(%s: %s)", key, value));

            if (index + 1 < map.size()) line.append(", ");
            index++;
        }
        return "{" + line.toString() + "}";
    }
}
