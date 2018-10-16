package com.natpryce.jsonk

import kotlin.js.Json


fun String.toJsonElement(): JsonElement =
    JSON.parse<Any?>(this).toJsonElement()

private fun Any?.toJsonElement(): JsonElement {
    return when(this) {
        undefined, null -> JsonNull
        is Number -> JsonNumber(toString())
        is String -> JsonString(this)
        is Boolean -> JsonBoolean(this)
        is Array<*> -> JsonArray(this.map { it.toJsonElement() })
        else -> { // assume object
            @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
            val json: Json = this as Json
            JsonObject(Object.keys(json).map { it to json[it].toJsonElement() })
        }
    }
}

external private class Object {
    companion object {
        fun keys(o: Any?): Array<out String>
    }
}

