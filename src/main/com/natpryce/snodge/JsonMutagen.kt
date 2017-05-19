package com.natpryce.snodge

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.natpryce.snodge.internal.walk
import com.natpryce.snodge.mutagens.AddArrayElement
import com.natpryce.snodge.mutagens.AddObjectProperty
import com.natpryce.snodge.mutagens.RemoveJsonElement
import com.natpryce.snodge.mutagens.ReorderObjectProperties
import com.natpryce.snodge.mutagens.ReplaceJsonElement
import java.nio.charset.Charset
import java.util.Arrays


fun JsonMutagen(vararg nodeMutagens: JsonNodeMutagen) =
    JsonMutagen(combine(*nodeMutagens))

fun JsonMutagen(nodeMutagen: JsonNodeMutagen) =
    fun(original: JsonElement) =
        original.walk()
            .flatMap { path -> nodeMutagen.invoke(original, path, path(original)) }

fun Mutagen<JsonElement>.forStrings(): Mutagen<String> {
    val gson = Gson()
    return this@forStrings.mapped({ gson.fromJson(it, JsonElement::class.java) }, { it.toString() })
}

fun Mutagen<JsonElement>.forEncodedStrings(encoding: Charset) =
    this.forStrings().encodedAs(encoding)

fun Mutagen<JsonElement>.forEncodedStrings(encodingName: String) =
    this.forEncodedStrings(Charset.forName(encodingName))

/**
 * @return Applies all the JSON mutations implemented in the Snodge library.
 */
fun allJsonMutagens(): Mutagen<JsonElement> {
    return JsonMutagen(
        forAll(exampleElements, { ReplaceJsonElement(it) }),
        forAll(exampleElements, { AddArrayElement(it) }),
        forAll(exampleElements, { AddObjectProperty(it) }),
        RemoveJsonElement(),
        ReorderObjectProperties())
}

private fun forAll(elements: List<JsonElement>, fn: (JsonElement) -> JsonNodeMutagen): JsonNodeMutagen {
    return combine(elements.map(fn).toList())
}

private val exampleElements = Arrays.asList(
    JsonNull.INSTANCE,
    JsonPrimitive(true),
    JsonPrimitive(false),
    JsonPrimitive(99),
    JsonPrimitive(-99),
    JsonPrimitive("a string"),
    JsonArray(),
    JsonObject())
