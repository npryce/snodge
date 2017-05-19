@file:JvmName("JsonWalk")

package com.natpryce.snodge.json

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.natpryce.snodge.json.JsonPath.Companion

internal fun JsonElement.walk(): Sequence<JsonPath> {
    return walk(this, Companion.root)
}

private fun walk(element: JsonElement, elementPath: JsonPath): Sequence<JsonPath> {
    return sequenceOf(elementPath) + walkChildren(element, elementPath)
}

private fun walkChildren(element: JsonElement, elementPath: JsonPath): Sequence<JsonPath> {
    if (element is JsonObject) {
        return walkChildren(elementPath, element.entrySet().asSequence())
    }
    else if (element is JsonArray) {
        return walkChildren(elementPath, arrayEntries(element))
    }
    else {
        return emptySequence()
    }
}

private fun <T : Any> walkChildren(parentPath: JsonPath, children: Sequence<Map.Entry<T, JsonElement>>): Sequence<JsonPath> {
    return children.flatMap { child -> walk(child.value, parentPath.extend(child.key)) }
}
