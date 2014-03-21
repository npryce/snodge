package com.natpryce.snodge.mutagens;

import com.google.common.base.Function;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.Mutagen;
import com.natpryce.snodge.internal.ElementAtPathMutation;
import com.natpryce.snodge.internal.JsonPath;

import java.util.Collections;
import java.util.Set;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Sets.difference;
import static com.natpryce.snodge.internal.JsonFunctions.propertyNames;
import static java.util.Collections.singleton;

public class RemoveObjectProperty implements Mutagen {
    @Override
    public Iterable<DocumentMutation> potentialMutations(JsonElement document, final JsonPath pathToElement, JsonElement elementToMutate) {
        if (elementToMutate.isJsonObject()) {
            Set<String> indices = propertyNames(elementToMutate.getAsJsonObject());

            return transform(indices, new Function<String, DocumentMutation>() {
                @Override
                public DocumentMutation apply(String propertyName) {
                    return mutationFor(pathToElement, propertyName);
                }
            });
        } else {
            return Collections.emptyList();
        }
    }

    DocumentMutation mutationFor(final JsonPath pathToElement, final String propertyName) {
        return new ElementAtPathMutation(pathToElement, new Function<JsonElement, JsonElement>() {
            @Override
            public JsonElement apply(JsonElement input) {
                return removeObjectProperty(input.getAsJsonObject(), propertyName);
            }
        });
    }

    JsonElement removeObjectProperty(JsonObject original, String nameToRemove) {
        Set<String> propertiesToKeep = difference(propertyNames(original), singleton(nameToRemove));

        JsonObject mutant = new JsonObject();
        for (String propertyName : propertiesToKeep) {
            mutant.add(propertyName, original.get(propertyName));
        }

        return mutant;
    }
}
