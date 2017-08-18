package com.natpryce.snodge.json

import argo.jdom.JsonField
import argo.jdom.JsonNode
import argo.jdom.JsonNodeFactories.`object`
import argo.jdom.JsonNodeFactories.array
import argo.jdom.JsonNodeFactories.falseNode
import argo.jdom.JsonNodeFactories.nullNode
import argo.jdom.JsonNodeFactories.number
import argo.jdom.JsonNodeFactories.string
import argo.jdom.JsonNodeFactories.trueNode
import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonBoolean
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonNull
import com.natpryce.jsonk.JsonNumber
import com.natpryce.jsonk.JsonObject
import com.natpryce.jsonk.JsonString
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.mapped

fun Mutagen<JsonElement>.forArgo(): Mutagen<JsonNode> =
    mapped(JsonNode::toJsonk, JsonElement::toArgo)

fun JsonNode.toJsonk(): JsonElement =
    when {
        isObjectNode() -> JsonObject(fields.map { (key, value) -> key.text to value.toJsonk() }.toList())
        isArrayNode() -> JsonArray(elements.map { it.toJsonk() })
        isNullNode() -> JsonNull
        isStringValue() -> JsonString(getStringValue())
        isBooleanValue() -> JsonBoolean(getBooleanValue())
        isNumberValue() -> JsonNumber(getNumberValue())
        else -> throw IllegalArgumentException("cannot convert $this to JsonK")
    }

fun JsonElement.toArgo(): JsonNode =
    when (this) {
        is JsonObject -> `object`(properties.map { (key, value) -> JsonField(key, value.toArgo()) })
        is JsonArray -> array(elements.map { it.toArgo() })
        JsonNull -> nullNode()
        is JsonNumber -> number(valueAsString)
        is JsonString -> string(value)
        is JsonBoolean -> if (value) trueNode() else falseNode()
    }
