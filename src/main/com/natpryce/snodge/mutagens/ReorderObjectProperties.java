package com.natpryce.snodge.mutagens;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.JsonPath;
import com.natpryce.snodge.Mutagen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class ReorderObjectProperties implements Mutagen, Function<JsonElement,JsonElement> {
    @Override
    public Iterable<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate) {
        if (elementToMutate.isJsonObject()) {
            return singletonList(pathToElement.map(this));
        }
        else {
            return emptyList();
        }
    }

    @Override
    public JsonElement apply(JsonElement input) {
        ArrayList<Map.Entry<String, JsonElement>> properties = newArrayList(input.getAsJsonObject().entrySet());
        Collections.shuffle(properties);

        JsonObject mutant = new JsonObject();
        for (Map.Entry<String, JsonElement> property : properties) {
            mutant.add(property.getKey(), property.getValue());
        }
        return mutant;
    }
}
