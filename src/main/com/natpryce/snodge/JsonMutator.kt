package com.natpryce.snodge

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.natpryce.snodge.internal.walk
import java.nio.charset.Charset
import java.util.*


fun JsonMutagen(vararg nodeMutagens: JsonNodeMutagen) =
    JsonMutagen(combine(*nodeMutagens))

fun JsonMutagen(nodeMutagen: JsonNodeMutagen) =
    fun(original: JsonElement) =
        original.walk()
            .flatMap { path -> nodeMutagen.potentialMutations(original, path, path(original)) }

fun Mutagen<JsonElement>.forStrings(): Mutagen<String> {
    val gson = Gson()
    return this@forStrings.mapped({ gson.fromJson(it, JsonElement::class.java) }, { it.toString() })
}

fun Mutagen<JsonElement>.forEncodedStrings(encoding: Charset) =
    this.forStrings().encodedAs(encoding)

fun Mutagen<JsonElement>.forEncodedStrings(encodingName: String) =
    this.forEncodedStrings(Charset.forName(encodingName))
