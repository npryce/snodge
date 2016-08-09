package com.natpryce.snodge.mutagens;

import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.JsonPath;
import com.natpryce.snodge.Mutagen;
import kotlin.sequences.Sequence;

import static kotlin.sequences.SequencesKt.sequenceOf;

public class ReplaceJsonElement implements Mutagen {
    private final JsonElement replacement;

    public ReplaceJsonElement(JsonElement replacement) {
        this.replacement = replacement;
    }

    @Override
    public Sequence<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate) {
        return sequenceOf(pathToElement.map(e -> replacement));
    }
}
