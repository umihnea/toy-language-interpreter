package com.inter.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SymbolPair {
    private final SimpleStringProperty key = new SimpleStringProperty("");
    private final SimpleIntegerProperty value = new SimpleIntegerProperty(0);

    public SymbolPair() {
        this("", 0);
    }

    public SymbolPair(String key, Integer value) {
        setKey(key);
        setValue(value);
    }

    public String getKey() {
        return key.get();
    }

    public void setKey(String newKey) {
        key.set(newKey);
    }

    public Integer getValue() {
        return value.get();
    }

    public void setValue(Integer newValue) {
        value.set(newValue);
    }
}
