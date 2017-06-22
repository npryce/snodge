package com.natpryce.jsonk

import java.io.Reader
import javax.json.Json
import javax.json.stream.JsonParser
import javax.json.stream.JsonParser.Event
import javax.json.stream.JsonParser.Event.END_ARRAY
import javax.json.stream.JsonParser.Event.END_OBJECT
import javax.json.stream.JsonParser.Event.START_ARRAY
import javax.json.stream.JsonParser.Event.START_OBJECT
import javax.json.stream.JsonParser.Event.VALUE_FALSE
import javax.json.stream.JsonParser.Event.VALUE_NULL
import javax.json.stream.JsonParser.Event.VALUE_NUMBER
import javax.json.stream.JsonParser.Event.VALUE_STRING
import javax.json.stream.JsonParser.Event.VALUE_TRUE
import javax.json.stream.JsonParsingException


fun String.toJsonElement(): JsonElement = this.reader().readJsonElement()

fun Reader.readJsonElement(): JsonElement = Json.createParser(this).readJsonElement()

fun JsonParser.readJsonElement(): JsonElement {
    return parseForToken(next())
}

private fun JsonParser.parseJsonObject(): JsonElement {
    val properties = linkedMapOf<String,JsonElement>()
    
    while (next() != END_OBJECT) {
        val name = string
        val value = readJsonElement()
        properties[name] = value
    }
    
    return JsonObject(properties)
}

private fun JsonParser.parseJsonArray(): JsonElement {
    val elements = mutableListOf<JsonElement>()
    
    while (true) {
        val token = next()
        if (token == END_ARRAY) break
        elements.add(parseForToken(token))
    }
    
    return JsonArray(elements.toList())
}

private fun JsonParser.parseForToken(token: Event?): JsonElement {
    return when (token) {
        START_ARRAY -> parseJsonArray()
        START_OBJECT -> parseJsonObject()
        VALUE_NULL -> JsonNull
        VALUE_STRING -> JsonString(string)
        VALUE_NUMBER -> JsonNumber(string)
        VALUE_FALSE -> JsonBoolean(false)
        VALUE_TRUE -> JsonBoolean(true)
        else -> throw JsonParsingException("JSON syntax error: unexpected token $token", location)
    }
}
