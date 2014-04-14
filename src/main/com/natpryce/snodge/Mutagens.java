package com.natpryce.snodge;

import com.google.gson.*;
import com.natpryce.snodge.mutagens.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

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
     *
     * @param mutagens the Mutagens to combine
     * @return the combination Mutagen
     */
    public static Mutagen combine(final Mutagen... mutagens) {
        return combine(asList(mutagens));
    }

    /**
     * Combine multiple component Mutagens into a single Mutagen that generates all the mutations of the components.
     *
     * @param mutagens the Mutagens to combine
     * @return the combination Mutagen
     */
    public static Mutagen combine(Collection<Mutagen> mutagens) {
        return (document, pathToElement, elementToMutate) -> mutagens.stream().flatMap(
                mutagen -> mutagen.potentialMutations(document, pathToElement, elementToMutate));
    }

    public static Mutagen atPath(JsonPath path, Mutagen atPathMutagen) {
        return atPath(p -> p.equals(path), atPathMutagen);
    }

    public static Mutagen atPath(Predicate<? super JsonPath> pathSelector, Mutagen atPathMutagen) {
        return (document, pathToElement, elementToMutate) ->
                pathSelector.test(pathToElement)
                    ? atPathMutagen.potentialMutations(document, pathToElement, elementToMutate)
                    : Stream.empty();
    }

    /**
     * @return A combination of all the Mutagens implemented in the Snodge library.
     */
    public static Mutagen allMutagens() {
        return combine(
                forAll(exampleElements, ReplaceJsonElement::new),
                forAll(exampleElements, AddArrayElement::new),
                forAll(exampleElements, AddObjectProperty::new),
                new RemoveJsonElement(),
                new ReorderObjectProperties()
        );
    }

    private static Mutagen forAll(List<JsonElement> elements, Function<JsonElement, Mutagen> fn) {
        return combine(elements.stream().map(fn).collect(toList()));
    }
}
