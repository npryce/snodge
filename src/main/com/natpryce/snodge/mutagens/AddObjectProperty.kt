package com.natpryce.snodge.mutagens

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.natpryce.snodge.JsonNodeMutagen
import com.natpryce.snodge.JsonPath

class AddObjectProperty(private val newElement: JsonElement) : JsonNodeMutagen {
    
    override fun invoke(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement): Sequence<Lazy<JsonElement>> {
        if (elementToMutate.isJsonObject) {
            return sequenceOf(lazy {pathToElement.map(document, this::mutate) })
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
