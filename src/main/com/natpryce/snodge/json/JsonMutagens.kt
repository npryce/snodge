package com.natpryce.snodge.json

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.natpryce.snodge.reflect.troublesomeClasses
import java.util.ArrayList
import java.util.Collections


fun addArrayElement(newElement: JsonElement) = object : JsonMutagen() {
    override fun invoke(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
        if (elementToMutate is JsonArray) {
            sequenceOf(lazy {
                pathToElement.map(document) { array ->
                    JsonArray().apply {
                        addAll(array.asJsonArray)
                        add(newElement)
                    }
                }
            })
        }
        else {
            emptySequence()
        }
}

fun addObjectProperty(newElement: JsonElement) = object : JsonMutagen() {
    override fun invoke(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
        if (elementToMutate.isJsonObject) {
            sequenceOf(lazy {
                pathToElement.map(document) {
                    JsonObject().apply {
                        it.asJsonObject.entrySet().forEach { (key, value) -> this.add(key, value) }
                        this.add(this.newProperty("x"), newElement)
                    }
                }
            })
        }
        else {
            emptySequence()
        }
    
    
    private fun JsonObject.newProperty(basename: String) =
        (sequenceOf(basename) + generateSequence(1, { it + 1 }).map { i -> "${basename}_$i" })
            .filterNot { has(it) }
            .first()
}

fun removeJsonElement() = object : JsonMutagen() {
    override fun invoke(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
        if (pathToElement.isRoot) emptySequence() else sequenceOf(lazy { pathToElement.remove(document) })
}

fun replaceJsonElement(replacement: JsonElement) = object : JsonMutagen() {
    override fun invoke(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
        sequenceOf(lazy { pathToElement.replace(document, replacement) })
}


fun reorderObjectProperties() = object : JsonMutagen() {
    override fun invoke(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
        if (elementToMutate.isJsonObject) {
            sequenceOf(lazy {
                pathToElement.map(document) {
                    val objectProperties = ArrayList(it.asJsonObject.entrySet())
                    Collections.shuffle(objectProperties)
                    JsonObject().apply {
                        for ((key, value) in objectProperties) {
                            add(key, value)
                        }
                    }
                }
            })
        }
        else {
            emptySequence()
        }
}


fun reflectionMutagens(): JsonMutagen =
    troublesomeClasses()
        .map { replaceJsonElement(JsonPrimitive(it)).ifElement { it is JsonPrimitive && it.isString } }
        .let { combine(it) }



private val exampleElements = listOf(
    JsonNull.INSTANCE,
    JsonPrimitive(true),
    JsonPrimitive(false),
    JsonPrimitive(99),
    JsonPrimitive(-99),
    JsonPrimitive("a string"),
    JsonArray(),
    JsonObject())


/**
 * @return Applies all the JSON mutations implemented in the Snodge library.
 */
fun allJsonMutagens() =
    combine(combine(exampleElements.map { exampleElement ->
        combine(
            replaceJsonElement(exampleElement),
            addArrayElement(exampleElement),
            addObjectProperty(exampleElement)
        )
    }),
        removeJsonElement(),
        reorderObjectProperties(),
        reflectionMutagens())
