package com.natpryce.snodge.mutagens;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.JsonPath;
import com.natpryce.snodge.Mutagen;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class AddArrayElement implements Mutagen, Function<JsonElement, JsonElement> {
    private final JsonElement newElement;

    public AddArrayElement(JsonElement newElement) {
        this.newElement = newElement;
    }

    @Override
    public Stream<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate) {
        if (elementToMutate.isJsonArray()) {
            return Stream.of(pathToElement.map(this));
        } else {
            return Stream.empty();
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
