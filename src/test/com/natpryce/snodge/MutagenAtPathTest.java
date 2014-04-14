package com.natpryce.snodge;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.natpryce.snodge.mutagens.ReplaceJsonElement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.natpryce.snodge.JsonBuilders.object;
import static com.natpryce.snodge.JsonBuilders.withField;
import static com.natpryce.snodge.Mutagens.atPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class MutagenAtPathTest {
    @Test
    public void canLimitMutagenToPath() throws Exception {
        JsonMutator mutator = new JsonMutator(
                atPath(JsonPath.of("a", "b"), new ReplaceJsonElement(new JsonPrimitive("XXX"))));

        JsonElement doc = object(
                withField("a", object(
                        withField("b", 1),
                        withField("c", 2))),
                withField("d", object(
                        withField("b", 1),
                        withField("c", 2)))
        );

        List<JsonElement> mutations = new ArrayList<>(mutator.mutate(doc, 1));

        assertThat(mutations.get(0), equalTo((JsonElement) object(
                withField("a", object(
                        withField("b", "XXX"),
                        withField("c", 2))),
                withField("d", object(
                        withField("b", 1),
                        withField("c", 2)))
        )));
    }

    @Test
    public void canLimitMutagenToPathsByPredicate() throws Exception {
        JsonMutator mutator = new JsonMutator(
                atPath(JsonPath.functions.endsWith("b"), new ReplaceJsonElement(new JsonPrimitive("XXX"))));

        JsonElement doc = object(
                withField("a", object(
                        withField("b", 1),
                        withField("c", 2))),
                withField("d", object(
                        withField("b", 1),
                        withField("c", 2)))
        );

        List<JsonElement> mutations = new ArrayList<>(mutator.mutate(doc, 2));

        assertTrue("a.b mutated", mutations.contains(
                object(
                        withField("a", object(
                                withField("b", "XXX"),
                                withField("c", 2))),
                        withField("d", object(
                                withField("b", 1),
                                withField("c", 2)))
                )
        ));

        assertTrue("d.b mutated", mutations.contains(
                object(
                        withField("a", object(
                                withField("b", 1),
                                withField("c", 2))),
                        withField("d", object(
                                withField("b", "XXX"),
                                withField("c", 2)))
                )
        ));
    }
}
