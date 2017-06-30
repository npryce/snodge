package com.natpryce.jsonk

import kotlin.js.json

fun JsonElement.toJsonString(): String {
    return JSON.stringify(this.toJavaScriptObject())
}

private fun JsonElement.toJavaScriptObject(): Any? {
    return when(this) {
        is JsonObject -> json(*this.map { it.key to it.value.toJavaScriptObject() }.toTypedArray())
        is JsonArray -> this.map { it.toJavaScriptObject() }
        is JsonString -> this.value
        is JsonBoolean -> this.value
        is JsonNull -> null
        is JsonNumber -> this.toDouble()
    }
}
