package com.natpryce.snodge.mutagens;

import com.google.common.base.Function;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.Mutagen;
import com.natpryce.snodge.internal.ElementAtPathMutation;
import com.natpryce.snodge.internal.JsonPath;

import java.util.Arrays;
import java.util.Collections;

public class AddArrayElement implements Mutagen, Function<JsonElement, JsonElement> {
    private final JsonElement newElement;

    public AddArrayElement(JsonElement newElement) {
        this.newElement = newElement;
    }

    @Override
    public Iterable<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate) {
        if (elementToMutate.isJsonArray()) {
            return Arrays.<DocumentMutation>asList(new ElementAtPathMutation(pathToElement, this));
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public JsonElement apply(JsonElement original) {
        JsonArray mutated = new JsonArray();
        mutated.addAll(original.getAsJsonArray());
        mutated.add(newElement);
        return mutated;
    }
}
