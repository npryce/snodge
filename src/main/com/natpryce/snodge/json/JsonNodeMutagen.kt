package com.natpryce.snodge.json

import com.google.gson.JsonElement


/**
 * A source of mutation within a JSON document.
 *
 * A JsonNodeMutagen maps an element within a JSON document, at a location described by a [com.natpryce.snodge.JsonPath],
 * to zero or more lazily evaluated mutations of the entire document.
 *
 * Multiple JsonNodeMutagen can be combined into a more powerful JsonNodeMutagen by the
 * [com.natpryce.snodge.JsonNodeMutagen.combine] function.
 */
typealias JsonNodeMutagen =
    (document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) ->Sequence<Lazy<JsonElement>>


/**
 * Useful for turning a lambda to a fully typed JsonNodeMutagen
 */
fun JsonNodeMutagen(f: (JsonElement, JsonPath, JsonElement) -> Sequence<Lazy<JsonElement>>) = f


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
    return JsonNodeMutagen { document, pathToElement, elementToMutate ->
        mutagens.asSequence().flatMap { it(document, pathToElement, elementToMutate) }
    }
}

/**
 * Constrain a JsonNodeMutagen to apply only to the element at the given path
 */
fun JsonNodeMutagen.atPath(path: JsonPath) = this.atPath { it == path }

/**
 * Constrain a JsonNodeMutagen to apply only to the element at the given path or its children
 */
fun JsonNodeMutagen.atOrBelowPath(path: JsonPath) = this.atPath { it.startsWith(path) }

/**
 * Constrain a JsonNodeMutagen to apply only to elements at paths that match the given predicate
 */
fun JsonNodeMutagen.atPath(pathSelector: (JsonPath) -> Boolean) =
    JsonNodeMutagen { document, pathToElement, elementToMutate ->
        if (pathSelector(pathToElement)) {
            invoke(document, pathToElement, elementToMutate)
        }
        else {
            emptySequence()
        }
    }


fun JsonNodeMutagen.ifElement(criteria: (JsonElement)->Boolean) =
    JsonNodeMutagen { document, pathToElement, elementToMutate ->
        if (criteria(elementToMutate)) {
            this(document, pathToElement, elementToMutate)
        } else {
            emptySequence()
        }
    }
