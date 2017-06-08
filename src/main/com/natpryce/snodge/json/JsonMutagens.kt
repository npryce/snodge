package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonBoolean
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonNull
import com.natpryce.jsonk.JsonNumber
import com.natpryce.jsonk.JsonObject
import com.natpryce.jsonk.JsonString
import com.natpryce.jsonk.plusElement
import com.natpryce.jsonk.withProperty
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.combine
import com.natpryce.snodge.filtered
import com.natpryce.snodge.reflect.troublesomeClasses
import java.util.Collections


fun addArrayElement(newElement: JsonElement) = JsonMutagen { _, document, pathToElement, elementToMutate ->
    if (elementToMutate is JsonArray) {
        sequenceOf(lazy { pathToElement.replace(document, elementToMutate.plusElement(newElement)) })
    }
    else {
        emptySequence()
    }
}

fun addObjectProperty(newElement: JsonElement) = JsonMutagen { _, document, pathToElement, elementToMutate ->
    if (elementToMutate is JsonObject) {
        sequenceOf(lazy {
            pathToElement.replace(document, elementToMutate.withProperty((elementToMutate.newProperty("x") to newElement)))
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

fun removeJsonElement() = JsonMutagen { _, document, pathToElement, _ ->
    if (pathToElement.isRoot) emptySequence() else sequenceOf(lazy { pathToElement.remove(document) })
}

fun replaceJsonElement(replacement: JsonElement) = JsonMutagen { _, document, pathToElement, _ ->
    sequenceOf(lazy { pathToElement.replace(document, replacement) })
}

fun reorderObjectProperties() = JsonMutagen { _, document, pathToElement, elementToMutate ->
    if (elementToMutate is JsonObject) {
        sequenceOf(lazy {
            val objectProperties = elementToMutate.properties.toList()
            Collections.shuffle(objectProperties)
            pathToElement.replace(document, JsonObject(linkedMapOf(*objectProperties.toTypedArray())))
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
