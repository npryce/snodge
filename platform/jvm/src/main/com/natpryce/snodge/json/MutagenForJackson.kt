package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonBoolean
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonNull
import com.natpryce.jsonk.JsonNumber
import com.natpryce.jsonk.JsonObject
import com.natpryce.jsonk.JsonString
import com.natpryce.jsonk.toBigDecimal
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.mapped
import com.fasterxml.jackson.databind.JsonNode as JacksonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory.instance as jackson

fun Mutagen<JsonElement>.forJackson(): Mutagen<JacksonNode> =
    mapped(JacksonNode::toJsonk, JsonElement::toJackson)

fun JacksonNode.toJsonk(): JsonElement =
    when {
        isObject -> JsonObject(fields().asSequence().map { (key, value) -> key to value.toJsonk() }.toList())
        isArray -> JsonArray(map { it.toJsonk() })
        isNull -> JsonNull
        isTextual -> JsonString(textValue())
        isBoolean -> JsonBoolean(booleanValue())
        isNumber -> JsonNumber(asText())
        else -> throw IllegalArgumentException("cannot convert $this to JsonK")
    }

fun JsonElement.toJackson(): JacksonNode =
    when (this) {
        is JsonObject -> jackson.objectNode().also {
            it.setAll(properties.mapValues { (_, value) -> value.toJackson() })
        }
        is JsonArray -> jackson.arrayNode().also {
            it.addAll(elements.map { it.toJackson() })
        }
        JsonNull -> jackson.nullNode()
        is JsonNumber -> jackson.numberNode(toBigDecimal())
        is JsonString -> jackson.textNode(value)
        is JsonBoolean -> jackson.booleanNode(value)
    }
