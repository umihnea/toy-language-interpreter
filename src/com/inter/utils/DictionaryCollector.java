package com.inter.utils;

import com.inter.utils.adt.Dictionary;
import com.inter.utils.adt.IDictionary;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

public class DictionaryCollector implements Collector<Map.Entry<Integer, Integer>, IDictionary<Integer, Integer>, IDictionary<Integer, Integer>> {
    @Override
    public Supplier<IDictionary<Integer, Integer>> supplier() {
        return Dictionary::new;
    }

    @Override
    public BiConsumer<IDictionary<Integer, Integer>, Map.Entry<Integer, Integer>> accumulator() {
        return (dict, entry) -> dict.put(entry.getKey(), entry.getValue());
    }

    @Override
    public BinaryOperator<IDictionary<Integer, Integer>> combiner() {
        /* Combiner defines what to be done if two partial results are provided. */
        return null;
    }

    @Override
    public Function<IDictionary<Integer, Integer>, IDictionary<Integer, Integer>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(IDENTITY_FINISH);
    }
}
