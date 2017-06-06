@file:JvmName("JsonWalk")

package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonObject


fun JsonElement.walk(): Sequence<JsonPath> =
    walk(this, JsonPath.root)

private fun walk(element: JsonElement, elementPath: JsonPath): Sequence<JsonPath> =
    sequenceOf(elementPath) + walkChildren(element, elementPath)

private fun walkChildren(element: JsonElement, elementPath: JsonPath) =
    when (element) {
        is JsonObject ->
            walkChildren(elementPath, objectEntries(element))
        is JsonArray ->
            walkChildren(elementPath, arrayEntries(element))
        else ->
            emptySequence()
    }

private fun <T : Any> walkChildren(parentPath: JsonPath, children: List<Pair<T, JsonElement>>) =
    children.asSequence().flatMap { (key, value) -> walk(value, parentPath.extend(key)) }

private fun objectEntries(element: JsonObject) =
    element.map { it.key to it.value }

private fun arrayEntries(element: JsonArray) =
    element.mapIndexed(::Pair)
