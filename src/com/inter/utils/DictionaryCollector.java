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

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

public class DictionaryCollector<K, V> implements IDictionaryCollector<K, V> {
    @Override
    public Supplier<IDictionary<K, V>> supplier() {
        return Dictionary::new;
    }

    @Override
    public BiConsumer<IDictionary<K, V>, Map.Entry<K, V>> accumulator() {
        return (dict, entry) -> dict.put(entry.getKey(), entry.getValue());
    }

    @Override
    public BinaryOperator<IDictionary<K, V>> combiner() {
        /* Combiner defines what to be done if two partial results are provided. */
        return null;
    }

    @Override
    public Function<IDictionary<K, V>, IDictionary<K, V>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(IDENTITY_FINISH);
    }
}
