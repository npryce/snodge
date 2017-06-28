package com.natpryce.jsonk

import java.io.StringWriter
import java.io.Writer
import javax.json.Json
import javax.json.stream.JsonGenerator


fun JsonElement.toJsonString(): String =
    StringWriter().also { it.writeJsonElement(this) }.toString()

fun Writer.writeJsonElement(e: JsonElement) {
    Json.createGenerator(this).apply {
        writeJsonElement(e)
        flush()
    }
}

fun JsonGenerator.writeJsonElement(e: JsonElement) {
    when (e) {
        JsonNull -> writeNull()
        is JsonString -> write(e.value)
        is JsonNumber -> write(e.toBigDecimal())
        is JsonBoolean -> write(e.value)
        is JsonArray -> {
            writeStartArray()
            e.elements.forEach { writeJsonElement(it) }
            writeEnd()
        }
        is JsonObject -> {
            writeStartObject()
            e.properties.forEach { (name, value) ->
                writeKey(name)
                writeJsonElement(value)
            }
            writeEnd()
        }
    }!!
}
