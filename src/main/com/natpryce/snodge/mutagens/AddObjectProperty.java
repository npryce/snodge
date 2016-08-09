package com.natpryce.snodge.mutagens;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.JsonPath;
import com.natpryce.snodge.Mutagen;
import kotlin.sequences.Sequence;

import java.util.Map;
import java.util.Set;

import static kotlin.sequences.SequencesKt.emptySequence;
import static kotlin.sequences.SequencesKt.sequenceOf;

public class AddObjectProperty implements Mutagen {
    private final JsonElement newElement;

    public AddObjectProperty(JsonElement newElement) {
        this.newElement = newElement;
    }

    @Override
    public Sequence<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate) {
        if (elementToMutate.isJsonObject()) {
            return sequenceOf(pathToElement.map(this::mutate));
        } else {
            return emptySequence();
        }
    }

    private JsonElement mutate(JsonElement original) {
        JsonObject mutated = new JsonObject();
        Set<Map.Entry<String, JsonElement>> entries = original.getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            mutated.add(entry.getKey(), entry.getValue());
        }
        mutated.add(newProperty(mutated), newElement);
        return mutated;
    }

    private String newProperty(JsonObject thingy) {
        String newPropertyName = "x";
        while (thingy.has(newPropertyName)) {
            newPropertyName += "x";
        }
        return newPropertyName;
    }
}
