@file:JvmName("JsonBuilders")

package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonBoolean
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonNull
import com.natpryce.jsonk.JsonNumber
import com.natpryce.jsonk.JsonObject
import com.natpryce.jsonk.JsonString


fun obj(vararg properties: Pair<String, Any?>) =
    JsonObject(properties.map { (name, value) -> name to value.asJsonElement() }.toMap())

fun list(vararg elements: Any?) =
    JsonArray(elements.map { it.asJsonElement() })

private fun Any?.asJsonElement(): JsonElement {
    when (this) {
        null -> return JsonNull
        is JsonElement -> return this
        is String -> return JsonString(this)
        is Boolean -> return JsonBoolean(this)
        is Number -> return JsonNumber(toString())
        is Char -> return JsonString(toString())
        else -> throw IllegalArgumentException("cannot turn a " + this.javaClass.name + " to a JsonElement")
    }
}
