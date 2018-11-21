package com.inter.utils;

import com.inter.utils.adt.IDictionary;

import java.util.Map;
import java.util.stream.Collector;

public interface IDictionaryCollector<K, V> extends Collector<Map.Entry<K, V>, IDictionary<K, V>, IDictionary<K, V>> {

}
