package com.natpryce.snodge.mutagens

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.natpryce.snodge.JsonNodeMutagen
import com.natpryce.snodge.JsonPath

class AddArrayElement(private val newElement: JsonElement) : JsonNodeMutagen {
    override fun invoke(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement): Sequence<Lazy<JsonElement>> {
        if (elementToMutate is JsonArray) {
            return sequenceOf(lazy { pathToElement.map(document, this::mutate) })
        }
        else {
            return emptySequence()
        }
    }
    
    fun mutate(original: JsonElement): JsonElement {
        val mutant = JsonArray()
        mutant.addAll(original.asJsonArray)
        mutant.add(newElement)
        return mutant
    }
}
