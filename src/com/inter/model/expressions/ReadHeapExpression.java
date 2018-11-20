package com.inter.model.expressions;

import com.inter.exceptions.EvaluationException;
import com.inter.utils.adt.IDictionary;

public class ReadHeapExpression extends Expression {
    private String key;

    public ReadHeapExpression(String key) {
        this.key = key;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> table, IDictionary<Integer, Integer> heap) throws EvaluationException {
        int heap_address = new VariableExpression(this.key).evaluate(table, heap);

        Integer heap_value = heap.get(heap_address);
        if (heap_value == null)
            throw new EvaluationException(String.format("%s: failed, address %s was not alloc'd", this.toString(), heap_address));

        return heap_value;
    }

    @Override
    public String toString() {
        return String.format("readHeap(%s)", this.key);
    }
}
