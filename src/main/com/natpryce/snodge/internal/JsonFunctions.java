package com.natpryce.snodge.internal;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JsonFunctions {
    public static <K,V> Map.Entry<K,V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<K, V>(key, value);
    }

    public static IntStream indices(JsonArray array) {
        return IntStream.range(0, array.size());
    }

    public static Stream<Map.Entry<Integer, JsonElement>> arrayEntries(final JsonArray array) {
        return indices(array).mapToObj(index -> entry(index, array.get(index)));
    }

    public static JsonElement removeArrayElement(JsonArray original, int indexToRemove) {
        JsonArray mutant = new JsonArray();
        indices(original)
                .filter(i -> i != indexToRemove)
                .forEach(i -> mutant.add(original.get(i)));
        return mutant;
    }

    public static JsonElement removeObjectProperty(JsonObject original, String nameToRemove) {
        JsonObject mutant = new JsonObject();

        original.entrySet().stream()
                .filter(e -> !e.getKey().equals(nameToRemove))
                .forEach(e -> mutant.add(e.getKey(), e.getValue()));

        return mutant;
    }
}
