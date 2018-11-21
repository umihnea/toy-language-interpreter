package com.inter.utils.adt;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDictionary<K, V> {
    void put(K key, V e);

    V get(K key);

    V remove(K key);

    Collection<V> values();

    Set<Map.Entry<K, V>> entrySet();

    int size();
}
