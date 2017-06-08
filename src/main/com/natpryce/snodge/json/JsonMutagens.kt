package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonBoolean
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonNull
import com.natpryce.jsonk.JsonNumber
import com.natpryce.jsonk.JsonObject
import com.natpryce.jsonk.JsonString
import com.natpryce.jsonk.append
import com.natpryce.jsonk.remove
import com.natpryce.jsonk.removeProperty
import com.natpryce.jsonk.replace
import com.natpryce.jsonk.withProperty
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.combine
import com.natpryce.snodge.filtered
import com.natpryce.snodge.reflect.troublesomeClasses
import java.util.Collections


fun addArrayElement(newElement: JsonElement) = JsonMutagen { _, elementToMutate ->
    if (elementToMutate is JsonArray) {
        sequenceOf(lazy { elementToMutate.append(newElement) })
    }
    else {
        emptySequence()
    }
}

fun addObjectProperty(newElement: JsonElement) = JsonMutagen { _, elementToMutate ->
    if (elementToMutate is JsonObject) {
        sequenceOf(lazy {
            elementToMutate.withProperty((elementToMutate.newProperty("x") to newElement))
        })
    }
    else {
        emptySequence()
    }
}

private fun JsonObject.newProperty(basename: String) =
    (sequenceOf(basename) + generateSequence(1, { it + 1 }).map { i -> "${basename}_$i" })
        .filterNot { it in this }
        .first()

fun removeJsonElement() = JsonMutagen { _, elementToMutate ->
    when (elementToMutate) {
        is JsonObject -> elementToMutate.keys.asSequence().map { key ->
            lazy { elementToMutate.removeProperty(key) }
        }
        is JsonArray -> elementToMutate.indices.asSequence().map { i ->
            lazy { elementToMutate.remove(i) }
        }
        else -> emptySequence()
    }
}

fun replaceJsonElement(replacement: JsonElement) = JsonMutagen { _, elementToMutate ->
    when (elementToMutate) {
        is JsonObject -> elementToMutate.keys.asSequence().map { key ->
            lazy { elementToMutate.withProperty(key to replacement) }
        }
        is JsonArray -> elementToMutate.indices.asSequence().map { i ->
            lazy { elementToMutate.replace(i, replacement) }
        }
        else -> emptySequence()
    }
}

fun reorderObjectProperties() = JsonMutagen { _, elementToMutate ->
    if (elementToMutate is JsonObject) {
        sequenceOf(lazy {
            val objectProperties = elementToMutate.properties.toList()
            Collections.shuffle(objectProperties)
            JsonObject(objectProperties)
        })
    }
    else {
        emptySequence()
    }
}


fun reflectionMutagens(): Mutagen<JsonElement> =
    troublesomeClasses()
        .map { replaceJsonElement(JsonString(it)).filtered { it is JsonString } }
        .let { combine(it) }


private val exampleElements = listOf(
    JsonNull,
    JsonBoolean(true),
    JsonBoolean(false),
    JsonNumber(99),
    JsonNumber(-99),
    JsonNumber(0.0),
    JsonNumber(1.0),
    JsonNumber(-1.0),
    JsonString("a string"),
    JsonArray(),
    JsonObject())


/**
 * @return Applies all the JSON mutations implemented in the Snodge library.
 */
fun allJsonMutagens() =
    combine(
        combine(exampleElements.map { exampleElement ->
            combine(
                replaceJsonElement(exampleElement),
                addArrayElement(exampleElement),
                addObjectProperty(exampleElement)
            )
        }),
        removeJsonElement(),
        reorderObjectProperties(),
        reflectionMutagens())
