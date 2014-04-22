package com.natpryce.snodge;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.natpryce.snodge.mutagens.AddArrayElement;
import com.natpryce.snodge.mutagens.AddObjectProperty;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.natpryce.snodge.JsonBuilders.*;
import static com.natpryce.snodge.Mutagens.combine;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

public class JsonMutatorTest {
    Random rng = new Random();

    JsonMutator mutator = new JsonMutator(rng,
            combine(new AddObjectProperty(JsonNull.INSTANCE),
                    new AddArrayElement(JsonNull.INSTANCE)));

    @Test
    public void canAddNullObjectProperty() throws Exception {
        JsonElement doc = object(
                withField("alice", 1),
                withField("bob", 2));

        List<JsonElement> mutations = mutator.mutate(doc, 1).collect(toList());

        assertThat("should only be one mutation", mutations.size(), equalTo(1));

        assertThat(mutations.get(0), equalTo((JsonElement) object(
                withField("alice", 1),
                withField("bob", 2),
                withNullField("x"))));
    }

    @Test
    public void canAddNullArrayProperty() throws Exception {
        JsonElement doc = list(1, 2, 3);

        Optional<JsonElement> mutations = mutator.mutate(doc, 1).findAny();

        assertThat("should be one mutation", mutations.isPresent(), equalTo(true));

        assertThat(mutations.get(), equalTo((JsonElement) list(1, 2, 3, null)));
    }

    @Test
    public void canReturnMultipleMutations() throws Exception {
        JsonElement doc = object(
                withField("num", 1),
                withField("list", list(1, 2, 3)));

        List<JsonElement> mutatedDocs = mutator.mutate(doc, 2).collect(toList());

        assertThat("number of mutations", mutatedDocs.size(), equalTo(2));

        assertTrue(mutatedDocs.contains(object(
                withField("num", 1),
                withField("list", list(1, 2, 3)),
                withNullField("x"))));

        assertTrue(mutatedDocs.contains(object(
                withField("num", 1),
                withField("list", list(1, 2, 3, null)))));
    }

    @Test
    public void returnsARandomSampleOfAllPossibleMutations() {
        JsonElement doc = list(list(1, 2), list(list(3, 4), list(5, 6, list(7, 8)), list(9, 10)), list(11, 12));

        rng.setSeed(0);
        List<JsonElement> mutatedDocs = mutator.mutate(doc, 2).collect(toList());

        assertThat("number of mutations", mutatedDocs.size(), equalTo(2));

        rng.setSeed(99);
        assertThat(mutator.mutate(doc, 2), not(equalTo(mutator.mutate(doc, 2))));
    }

    @Test
    public void willNotReturnMoreMutationsThanCanBeGeneratedByTheMutagens() throws Exception {
        JsonElement doc = list(list(1, 2), list(3, 4));

        assertThat("number of mutations", mutator.mutate(doc, 10).count(), equalTo(3L));
    }

    @Test
    public void canMutateJsonText() {
        Gson gson = new Gson();
        String original = gson.toJson(object(
                withField("num", 1),
                withField("list", list(1, 2, 3))));

        String mutated = mutator.forStrings().mutate(original, 1).findAny().get();

        assertThat(mutated, not(equalTo(original)));

        // Does not blow up
        gson.fromJson(mutated, JsonElement.class);
    }
}
