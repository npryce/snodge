package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonBoolean
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonNull
import com.natpryce.jsonk.JsonNumber
import com.natpryce.jsonk.JsonObject
import com.natpryce.jsonk.JsonString
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.mapped
import javax.json.Json
import javax.json.JsonArray as JsonpArray
import javax.json.JsonNumber as JsonpNumber
import javax.json.JsonObject as JsonpObject
import javax.json.JsonString as JsonpString
import javax.json.JsonValue as JsonpValue


fun Mutagen<JsonElement>.forJsonp(): Mutagen<JsonpValue> =
    mapped(JsonpValue::toJsonk, JsonElement::toJsonp)

fun JsonElement.toJsonp(): JsonpValue =
    when (this) {
        is JsonObject -> Json.createObjectBuilder()
            .also { b ->
                this.forEach { key, value -> b.add(key, value.toJsonp()) }
            }.build()
        is JsonArray -> Json.createArrayBuilder()
            .also { b ->
                this.forEach { b.add(it.toJsonp()) }
            }.build()
        JsonNull -> JsonpValue.NULL
        is JsonBoolean -> if (value) JsonpValue.TRUE else JsonpValue.FALSE
        is JsonNumber -> Json.createValue(toBigDecimal())
        is JsonString -> Json.createValue(value)
    }

fun JsonpValue.toJsonk(): JsonElement =
    when (this) {
        is JsonpObject -> JsonObject(entries.map { (key, value) -> key to value.toJsonk() })
        is JsonpArray -> JsonArray(this.map { it.toJsonk() })
        JsonpValue.NULL -> JsonNull
        JsonpValue.TRUE -> JsonBoolean(true)
        JsonpValue.FALSE -> JsonBoolean(false)
        is JsonpNumber -> JsonNumber(bigDecimalValue().toPlainString())
        is JsonpString -> JsonString(string)
        else -> throw IllegalArgumentException("cannot convert $this to JsonK")
    }
