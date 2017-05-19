package com.natpryce.snodge.mutagens

import com.google.gson.JsonElement
import com.natpryce.snodge.JsonNodeMutagen
import com.natpryce.snodge.JsonPath

class ReplaceJsonElement(private val replacement: JsonElement) : JsonNodeMutagen {
    override fun invoke(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement): Sequence<Lazy<JsonElement>> {
        return sequenceOf(lazy { pathToElement.replace(document, replacement) })
    }
}
