package com.inter.utils.adt;

import java.util.Collection;

public interface IDictionary<K, V> {
    void put(K key, V e);

    V get(K key);

    V remove(K key);

    Collection<V> values();

    int size();
}
