package com.inter.utils.adt;

import com.inter.exceptions.CollectionException;
import org.junit.Assert;
import org.junit.Test;

public class DictionaryTest {

    @Test
    public void put() {
        IDictionary<Integer, String> d = new Dictionary<>();
        d.put(0, "A1");
        d.put(0, "B2");     // updates key 0
        Assert.assertEquals("B2", d.get(0));
    }

    @Test
    public void get() {
        IDictionary<Integer, String> d = new Dictionary<>();
        d.put(1, "B2");
        Assert.assertEquals("B2", d.get(1));
    }

    @Test(expected = CollectionException.class)
    public void getNonExistent() {
        IDictionary<Integer, String> d = new Dictionary<>();
        d.put(0, "zero");
        d.get(1);
    }

    @Test
    public void remove() {
        IDictionary<Integer, String> d = new Dictionary<>();
        Assert.assertEquals(0, d.size());
        d.put(0, "A1");
        Assert.assertEquals(1, d.size());
        Assert.assertEquals("A1", d.get(0));
        d.remove(0);
    }

    @Test(expected = CollectionException.class)
    public void removeNonExistent() {
        IDictionary<Integer, String> d = new Dictionary<>();
        d.put(1, "B2");
        Assert.assertEquals("B2", d.get(1));
        Assert.assertEquals(1, d.size());
        d.remove(0);
    }

    @Test
    public void size() {
        IDictionary<Integer, String> d = new Dictionary<>();
        d.put(0, "A");
        d.put(0, "X");
        d.put(1, "B");
        d.put(2, "C");
        d.put(3, "D");
        d.put(2, "Y");
        Assert.assertEquals(4, d.size());
    }

    @Test
    public void testToString() {
        IDictionary<Integer, String> d = new Dictionary<>();
        d.put(0, "A");
        d.put(0, "X");
        d.put(1, "B");
        d.put(2, "C");
        d.put(3, "D");
        d.put(2, "Y");
        String expected = "{(0: X), (1: B), (2: Y), (3: D)}";
        Assert.assertEquals(expected, d.toString());
    }
}