package com.natpryce.jsonk

import java.math.BigDecimal

sealed class JsonElement

data class JsonObject(
    val properties: Map<String, JsonElement> = emptyMap()
) : JsonElement(), Map<String, JsonElement> by properties {
    
    constructor(properties: Iterable<Pair<String, JsonElement>>) :
        this(linkedMapOf<String, JsonElement>().apply { putAll(properties) })
    
    constructor(vararg properties: Pair<String, JsonElement>) :
        this(properties.toList())
}

operator fun JsonObject.plus(anotherProperty: Pair<String, JsonElement>) =
    copy(properties = this.properties + anotherProperty)

fun JsonObject.plus(vararg moreProperties: Pair<String, JsonElement>) =
    copy(properties = this.properties + moreProperties)


data class JsonArray(
    val elements: List<JsonElement>
) : JsonElement(), List<JsonElement> by elements {
    constructor(vararg elements: JsonElement) : this(listOf(*elements))
}

operator fun JsonArray.plus(anotherElement: JsonElement) =
    copy(elements = elements + anotherElement)

operator fun JsonArray.plus(moreElements: Iterable<JsonElement>) =
    copy(elements = elements + moreElements)

fun JsonArray.plus(vararg moreElements: JsonElement) =
    copy(elements = elements + moreElements)

data class JsonNumber(val valueAsString: String) : JsonElement() {
    constructor(value: Int) : this(value.toString())
    constructor(value: Long) : this(value.toString())
    constructor(value: Double) : this(value.toString())
    
    fun toInt() = valueAsString.toInt()
    fun toLong() = valueAsString.toLong()
    fun toDouble() = valueAsString.toDouble()
    fun toBigDecimal() = BigDecimal(valueAsString)
}

data class JsonString(val value: String) : JsonElement()

data class JsonBoolean(val value: Boolean) : JsonElement()

object JsonNull : JsonElement()
