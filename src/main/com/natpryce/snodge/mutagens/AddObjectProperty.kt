package com.natpryce.snodge.mutagens

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.natpryce.snodge.DocumentMutation
import com.natpryce.snodge.JsonPath
import com.natpryce.snodge.Mutagen

import kotlin.sequences.emptySequence
import kotlin.sequences.sequenceOf

class AddObjectProperty(private val newElement: JsonElement) : Mutagen {
    
    override fun potentialMutations(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement): Sequence<DocumentMutation> {
        if (elementToMutate.isJsonObject) {
            return sequenceOf(pathToElement.map { original: JsonElement -> this.mutate(original) })
        }
        else {
            return emptySequence()
        }
    }
    
    private fun mutate(original: JsonElement): JsonElement {
        val mutated = JsonObject()
        val entries = original.asJsonObject.entrySet()
        for ((key, value) in entries) {
            mutated.add(key, value)
        }
        mutated.add(newProperty(mutated), newElement)
        return mutated
    }
    
    private fun newProperty(thingy: JsonObject): String {
        var newPropertyName = "x"
        while (thingy.has(newPropertyName)) {
            newPropertyName += "x"
        }
        return newPropertyName
    }
}
