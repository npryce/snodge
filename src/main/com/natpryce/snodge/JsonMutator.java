package com.natpryce.snodge;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.natpryce.snodge.Mutagens.allMutagens;
import static com.natpryce.snodge.internal.JsonWalk.walk;
import static java.util.stream.Collectors.toList;

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
    public List<JsonElement> mutate(final JsonElement document, int mutationCount) {
        return mutations(document, mutationCount).stream()
                .map(mutation -> mutation.apply(document))
                .collect(toList());
    }

    private List<DocumentMutation> mutations(JsonElement document, int mutationCount) {
        List<DocumentMutation> selectedMutations = new ArrayList<>(mutationCount);
        AtomicInteger counter = new AtomicInteger(0);

        walk(document)
                .flatMap(path -> mutagens.potentialMutations(document, path, path.apply(document)))
                .sequential()
                .forEach(potentialMutation -> {
                    int count = counter.incrementAndGet();
                    if (count <= mutationCount) {
                        selectedMutations.add(potentialMutation);
                    } else {
                        int index = rng.nextInt(count);
                        if (index < selectedMutations.size()) {
                            selectedMutations.set(index, potentialMutation);
                        }
                    }
                });

        return selectedMutations;
    }

    public Mutator<String> forStrings() {
        Gson gson = new Gson();

        return (originalJsonString, mutationCount) -> {
            JsonElement originalJsonDocument = gson.fromJson(originalJsonString, JsonElement.class);
            return mutate(originalJsonDocument, mutationCount).stream()
                    .map(Object::toString)
                    .collect(toList());
        };
    }
}
