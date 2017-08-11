package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonObject
import com.natpryce.jsonk.replace
import com.natpryce.jsonk.toJsonElement
import com.natpryce.jsonk.toJsonString
import com.natpryce.jsonk.withProperty
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.mapped
import com.natpryce.snodge.recurse
import com.natpryce.snodge.walk


inline fun <reified T : JsonElement> JsonMutagen(crossinline elementMutagen: Mutagen<T>): Mutagen<JsonElement> =
    recurse({ e: JsonElement -> e.walk() }, elementMutagen)

fun Mutagen<JsonElement>.forStrings() = mapped({ it.toJsonElement() }, { it.toJsonString() })

fun JsonElement.walk(): Sequence<Pair<JsonElement, (JsonElement) -> JsonElement>> = walk {
    when (it) {
        is JsonObject ->
            it.asSequence().map { (key, child) ->
                child to { newChild: JsonElement -> it.withProperty(key to newChild) }
            }
        is JsonArray ->
            it.asSequence().withIndex().map { (i, child) ->
                child to { newChild: JsonElement -> it.replace(i, newChild) }
            }
        else ->
            emptySequence()
    }
}
