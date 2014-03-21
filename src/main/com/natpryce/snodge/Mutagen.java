package com.natpryce.snodge;

import com.google.gson.JsonElement;
import com.natpryce.snodge.internal.JsonPath;

public interface Mutagen {
    Iterable<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate);
}
