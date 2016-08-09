package com.natpryce.snodge.mutagens

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.natpryce.snodge.DocumentMutation
import com.natpryce.snodge.JsonPath
import com.natpryce.snodge.Mutagen

class AddArrayElement(private val newElement: JsonElement) : Mutagen {
    override fun potentialMutations(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement): Sequence<DocumentMutation> {
        if (elementToMutate is JsonArray) {
            return sequenceOf(pathToElement.map({mutate(it)}))
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
