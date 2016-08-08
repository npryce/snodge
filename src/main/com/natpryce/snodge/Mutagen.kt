package com.natpryce.snodge

import com.google.gson.JsonElement

import java.util.stream.Stream

/**
 * A source of mutation within a JSON document.
 *
 *
 * A Mutagen maps an element within a JSON document, at a location described by a [com.natpryce.snodge.JsonPath],
 * to zero or more potential [com.natpryce.snodge.DocumentMutation]s that can be applied to the document.
 *
 *
 * Multiple Mutagens can be combined into a more powerful Mutagen by the
 * [com.natpryce.snodge.Mutagens.combine] function.
 */
interface Mutagen {
    /**
     * Returns zero or more mutations that can be applied to the document.
     
     * @param document the document for which potential mutations are calculated
     * *
     * @param pathToElement the path from the root of the document to the elementToMutate
     * *
     * @param elementToMutate the element in the document that will be affected by the mutations returned
     * *
     * @return zero or more mutations of the entire document.
     */
    fun potentialMutations(
        document: JsonElement,
        pathToElement: JsonPath,
        elementToMutate: JsonElement): Stream<DocumentMutation>
}
