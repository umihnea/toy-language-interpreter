package com.inter.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FilePair {
    private final SimpleIntegerProperty key = new SimpleIntegerProperty(0);
    private final SimpleStringProperty value = new SimpleStringProperty("");

    public FilePair() {
        this(0, "");
    }

    public FilePair(Integer key, String value) {
        setKey(key);
        setValue(value);
    }

    public Integer getKey() {
        return key.get();
    }

    public void setKey(Integer newKey) {
        key.set(newKey);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String newValue) {
        value.set(newValue);
    }
}
