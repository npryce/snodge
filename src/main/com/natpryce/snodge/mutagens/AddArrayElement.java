package com.natpryce.snodge.mutagens;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.JsonPath;
import com.natpryce.snodge.Mutagen;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class AddArrayElement implements Mutagen, Function1<JsonElement,JsonElement> {
    private final JsonElement newElement;

    public AddArrayElement(JsonElement newElement) {
        this.newElement = newElement;
    }

    @Override
    public Stream<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate) {
        if (elementToMutate instanceof JsonArray) {
            return Stream.of(pathToElement.map(this));
        } else {
            return Stream.empty();
        }
    }

    @Override
    public JsonElement invoke(JsonElement original) {
        JsonArray mutant = new JsonArray();
        mutant.addAll(original.getAsJsonArray());
        mutant.add(newElement);
        return mutant;
    }
}
