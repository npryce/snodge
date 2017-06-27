package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.toJsonElement
import com.natpryce.jsonk.toJsonString
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.Random
import com.natpryce.snodge.internal.mapLazy
import com.natpryce.snodge.mapped


typealias JsonElementMutagen<T> = (random: Random, elementToMutate: T) -> Sequence<Lazy<T>>


inline fun <reified T: JsonElement> JsonMutagen(crossinline elementMutagen: JsonElementMutagen<T>): Mutagen<JsonElement> =
    { random: Random, original: JsonElement ->
        original.walk().flatMap { (element, replaceInDocument) ->
            if (element is T) {
                elementMutagen(random, element).mapLazy(replaceInDocument)
            } else {
                emptySequence()
            }
        }
    }


fun Mutagen<JsonElement>.forStrings() = mapped({it.toJsonElement()}, {it.toJsonString()})
