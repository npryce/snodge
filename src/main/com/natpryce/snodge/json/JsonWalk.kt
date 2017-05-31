@file:JvmName("JsonWalk")

package com.natpryce.snodge.json

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.natpryce.snodge.json.JsonPath.Companion

fun JsonElement.walk(): Sequence<JsonPath> =
    walk(this, JsonPath.root)

private fun walk(element: JsonElement, elementPath: JsonPath): Sequence<JsonPath> =
    sequenceOf(elementPath) + walkChildren(element, elementPath)

private fun walkChildren(element: JsonElement, elementPath: JsonPath) =
    when (element) {
        is JsonObject ->
            walkChildren(elementPath, element.entrySet().map { it.key to it.value })
        is JsonArray ->
            walkChildren(elementPath, arrayEntries(element))
        else ->
            emptySequence()
    }

private fun <T : Any> walkChildren(parentPath: JsonPath, children: List<Pair<T, JsonElement>>) =
    children.asSequence().flatMap { (key, value) -> walk(value, parentPath.extend(key)) }

private fun arrayEntries(array: JsonArray) =
    array.mapIndexed(::Pair)
