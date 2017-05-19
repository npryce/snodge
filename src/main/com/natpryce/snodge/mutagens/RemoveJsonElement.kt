package com.natpryce.snodge.mutagens

import com.google.gson.JsonElement
import com.natpryce.snodge.JsonNodeMutagen
import com.natpryce.snodge.JsonPath

class RemoveJsonElement : JsonNodeMutagen {
    override fun invoke(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement): Sequence<Lazy<JsonElement>> {
        if (pathToElement.isRoot) {
            return emptySequence()
        }
        else {
            return sequenceOf(lazy { pathToElement.remove(document) })
        }
    }
}
