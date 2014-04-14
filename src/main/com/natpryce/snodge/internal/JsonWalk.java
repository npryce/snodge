package com.natpryce.snodge.internal;

import com.google.gson.JsonElement;
import com.natpryce.snodge.JsonPath;

import java.util.Map;
import java.util.stream.Stream;

import static com.natpryce.snodge.internal.JsonFunctions.arrayEntries;
import static java.util.stream.Stream.concat;

public class JsonWalk {
    public static Stream<JsonPath> walk(JsonElement start) {
        return walk(start, JsonPath.root);
    }

    private static Stream<JsonPath> walk(JsonElement element, JsonPath elementPath) {
        return concat(Stream.of(elementPath), walkChildren(element, elementPath));
    }

    private static Stream<JsonPath> walkChildren(JsonElement element, JsonPath elementPath) {
        if (element.isJsonObject()) {
            return walkChildren(elementPath, element.getAsJsonObject().entrySet().stream());
        } else if (element.isJsonArray()) {
            return walkChildren(elementPath, arrayEntries(element.getAsJsonArray()));
        } else {
            return Stream.empty();
        }
    }

    private static <T> Stream<JsonPath> walkChildren(final JsonPath parentPath, Stream<Map.Entry<T, JsonElement>> children) {
        return children.flatMap(child -> walk(child.getValue(), parentPath.extend(child.getKey())));
    }
}
