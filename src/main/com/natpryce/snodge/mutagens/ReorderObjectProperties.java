package com.natpryce.snodge.mutagens;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.natpryce.snodge.DocumentMutation;
import com.natpryce.snodge.JsonPath;
import com.natpryce.snodge.Mutagen;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static kotlin.sequences.SequencesKt.emptySequence;
import static kotlin.sequences.SequencesKt.sequenceOf;

public class ReorderObjectProperties implements Mutagen {
    @Override
    public Sequence<DocumentMutation> potentialMutations(JsonElement document, JsonPath pathToElement, JsonElement elementToMutate) {
        if (elementToMutate.isJsonObject()) {
            return sequenceOf(pathToElement.map(this::mutate));
        }
        else {
            return emptySequence();
        }
    }

    public JsonElement mutate(JsonElement element) {
        List<Map.Entry<String, JsonElement>> objectProperties = new ArrayList<>(element.getAsJsonObject().entrySet());
        Collections.shuffle(objectProperties);

        JsonObject mutant = new JsonObject();
        for (Map.Entry<String, JsonElement> property : objectProperties) {
            mutant.add(property.getKey(), property.getValue());
        }
        return mutant;
    }
}
