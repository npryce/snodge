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
import com.google.gson.JsonArray as GsonArray
import com.google.gson.JsonElement as GsonElement
import com.google.gson.JsonNull as GsonNull
import com.google.gson.JsonObject as GsonObject
import com.google.gson.JsonPrimitive as GsonPrimitive

fun Mutagen<JsonElement>.forGson(): Mutagen<GsonElement> =
    mapped(GsonElement::toJsonk, JsonElement::toGson)

fun GsonElement.toJsonk(): JsonElement =
    when (this) {
        is GsonObject -> JsonObject(this.entrySet().map { it.key to it.value.toJsonk() })
        is GsonArray -> JsonArray(this.map { it.toJsonk() })
        is GsonNull -> JsonNull
        is GsonPrimitive ->
            when {
                isBoolean -> JsonBoolean(asBoolean)
                isString -> JsonString(asString)
                isNumber -> JsonNumber(asString)
                else -> throw IllegalArgumentException("cannot convert primitive to JsonK: $this")
            }
        else -> throw IllegalArgumentException("cannot convert to JsonK: $this")
    }

fun JsonElement.toGson(): GsonElement =
    when (this) {
        is JsonObject -> {
            GsonObject().also {
                this.forEach { p -> it.add(p.key, p.value.toGson()) }
            }
        }
        is JsonArray -> {
            GsonArray().also {
                this.forEach { e -> it.add(e.toGson()) }
            }
        }
        JsonNull -> GsonNull.INSTANCE
        is JsonString -> GsonPrimitive(value)
        is JsonBoolean -> GsonPrimitive(value)
        is JsonNumber -> GsonPrimitive(toBigDecimal())
    }

