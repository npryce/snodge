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

private fun Mutagen(f: (JsonElement, JsonPath, JsonElement) -> Sequence<DocumentMutation>) =
    object : Mutagen {
        override fun potentialMutations(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
            f(document, pathToElement, elementToMutate)
    }

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
    return combine(asList(*mutagens))
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

fun atPath(path: JsonPath, atPathMutagen: Mutagen): Mutagen {
    return atPath({ p -> p == path }, atPathMutagen)
}

fun atPath(pathSelector: (JsonPath) -> Boolean, atPathMutagen: Mutagen): Mutagen {
    return Mutagen { document, pathToElement, elementToMutate ->
        if (pathSelector(pathToElement))
            atPathMutagen.potentialMutations(document, pathToElement, elementToMutate)
        else
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
