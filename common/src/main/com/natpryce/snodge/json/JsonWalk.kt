package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonObject
import com.natpryce.jsonk.replace
import com.natpryce.jsonk.withProperty


fun JsonElement.walk(): Sequence<Pair<JsonElement, (JsonElement) -> JsonElement>> =
    walk(this, { it })

private fun walk(element: JsonElement, replaceInDocument: (JsonElement) -> JsonElement): Sequence<Pair<JsonElement, (JsonElement) -> JsonElement>> =
    sequenceOf(Pair(element, replaceInDocument)) + walkChildren(element, replaceInDocument)

private fun walkChildren(parent: JsonElement, replaceInDocument: (JsonElement) -> JsonElement) =
    when (parent) {
        is JsonObject ->
            parent.asSequence().flatMap { (key, child) ->
                walk(child, { newChild: JsonElement -> replaceInDocument(parent.withProperty(key to newChild)) })
            }
        is JsonArray ->
            parent.asSequence().withIndex().flatMap { (i, child) ->
                walk(child, { newChild: JsonElement -> replaceInDocument(parent.replace(i, newChild)) })
            }
        else ->
            emptySequence()
    }
