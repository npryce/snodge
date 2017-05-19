@file:JvmName("JsonBuilders")

package com.natpryce.snodge.json

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

fun obj(vararg properties: Map.Entry<String, Any?>): JsonObject {
    return toJsonObject(properties.toList())
}

fun withNullField(name: String): Map.Entry<String, Any?> {
    return entry<String, Any?>(name, null)
}

fun withField(name: String, value: String): Map.Entry<String, Any?> {
    return entry(name, value as Any)
}

fun withField(name: String, value: Int?): Map.Entry<String, Any?> {
    return entry<String, Any?>(name, value)
}

fun withField(name: String, value: JsonElement): Map.Entry<String, Any?> {
    return entry(name, value)
}

fun list(vararg elements: Any?): JsonArray {
    val jsonArray = JsonArray()
    for (element in elements) {
        jsonArray.add(asJsonElement(element))
    }
    return jsonArray
}

private fun toJsonObject(entries: Iterable<Map.Entry<String, Any?>>): JsonObject {
    val json = JsonObject()
    for (property in entries) {
        json.add(property.key, asJsonElement(property.value))
    }
    return json
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
