package com.natpryce.snodge.internal;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Arrays;
import java.util.Map;

@SuppressWarnings("ChainOfInstanceofChecks")
public class JsonPath implements Function<JsonElement, JsonElement> {
    public static final JsonPath root = new JsonPath(new Object[0]);

    private final Object[] path;

    private JsonPath(Object[] path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JsonPath that = (JsonPath) o;

        return Arrays.equals(this.path, that.path);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.path);
    }

    @Override
    public String toString() {
        return pathBitsToString(path, path.length);
    }

    public static JsonPath of(Object... path) {
        return new JsonPath(path);
    }

    public JsonPath extend(Object... morePath) {
        Object[] newPath = new Object[this.path.length + morePath.length];
        System.arraycopy(this.path, 0, newPath, 0, this.path.length);
        System.arraycopy(morePath, 0, newPath, this.path.length, morePath.length);
        return new JsonPath(newPath);
    }

    @Override
    public JsonElement apply(JsonElement json) {
        JsonElement result = json;

        for (int i = 0; i < path.length; i++) {
            result = applyPathElement(json, result, i);
        }

        return result;
    }

    private JsonElement applyPathElement(JsonElement root, JsonElement parent, int i) {
        Object pathBit = path[i];

        if (pathBit instanceof String) {
            String memberName = (String) pathBit;
            check(parent.isJsonObject(), "expected object", path, i, root);
            JsonObject object = parent.getAsJsonObject();
            check(object.has(memberName), "no such member", path, i, root);

            return object.get(memberName);
        } else if (pathBit instanceof Integer) {
            int index = (Integer) pathBit;
            check(parent.isJsonArray(), "expected array", path, i, root);
            JsonArray array = parent.getAsJsonArray();
            check(array.size() > index, "index out of bounds", path, i, root);

            return array.get(index);
        } else {
            throw new IllegalArgumentException("unexpected path element: " + pathBitsToString(path, i));
        }
    }

    public JsonElement map(JsonElement json, Function<? super JsonElement, ? extends JsonElement> f) {
        JsonElement[] parents = new JsonElement[path.length + 1];
        parents[0] = json;

        for (int i = 0; i < path.length; i++) {
            parents[i + 1] = applyPathElement(json, parents[i], i);
        }

        JsonElement replaced = f.apply(parents[path.length]);

        for (int i = path.length - 1; i >= 0; i--) {
            replaced = replaceElement(json, parents[i], i, replaced);
        }

        return replaced;
    }

    public JsonElement replace(JsonElement json, JsonElement replacement) {
        return map(json, Functions.constant(replacement));
    }

    private JsonElement replaceElement(JsonElement root, JsonElement parent, int i, JsonElement replacement) {
        Object pathBit = path[i];

        if (pathBit instanceof String) {
            String memberName = (String) pathBit;
            check(parent.isJsonObject(), "expected object", path, i, root);
            JsonObject original = parent.getAsJsonObject();
            check(original.has(memberName), "no such member", path, i, root);

            JsonObject replaced = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : original.entrySet())
                if (!entry.getKey().equals(memberName)) {
                    replaced.add(entry.getKey(), entry.getValue());
                }
            replaced.add(memberName, replacement);

            return replaced;
        } else if (pathBit instanceof Integer) {
            int index = (Integer) pathBit;
            check(parent.isJsonArray(), "expected array", path, i, root);
            JsonArray original = parent.getAsJsonArray();
            check(original.size() > index, "index out of bounds", path, i, root);

            JsonArray replaced = new JsonArray();
            for (int j = 0; j < i; j++) {
                replaced.add(original.get(j));
            }
            replaced.add(replacement);
            for (int j = i + 1; j < original.size(); j++) {
                replaced.add(original.get(j));
            }

            return replaced;
        } else {
            throw new IllegalArgumentException("unexpected path element: " + pathBitsToString(path, i));
        }
    }

    private static void check(boolean isOk, String what, Object[] pathBits, int badOne, JsonElement json) {
        if (!isOk) {
            throw new IllegalArgumentException(what + " at " + pathBitsToString(pathBits, badOne + 1) + " in " + json);
        }
    }

    private static String pathBitsToString(Object[] pathBits, int count) {
        return "/" + Joiner.on("/").join(Arrays.copyOf(pathBits, count));
    }
}
