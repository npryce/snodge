package com.natpryce.snodge;

import com.google.common.base.Function;
import com.google.gson.*;
import com.natpryce.snodge.mutagens.*;

import java.util.List;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static java.util.Arrays.asList;

/**
 * Functions that apply to, or provide, {@link com.natpryce.snodge.Mutagen}s.
 */
public class Mutagens {
    private static final List<JsonElement> exampleElements = asList(
            JsonNull.INSTANCE,
            new JsonPrimitive(true),
            new JsonPrimitive(false),
            new JsonPrimitive(99),
            new JsonPrimitive(-99),
            new JsonPrimitive("a string"),
            new JsonArray(),
            new JsonObject());

    /**
     * Combine multiple component Mutagens into a single Mutagen that generates all the mutations of the components.
     * @param mutagens the Mutagens to combine
     * @return the combination Mutagen
     */
    public static Mutagen combine(final Mutagen... mutagens) {
        return combine(asList(mutagens));
    }

    /**
     * Combine multiple component Mutagens into a single Mutagen that generates all the mutations of the components.
     * @param mutagens the Mutagens to combine
     * @return the combination Mutagen
     */
    public static Mutagen combine(final Iterable<Mutagen> mutagens) {
        return new Mutagen() {
            @Override
            public Iterable<DocumentMutation> potentialMutations(final JsonElement document, final JsonPath pathToElement, final JsonElement elementToMutate) {
                return concat(transform(mutagens, new Function<Mutagen, Iterable<DocumentMutation>>() {
                    @Override
                    public Iterable<DocumentMutation> apply(Mutagen mutagen) {
                        return mutagen.potentialMutations(document, pathToElement, elementToMutate);
                    }
                }));
            }
        };
    }

    /**
     * A combination of all the Mutagens implemented in the Snodge library.
     */
    public static Mutagen allMutagens() {
        return combine(
                forAll(exampleElements, new Function<JsonElement, Mutagen>() {
                    @Override
                    public Mutagen apply(JsonElement e) {
                        return new ReplaceJsonElement(e);
                    }
                }),
                forAll(exampleElements, new Function<JsonElement, Mutagen>() {
                    @Override
                    public Mutagen apply(JsonElement e) {
                        return new AddArrayElement(e);
                    }
                }),
                forAll(exampleElements, new Function<JsonElement, Mutagen>() {
                    @Override
                    public Mutagen apply(JsonElement e) {
                        return new AddObjectProperty(e);
                    }
                }),
                new RemoveJsonElement(),
                new ReorderObjectProperties()
        );
    }

    private static Mutagen forAll(List<JsonElement> elements, Function<JsonElement, Mutagen> fn) {
        return combine(transform(elements, fn));
    }
}
