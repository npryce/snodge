package com.natpryce.snodge.mutagens;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.JsonPath;
import com.natpryce.snodge.Mutagen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class ReorderObjectProperties implements Mutagen, Function<JsonElement,JsonElement> {
    @Override
    public Stream<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate) {
        if (elementToMutate.isJsonObject()) {
            return Stream.of(pathToElement.map(this));
        }
        else {
            return Stream.empty();
        }
    }

    @Override
    public JsonElement apply(JsonElement element) {
        List<Map.Entry<String, JsonElement>> objectProperties = new ArrayList<>(element.getAsJsonObject().entrySet());
        Collections.shuffle(objectProperties);

        JsonObject mutant = new JsonObject();
        for (Map.Entry<String, JsonElement> property : objectProperties) {
            mutant.add(property.getKey(), property.getValue());
        }
        return mutant;
    }
}
