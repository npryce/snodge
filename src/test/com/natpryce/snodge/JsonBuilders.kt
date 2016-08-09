@file:JvmName("JsonBuilders")

package com.natpryce.snodge

import com.google.gson.*
import com.natpryce.snodge.internal.JsonFunctions

import com.natpryce.snodge.internal.JsonFunctions.entry
import java.util.Arrays.asList

@SafeVarargs
fun obj(vararg properties: Map.Entry<String, Any>): JsonObject {
    return toJsonObject(asList<Map.Entry<String, Any>>(*properties))
}

fun withNullField(name: String): Map.Entry<String, Any> {
    return entry<String, Any>(name, null)
}

fun withField(name: String, value: String): Map.Entry<String, Any> {
    return entry(name, value as Any)
}

fun withField(name: String, value: Int?): Map.Entry<String, Any> {
    return entry<String, Any>(name, value as Any?)
}

fun withField(name: String, value: JsonElement): Map.Entry<String, Any> {
    return entry(name, value as Any)
}

fun list(vararg elements: Any?): JsonArray {
    val jsonArray = JsonArray()
    for (element in elements) {
        jsonArray.add(asJsonElement(element))
    }
    return jsonArray
}

private fun toJsonObject(entries: Iterable<Map.Entry<String, Any>>): JsonObject {
    val json = JsonObject()
    for (property in entries) {
        json.add(property.key, asJsonElement(property.value))
    }
    return json
}

private fun asJsonElement(element: Any?): JsonElement {
    if (element == null) {
        return JsonNull.INSTANCE
    }
    else if (element is JsonElement) {
        return element
    }
    else if (element is String) {
        return JsonPrimitive((element as String?)!!)
    }
    else if (element is Boolean) {
        return JsonPrimitive((element as Boolean?)!!)
    }
    else if (element is Number) {
        return JsonPrimitive((element as Number?)!!)
    }
    else if (element is Char) {
        return JsonPrimitive((element as Char?)!!)
    }
    else {
        throw IllegalArgumentException("cannot turn a " + element.javaClass.name + " to a JsonElement")
    }
}
