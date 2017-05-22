@file:JvmName("JsonMutagen")

package com.natpryce.snodge.json

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.encodedAs
import com.natpryce.snodge.mapped
import com.natpryce.snodge.mutants
import com.natpryce.snodge.reflect.troublesomeClasses
import java.nio.charset.Charset
import java.util.Random


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


fun reflectionMutagens(): JsonNodeMutagen =
    troublesomeClasses()
        .map { replaceJsonElement(JsonPrimitive(it)).ifElement { it is JsonPrimitive && it.isString } }
        .let { combine(it) }

/**
 * @return Applies all the JSON mutations implemented in the Snodge library.
 */
fun allJsonMutagens() =
    JsonMutagen(
        combine(exampleElements.map { exampleElement ->
            combine(
                replaceJsonElement(exampleElement),
                addArrayElement(exampleElement),
                addObjectProperty(exampleElement)
            )
        }),
        removeJsonElement(),
        reorderObjectProperties(),
        reflectionMutagens())

private val exampleElements = listOf(
    JsonNull.INSTANCE,
    JsonPrimitive(true),
    JsonPrimitive(false),
    JsonPrimitive(99),
    JsonPrimitive(-99),
    JsonPrimitive("a string"),
    JsonArray(),
    JsonObject())
