package com.natpryce.snodge.mutagens;

import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.Mutagen;
import com.natpryce.snodge.internal.ElementAtPathMutation;
import com.natpryce.snodge.internal.JsonPath;

import java.util.Arrays;

import static com.google.common.base.Functions.constant;

public class ReplaceJsonElement implements Mutagen {
    private final JsonElement replacement;

    public ReplaceJsonElement(JsonElement replacement) {
        this.replacement = replacement;
    }

    @Override
    public Iterable<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate) {
        return Arrays.<DocumentMutation>asList(new ElementAtPathMutation(pathToElement, constant(replacement)));
    }
}
