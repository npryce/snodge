package com.natpryce.snodge.json

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.ArrayList
import java.util.Collections


fun AddArrayElement(newElement: JsonElement): JsonNodeMutagen =
    fun(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
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


fun AddObjectProperty(newElement: JsonElement): JsonNodeMutagen =
    fun(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
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


fun RemoveJsonElement(): JsonNodeMutagen =
    fun(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
        if (pathToElement.isRoot) emptySequence() else sequenceOf(lazy { pathToElement.remove(document) })


fun ReplaceJsonElement(replacement: JsonElement): JsonNodeMutagen =
    fun(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
        sequenceOf(lazy { pathToElement.replace(document, replacement) })


fun ReorderObjectProperties(): JsonNodeMutagen =
    fun(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement) =
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

