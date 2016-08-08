package com.natpryce.snodge

import com.google.gson.JsonElement

import java.util.function.Function

/**
 * A function that maps a JSON document to a mutated version of the document.
 */
interface DocumentMutation : (JsonElement)-> JsonElement {
    fun apply(original: JsonElement) = invoke(original)
}
