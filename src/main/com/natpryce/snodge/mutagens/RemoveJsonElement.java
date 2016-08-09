package com.natpryce.snodge.mutagens;

import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.JsonPath;
import com.natpryce.snodge.Mutagen;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

public class RemoveJsonElement implements Mutagen {
    @Override
    public Sequence<DocumentMutation> potentialMutations(JsonElement document, final JsonPath pathToElement, JsonElement elementToMutate) {
        if (pathToElement.isRoot()) {
            return SequencesKt.emptySequence();
        } else {
            return SequencesKt.sequenceOf(pathToElement.remove());
        }
    }
}
