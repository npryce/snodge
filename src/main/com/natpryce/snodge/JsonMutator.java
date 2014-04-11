package com.natpryce.snodge;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.natpryce.snodge.internal.JsonWalk;

import java.util.List;
import java.util.Random;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static com.natpryce.snodge.Mutagens.allMutagens;

public class JsonMutator implements Mutator<JsonElement> {
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

    @Override
    public Iterable<JsonElement> mutate(final JsonElement document, int mutationCount) {
        return transform(mutations(document, mutationCount), mutation -> mutation.apply(document));
    }

    private List<DocumentMutation> mutations(JsonElement document, int mutationCount) {
        List<DocumentMutation> selectedMutations = newArrayListWithCapacity(mutationCount);

        int count = 0;

        for (JsonPath path : JsonWalk.walk(document)) {
            for (DocumentMutation possibleMutation : mutagens.potentialMutations(document, path, path.apply(document))) {
                if (count < mutationCount) {
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

    public Mutator<String> forStrings() {
        final Gson gson = new Gson();

        return (original, mutationCount) ->
                transform(mutate(gson.fromJson(original, JsonElement.class), mutationCount), Object::toString);
    }
}
