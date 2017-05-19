@file:JvmName("JsonFunctions")

package com.natpryce.snodge.json

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.AbstractMap.SimpleEntry

internal fun <K, V> entry(key: K, value: V): Map.Entry<K, V> {
    return SimpleEntry(key, value)
}

internal fun arrayEntries(array: JsonArray): Sequence<Map.Entry<Int, JsonElement>> {
    return array.mapIndexed { i, element -> entry(i, element) }.asSequence()
}

internal fun removeArrayElement(original: JsonArray, indexToRemove: Int) =
    JsonArray().apply {
        addAll(original)
        remove(indexToRemove)
    }

internal fun removeObjectProperty(original: JsonObject, nameToRemove: String): JsonElement {
    val mutant = JsonObject()
    
    original.entrySet()
        .filter { e -> e.key != nameToRemove }
        .forEach { e -> mutant.add(e.key, e.value) }
    
    return mutant
}
