package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.toJsonElement
import com.natpryce.jsonk.toJsonString
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.internal.mapLazy
import com.natpryce.snodge.text.encodedAs
import com.natpryce.snodge.mapped
import java.nio.charset.Charset
import java.util.Random


typealias JsonElementMutagen = (random: Random, elementToMutate: JsonElement) -> Sequence<Lazy<JsonElement>>


fun JsonMutagen(elementMutagen: JsonElementMutagen): Mutagen<JsonElement> =
    fun(random: Random, original: JsonElement) =
        original.walk().flatMap { (element, replaceInDocument) ->
            elementMutagen(random, element).mapLazy(replaceInDocument)
        }


fun Mutagen<JsonElement>.forStrings() =
    mapped(String::toJsonElement, JsonElement::toJsonString)

fun Mutagen<JsonElement>.forEncodedStrings(encoding: Charset) =
    forStrings().encodedAs(encoding)
