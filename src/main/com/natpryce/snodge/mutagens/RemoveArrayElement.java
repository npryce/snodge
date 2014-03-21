package com.natpryce.snodge.mutagens;

import com.google.common.base.Function;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.Mutagen;
import com.natpryce.snodge.internal.ElementAtPathMutation;
import com.natpryce.snodge.internal.JsonFunctions;
import com.natpryce.snodge.internal.JsonPath;

import java.util.Collections;

import static com.google.common.collect.Iterables.transform;

public class RemoveArrayElement implements Mutagen {
    @Override
    public Iterable<DocumentMutation> potentialMutations(JsonElement document, final JsonPath pathToElement, JsonElement elementToMutate) {
        if (elementToMutate.isJsonArray()) {
            return transform(JsonFunctions.indices(elementToMutate.getAsJsonArray()), new Function<Integer, DocumentMutation>() {
                @Override
                public DocumentMutation apply(Integer index) {
                    return mutationFor(pathToElement, index);
                }
            });
        } else {
            return Collections.emptyList();
        }
    }


    DocumentMutation mutationFor(final JsonPath pathToElement, final int index) {
        return new ElementAtPathMutation(pathToElement, new Function<JsonElement, JsonElement>() {
            @Override
            public JsonElement apply(JsonElement input) {
                return removeArrayElement(input.getAsJsonArray(), index);
            }
        });
    }

    JsonElement removeArrayElement(JsonArray original, int index) {
        JsonArray mutant = new JsonArray();

        for (int i = 0; i < index; i++) {
            mutant.add(original.get(i));
        }
        for (int i = index + 1; i < original.size(); i++) {
            mutant.add(original.get(i));
        }

        return mutant;
    }
}
