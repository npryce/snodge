@file:JvmName("Mutagens")

package com.natpryce.snodge

/*
 * Functions that apply to, or provide, [com.natpryce.snodge.Mutagen]s.
 */

import com.google.gson.*
import com.natpryce.snodge.mutagens.*
import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Collectors
import java.util.stream.Stream

import java.util.Arrays.asList
import java.util.stream.Collectors.*

private val exampleElements = asList(
    JsonNull.INSTANCE,
    JsonPrimitive(true),
    JsonPrimitive(false),
    JsonPrimitive(99),
    JsonPrimitive(-99),
    JsonPrimitive("a string"),
    JsonArray(),
    JsonObject())

/**
 * Combine multiple component Mutagens into a single Mutagen that generates all the mutations of the components.
 
 * @param mutagens the Mutagens to combine
 * *
 * @return the combination Mutagen
 */
fun combine(vararg mutagens: Mutagen): Mutagen {
    return combine(mutagens.toList())
}

/**
 * Combine multiple component Mutagens into a single Mutagen that generates all the mutations of the components.
 
 * @param mutagens the Mutagens to combine
 * *
 * @return the combination Mutagen
 */
fun combine(mutagens: Collection<Mutagen>): Mutagen {
    return Mutagen { document, pathToElement, elementToMutate ->
        mutagens.asSequence().flatMap { mutagen ->
            mutagen.potentialMutations(document, pathToElement, elementToMutate)
        }
    }
}

fun Mutagen.atPath(path: JsonPath) = this.atPath { p -> p == path }

fun Mutagen.atPath(pathSelector: (JsonPath) -> Boolean) =
    Mutagen { document, pathToElement, elementToMutate ->
        if (pathSelector(pathToElement)) {
            potentialMutations(document, pathToElement, elementToMutate)
        }
        else {
            emptySequence()
        }
    }

/**
 * @return A combination of all the Mutagens implemented in the Snodge library.
 */
fun allMutagens(): Mutagen {
    return combine(
        forAll(exampleElements, { ReplaceJsonElement(it) }),
        forAll(exampleElements, { AddArrayElement(it) }),
        forAll(exampleElements, { AddObjectProperty(it) }),
        RemoveJsonElement(),
        ReorderObjectProperties())
}

private fun forAll(elements: List<JsonElement>, fn: (JsonElement) -> Mutagen): Mutagen {
    return combine(elements.map(fn).toList())
}
