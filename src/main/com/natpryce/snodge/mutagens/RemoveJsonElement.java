package com.natpryce.snodge.mutagens;

import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.JsonPath;
import com.natpryce.snodge.Mutagen;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class RemoveJsonElement implements Mutagen {
    @Override
    public Iterable<DocumentMutation> potentialMutations(JsonElement document, final JsonPath pathToElement, JsonElement elementToMutate) {
        if (pathToElement.isRoot()) {
            return emptyList();
        } else {
            return singletonList(pathToElement.remove());
        }
    }
}
