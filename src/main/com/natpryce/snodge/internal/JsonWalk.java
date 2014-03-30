package com.natpryce.snodge.internal;

import com.google.common.base.Function;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.natpryce.snodge.JsonPath;

import java.util.Collections;
import java.util.Map;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static com.natpryce.snodge.internal.JsonFunctions.arrayEntrySet;
import static java.util.Collections.singleton;

public class JsonWalk {
    public static Iterable<JsonPath> walk(JsonElement start) {
        return walk(start, JsonPath.root);
    }

    private static Iterable<JsonPath> walk(JsonElement element, JsonPath elementPath) {
        return concat(singleton(elementPath), walkChildren(element, elementPath));
    }

    private static Iterable<JsonPath> walkChildren(JsonElement element, JsonPath elementPath) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            return walkChildren(elementPath, object.entrySet());
        } else if (element.isJsonArray()) {
            final JsonArray array = element.getAsJsonArray();
            return walkChildren(elementPath, arrayEntrySet(array));
        } else {
            return Collections.emptyList();
        }
    }

    private static <T> Iterable<JsonPath> walkChildren(final JsonPath parentPath, Iterable<Map.Entry<T, JsonElement>> children) {
        return concat(transform(children, new Function<Map.Entry<T, JsonElement>, Iterable<JsonPath>>() {
            @Override
            public Iterable<JsonPath> apply(Map.Entry<T, JsonElement> child) {
                return walk(child.getValue(), parentPath.extend(child.getKey()));
            }
        }));
    }
}
