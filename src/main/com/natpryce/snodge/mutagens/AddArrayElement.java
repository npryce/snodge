package com.natpryce.snodge.mutagens;

import com.google.common.base.Function;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.Mutagen;
import com.natpryce.snodge.internal.JsonPath;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class AddArrayElement implements Mutagen, Function<JsonElement, JsonElement> {
    private final JsonElement newElement;

    public AddArrayElement(JsonElement newElement) {
        this.newElement = newElement;
    }

    @Override
    public Iterable<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate) {
        if (elementToMutate.isJsonArray()) {
            return singletonList(pathToElement.map(this));
        } else {
            return emptyList();
        }
    }

    @Override
    public JsonElement apply(JsonElement original) {
        JsonArray mutant = new JsonArray();
        mutant.addAll(original.getAsJsonArray());
        mutant.add(newElement);
        return mutant;
    }
}
