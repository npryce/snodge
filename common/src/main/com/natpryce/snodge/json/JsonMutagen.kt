package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.toJsonElement
import com.natpryce.jsonk.toJsonString
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.mapped
import com.natpryce.snodge.recurse


inline fun <reified T : JsonElement> JsonMutagen(crossinline elementMutagen: Mutagen<T>): Mutagen<JsonElement> =
    recurse(JsonElement::walk, elementMutagen)

fun Mutagen<JsonElement>.forStrings() = mapped({ it.toJsonElement() }, { it.toJsonString() })
