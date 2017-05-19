package com.natpryce.snodge.mutagens

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.natpryce.snodge.JsonNodeMutagen
import com.natpryce.snodge.JsonPath
import java.util.ArrayList
import java.util.Collections

class ReorderObjectProperties : JsonNodeMutagen {
    override fun invoke(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement): Sequence<Lazy<JsonElement>> {
        if (elementToMutate.isJsonObject) {
            return sequenceOf(lazy { pathToElement.map(document, this::mutate) })
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
