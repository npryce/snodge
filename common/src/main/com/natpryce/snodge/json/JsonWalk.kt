package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonObject
import com.natpryce.jsonk.replace
import com.natpryce.jsonk.withProperty
import com.natpryce.snodge.walk


fun JsonElement.walk(): Sequence<Pair<JsonElement, (JsonElement) -> JsonElement>> =
    walk(::listChildren)

private fun listChildren(parent: JsonElement): Sequence<Pair<JsonElement, (JsonElement) -> JsonElement>> {
    return when (parent) {
        is JsonObject ->
            parent.asSequence().map { (key, child) ->
                child to { newChild: JsonElement -> parent.withProperty(key to newChild) }
            }
        is JsonArray ->
            parent.asSequence().withIndex().map { (i, child) ->
                child to { newChild: JsonElement -> parent.replace(i, newChild) }
            }
        else ->
            emptySequence()
    }
}
