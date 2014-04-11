package com.natpryce.snodge.internal;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.DiscreteDomain.integers;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Sets.difference;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static java.util.Collections.singleton;

public class JsonFunctions {
    public static Set<Integer> indices(JsonArray array) {
        return ContiguousSet.create(Range.closedOpen(0, array.size()), integers());
    }

    public static Set<String> propertyNames(JsonObject object) {
        //noinspection Convert2MethodRef
        return newLinkedHashSet(transform(object.entrySet(), entry -> entry.getKey()));
    }

    public static Iterable<Map.Entry<Integer, JsonElement>> arrayEntrySet(final JsonArray array) {
        return transform(indices(array), index -> Maps.immutableEntry(index, array.get(index)));
    }

    public static JsonElement removeArrayElement(JsonArray original, int index) {
        JsonArray mutant = new JsonArray();

        for (int i = 0; i < index; i++) {
            mutant.add(original.get(i));
        }
        for (int i = index + 1; i < original.size(); i++) {
            mutant.add(original.get(i));
        }

        return mutant;
    }

    public static JsonElement removeObjectProperty(JsonObject original, String nameToRemove) {
        Set<String> propertiesToKeep = difference(propertyNames(original), singleton(nameToRemove));

        JsonObject mutant = new JsonObject();
        for (String propertyName : propertiesToKeep) {
            mutant.add(propertyName, original.get(propertyName));
        }

        return mutant;
    }
}
