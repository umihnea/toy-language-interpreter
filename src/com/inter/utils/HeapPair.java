package com.inter.utils;

import javafx.beans.property.SimpleIntegerProperty;

public class HeapPair {
    private final SimpleIntegerProperty key = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty value = new SimpleIntegerProperty(0);

    public HeapPair() {
        this(0, 0);
    }

    public HeapPair(Integer key, Integer value) {
        setKey(key);
        setValue(value);
    }

    public Integer getKey() {
        return key.get();
    }

    public void setKey(Integer newKey) {
        key.set(newKey);
    }

    public Integer getValue() {
        return value.get();
    }

    public void setValue(Integer newValue) {
        value.set(newValue);
    }
}
