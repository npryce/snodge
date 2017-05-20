@file:JvmName("JsonBuilders")

package com.natpryce.snodge.json

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

fun obj(vararg properties: Pair<String, Any?>) =
    JsonObject().apply {
        properties.forEach { (name, value) ->
            add(name, asJsonElement(value))
        }
    }

fun list(vararg elements: Any?) =
    JsonArray().apply {
        elements.forEach { element -> add(asJsonElement(element)) }
    }

private fun asJsonElement(element: Any?): JsonElement {
    when (element) {
        null -> return JsonNull.INSTANCE
        is JsonElement -> return element
        is String -> return JsonPrimitive((element as String?))
        is Boolean -> return JsonPrimitive((element as Boolean?))
        is Number -> return JsonPrimitive((element as Number?))
        is Char -> return JsonPrimitive((element as Char?))
        else -> throw IllegalArgumentException("cannot turn a " + element.javaClass.name + " to a JsonElement")
    }
}
