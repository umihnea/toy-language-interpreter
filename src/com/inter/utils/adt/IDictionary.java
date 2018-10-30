package com.inter.utils.adt;

public interface IDictionary<K, V> {
    void put(K key, V e);

    V get(K key);

    V remove(K key);
}
