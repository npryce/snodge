package com.natpryce.snodge.internal;

import com.google.common.base.Functions;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.natpryce.snodge.JsonPath;
import org.junit.Test;

import java.util.Set;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Sets.newTreeSet;
import static com.natpryce.snodge.JsonBuilders.*;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class JsonWalkTest {
    @Test
    public void returnsObjectProperties() throws Exception {
        assertWalk(object(withField("a", 1), withField("b", 2)),
                JsonPath.root,
                JsonPath.of("a"),
                JsonPath.of("b"));
    }

    @Test
    public void returnsNestedObjectProperties() throws Exception {
        assertWalk(object(withField("a", 1), withField("b", object(withField("c", 2)))),
                JsonPath.root,
                JsonPath.of("a"),
                JsonPath.of("b"),
                JsonPath.of("b", "c"));
    }

    @Test
    public void returnsArrayElements() throws Exception {
        assertWalk(list("a", "b"),
                JsonPath.root,
                JsonPath.of(0),
                JsonPath.of(1));
    }

    @Test
    public void returnsNestedArrayElements() throws Exception {
        assertWalk(list(list(1, 2), list(3, 4)),
                JsonPath.root,
                JsonPath.of(0),
                JsonPath.of(0, 0),
                JsonPath.of(0, 1),
                JsonPath.of(1),
                JsonPath.of(1, 0),
                JsonPath.of(1, 1));
    }

    @Test
    public void bigHairyJsonExample() throws Exception {
        JsonElement json = object(
                withField("a", object(
                        withField("b", list(1, 2, 3)))),
                withField("c", list(
                        object(withField("d", 1)),
                        object(withField("d", 2)))));

        assertWalk(json,
                JsonPath.root,
                JsonPath.of("a"),
                JsonPath.of("a", "b"),
                JsonPath.of("a", "b", 0),
                JsonPath.of("a", "b", 1),
                JsonPath.of("a", "b", 2),
                JsonPath.of("c"),
                JsonPath.of("c", 0),
                JsonPath.of("c", 0, "d"),
                JsonPath.of("c", 1),
                JsonPath.of("c", 1, "d"));
    }

    @Test
    public void returnsRootForJsonPrimitive() throws Exception {
        assertWalk(new JsonPrimitive("bob"),
                JsonPath.root);
    }

    @Test
    public void returnsRootForJsonNull() throws Exception {
        assertWalk(JsonNull.INSTANCE,
                JsonPath.root);
    }

    @Test
    public void returnsRootForEmptyObject() throws Exception {
        assertWalk(object(),
                JsonPath.root);
    }

    @Test
    public void returnsRootForEmptyArray() throws Exception {
        assertWalk(list(),
                JsonPath.root);
    }

    private void assertWalk(JsonElement json, JsonPath... expected) {
        Set<String> actualAsStrings = newTreeSet(transform(JsonWalk.walk(json), Functions.toStringFunction()));
        Set<String> expectedAsStrings = newTreeSet(transform(asList(expected), Functions.toStringFunction()));

        assertThat(actualAsStrings, equalTo(expectedAsStrings));
    }
}
