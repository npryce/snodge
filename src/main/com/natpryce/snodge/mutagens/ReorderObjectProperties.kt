package com.natpryce.snodge.mutagens

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.natpryce.snodge.DocumentMutation
import com.natpryce.snodge.JsonPath
import com.natpryce.snodge.Mutagen

import java.util.ArrayList
import java.util.Collections

import kotlin.sequences.emptySequence
import kotlin.sequences.sequenceOf

class ReorderObjectProperties : Mutagen {
    override fun potentialMutations(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement): Sequence<DocumentMutation> {
        if (elementToMutate.isJsonObject) {
            return sequenceOf(pathToElement.map { element: JsonElement -> this.mutate(element) })
        }
        else {
            return emptySequence()
        }
    }
    
    fun mutate(element: JsonElement): JsonElement {
        val objectProperties = ArrayList(element.asJsonObject.entrySet())
        Collections.shuffle(objectProperties)
        
        val mutant = JsonObject()
        for ((key, value) in objectProperties) {
            mutant.add(key, value)
        }
        return mutant
    }
}
