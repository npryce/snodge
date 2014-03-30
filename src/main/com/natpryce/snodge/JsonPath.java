package com.natpryce.snodge;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.natpryce.snodge.internal.JsonFunctions;

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

    public int size() {
        return path.length;
    }

    public Object at(int n) {
        return path[(path.length + n)%path.length];
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
            result = applyPathElement(json, i, result);
        }

        return result;
    }

    private JsonElement applyPathElement(JsonElement root, int i, JsonElement parent) {
        Object pathBit = path[i];

        if (pathBit instanceof String) {
            String memberName = (String) pathBit;
            JsonObject object = jsonObjectWithProperty(root, i, parent, memberName);
            return object.get(memberName);
        } else if (pathBit instanceof Integer) {
            int index = (Integer) pathBit;
            JsonArray array = jsonArrayWithIndex(root, i, parent, index);
            return array.get(index);
        } else {
            throw new IllegalArgumentException("unexpected path element: " + pathBitsToString(path, i));
        }
    }

    public JsonElement map(JsonElement json, Function<? super JsonElement, ? extends JsonElement> f) {
        return map(json, path.length, f);
    }

    public DocumentMutation map(final Function<? super JsonElement, ? extends JsonElement> f) {
        return new DocumentMutation() {
            @Override
            public JsonElement apply(JsonElement json) {
                return map(json, f);
            }
        };
    }

    public JsonElement replace(JsonElement root, JsonElement replacement) {
        return map(root, Functions.constant(replacement));
    }

    private JsonElement replaceElement(JsonElement root, JsonElement parent, int i, JsonElement replacement) {
        Object pathBit = path[i];

        if (pathBit instanceof String) {
            String memberName = (String) pathBit;
            JsonObject original = jsonObjectWithProperty(root, i, parent, memberName);

            JsonObject replaced = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : original.entrySet()) {
                if (!entry.getKey().equals(memberName)) {
                    replaced.add(entry.getKey(), entry.getValue());
                }
            }
            replaced.add(memberName, replacement);

            return replaced;

        } else if (pathBit instanceof Integer) {
            int index = (Integer) pathBit;
            JsonArray original = jsonArrayWithIndex(root, i, parent, index);

            JsonArray replaced = new JsonArray();
            for (int j = 0; j < index; j++) {
                replaced.add(original.get(j));
            }
            replaced.add(replacement);
            for (int j = index + 1; j < original.size(); j++) {
                replaced.add(original.get(j));
            }

            return replaced;

        } else {
            throw new IllegalArgumentException("unexpected path element: " + pathBitsToString(path, i));
        }
    }

    public DocumentMutation remove() {
        return new DocumentMutation() {
            @Override
            public JsonElement apply(JsonElement input) {
                return remove(input);
            }
        };
    }

    public JsonElement remove(final JsonElement root) {
        final int lastIndex = path.length - 1;

        return map(root, lastIndex, new Function<JsonElement, JsonElement>() {
            @Override
            public JsonElement apply(JsonElement input) {
                return removeElement(root, lastIndex, input, path[lastIndex]);
            }
        });
    }

    private JsonElement removeElement(JsonElement root, int i, JsonElement parent, Object pathBit) {
        if (pathBit instanceof String) {
            String memberName = (String) pathBit;
            JsonObject original = jsonObjectWithProperty(root, i, parent, memberName);
            return JsonFunctions.removeObjectProperty(original, memberName);

        } else if (pathBit instanceof Integer) {
            int index = (Integer) pathBit;
            JsonArray original = jsonArrayWithIndex(root, i, parent, index);
            return JsonFunctions.removeArrayElement(original, index);

        } else {
            throw new IllegalArgumentException("unexpected path element: " + pathBitsToString(path, i));
        }
    }

    private JsonElement map(JsonElement json, int pathLength, Function<? super JsonElement, ? extends JsonElement> f) {
        JsonElement[] parents = new JsonElement[pathLength + 1];
        parents[0] = json;

        for (int i = 0; i < pathLength; i++) {
            parents[i + 1] = applyPathElement(json, i, parents[i]);
        }

        JsonElement replaced = f.apply(parents[pathLength]);

        for (int i = pathLength - 1; i >= 0; i--) {
            replaced = replaceElement(json, parents[i], i, replaced);
        }

        return replaced;
    }

    private JsonObject jsonObjectWithProperty(JsonElement root, int i, JsonElement parent, String memberName) {
        check(parent.isJsonObject(), "expected object", path, i, root);
        JsonObject original = parent.getAsJsonObject();
        check(original.has(memberName), "no such member", path, i, root);
        return original;
    }

    private JsonArray jsonArrayWithIndex(JsonElement root, int i, JsonElement parent, int index) {
        check(parent.isJsonArray(), "expected array", path, i, root);
        JsonArray array = parent.getAsJsonArray();
        check(array.size() > index, "index out of bounds", path, i, root);
        return array;
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
