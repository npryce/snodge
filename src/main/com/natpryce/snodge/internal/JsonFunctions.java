package com.natpryce.snodge.internal;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Ranges;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.DiscreteDomains.integers;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Sets.newLinkedHashSet;

public class JsonFunctions {
    public static Set<Integer> indices(JsonArray array) {
        return Ranges.closedOpen(0, array.size()).asSet(integers());
    }

    public static Set<String> propertyNames(JsonObject object) {
        return newLinkedHashSet(transform(object.entrySet(), JsonFunctions.<String, JsonElement>toKey()));
    }

    public static Function<JsonObject, Set<String>> propertyNames = new Function<JsonObject, Set<String>>() {
        @Override
        public Set<String> apply(JsonObject input) {
            return propertyNames(input);
        }
    };

    private static <K, V> Function<Map.Entry<K, V>, K> toKey() {
        return new Function<Map.Entry<K, V>, K>() {
            @Override
            public K apply(Map.Entry<K, V> entry) {
                return entry.getKey();
            }
        };
    }

    public static Iterable<Map.Entry<Integer, JsonElement>> arrayEntrySet(final JsonArray array) {
        return transform(indices(array), new Function<Integer, Map.Entry<Integer, JsonElement>>() {
            @Override
            public Map.Entry<Integer, JsonElement> apply(Integer index) {
                return Maps.immutableEntry(index, array.get(index));
            }
        });
    }
}
