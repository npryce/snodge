package com.natpryce.snodge;

import com.google.common.base.Function;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.natpryce.snodge.internal.JsonPath;
import com.natpryce.snodge.internal.JsonWalk;

import java.util.List;
import java.util.Random;

import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static com.natpryce.snodge.Mutagens.allMutagens;

public class JsonMutator {
    private final Random rng;
    private final Mutagen mutagens;

    public JsonMutator() {
        this(allMutagens());
    }

    public JsonMutator(Mutagen mutagen) {
        this(new Random(), mutagen);
    }

    public JsonMutator(Random rng, Mutagen mutagen) {
        this.rng = rng;
        this.mutagens = mutagen;
    }

    public Iterable<String> mutate(String json, int sampleSize) {
        return transform(mutate((new Gson()).fromJson(json, JsonElement.class), sampleSize), toStringFunction());
    }

    public Iterable<JsonElement> mutate(final JsonElement document, int sampleSize) {
        return transform(mutations(document, sampleSize), new Function<DocumentMutation, JsonElement>() {
            @Override
            public JsonElement apply(DocumentMutation mutation) {
                return mutation.apply(document);
            }
        });
    }

    private List<DocumentMutation> mutations(JsonElement document, int sampleSize) {
        List<DocumentMutation> selectedMutations = newArrayListWithCapacity(sampleSize);

        int count = 0;

        for (JsonPath path : JsonWalk.walk(document)) {
            for (DocumentMutation possibleMutation : mutagens.potentialMutations(document, path, path.apply(document))) {
                if (count < sampleSize) {
                    selectedMutations.add(possibleMutation);
                } else {
                    int index = rng.nextInt(count);
                    if (index < selectedMutations.size()) {
                        selectedMutations.set(index, possibleMutation);
                    }
                }

                count++;
            }
        }

        return selectedMutations;
    }
}
