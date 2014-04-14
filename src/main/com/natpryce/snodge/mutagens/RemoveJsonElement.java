package com.natpryce.snodge.mutagens;

import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.JsonPath;
import com.natpryce.snodge.Mutagen;

import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class RemoveJsonElement implements Mutagen {
    @Override
    public Stream<DocumentMutation> potentialMutations(JsonElement document, final JsonPath pathToElement, JsonElement elementToMutate) {
        if (pathToElement.isRoot()) {
            return Stream.empty();
        } else {
            return Stream.of(pathToElement.remove());
        }
    }
}
