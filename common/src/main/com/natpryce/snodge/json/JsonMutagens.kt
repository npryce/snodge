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
import com.natpryce.snodge.plus
import com.natpryce.snodge.reflect.troublesomeClasses


fun addArrayElement(newElement: JsonElement) = JsonMutagen { _, elementToMutate: JsonArray ->
    sequenceOf(lazy { elementToMutate.append(newElement) })
}

fun addObjectProperty(newElement: JsonElement) = JsonMutagen { _, elementToMutate: JsonObject ->
    sequenceOf(lazy {
        elementToMutate.withProperty((elementToMutate.newProperty("x") to newElement))
    })
}

private fun JsonObject.newProperty(basename: String) =
    (sequenceOf(basename) + generateSequence(1, { it + 1 }).map { i -> "${basename}_$i" })
        .filterNot { it in this }
        .first()

fun removeJsonObjectProperty() = JsonMutagen { _, elementToMutate: JsonObject ->
    elementToMutate.keys.asSequence().map { key ->
        lazy { elementToMutate.removeProperty(key) }
    }
}

fun removeJsonArrayElement() = JsonMutagen { _, elementToMutate: JsonArray ->
    elementToMutate.indices.asSequence().map { i ->
        lazy { elementToMutate.remove(i) }
    }
}

fun removeJsonElement() =
    removeJsonObjectProperty() + removeJsonArrayElement()

fun replaceJsonObjectProperty(replacement: JsonElement) = JsonMutagen { _, elementToMutate: JsonObject ->
    elementToMutate.keys.asSequence().map { key ->
        lazy { elementToMutate.withProperty(key to replacement) }
    }
}

fun replaceJsonArrayElement(replacement: JsonElement) = JsonMutagen { _, elementToMutate: JsonArray ->
    elementToMutate.indices.asSequence().map { i ->
        lazy { elementToMutate.replace(i, replacement) }
    }
}

fun replaceJsonElement(replacement: JsonElement) =
    replaceJsonObjectProperty(replacement) + replaceJsonArrayElement(replacement)

fun reorderObjectProperties() = JsonMutagen { random, elementToMutate: JsonObject ->
    sequenceOf(lazy {
        JsonObject(elementToMutate.properties.toList().shuffled(random))
    })
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
 * @return Applies a default set of JSON mutations
 */
fun defaultJsonMutagens() =
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


@Deprecated(
    message="renamed to defaultJsonMutagens",
    replaceWith = ReplaceWith("defaultJsonMutagens()", "defaultJsonMutagens"))
fun allJsonMutagens() = defaultJsonMutagens()
