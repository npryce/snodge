@file:JvmName("Mutagens")

package com.natpryce.snodge

/*
 * Functions that apply to, or provide, [com.natpryce.snodge.Mutagen]s.
 */

import com.google.gson.*
import com.natpryce.snodge.mutagens.*

import java.util.Arrays.asList

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
 * Combine multiple component JsonNodeMutagen into a single JsonNodeMutagen that generates all the mutations of the components.
 
 * @param mutagens the JsonNodeMutagen to combine
 * *
 * @return the combination JsonNodeMutagen
 */
fun combine(vararg mutagens: JsonNodeMutagen): JsonNodeMutagen {
    return combine(mutagens.toList())
}

/**
 * Combine multiple component JsonNodeMutagen into a single JsonNodeMutagen that generates all the mutations of the components.
 
 * @param mutagens the JsonNodeMutagen to combine
 * *
 * @return the combination JsonNodeMutagen
 */
fun combine(mutagens: Collection<JsonNodeMutagen>): JsonNodeMutagen {
    return Mutagen { document, pathToElement, elementToMutate ->
        mutagens.asSequence().flatMap { mutagen ->
            mutagen.potentialMutations(document, pathToElement, elementToMutate)
        }
    }
}

fun JsonNodeMutagen.atPath(path: JsonPath) = this.atPath { p -> p == path }

fun JsonNodeMutagen.atPath(pathSelector: (JsonPath) -> Boolean) =
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
fun allMutagens(): Mutagen<JsonElement> {
    return JsonMutagen(
        forAll(exampleElements, { ReplaceJsonElement(it) }),
        forAll(exampleElements, { AddArrayElement(it) }),
        forAll(exampleElements, { AddObjectProperty(it) }),
        RemoveJsonElement(),
        ReorderObjectProperties())
}

private fun forAll(elements: List<JsonElement>, fn: (JsonElement) -> JsonNodeMutagen): JsonNodeMutagen {
    return combine(elements.map(fn).toList())
}
