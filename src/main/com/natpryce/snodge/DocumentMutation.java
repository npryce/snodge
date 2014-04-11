package com.natpryce.snodge;

import com.google.gson.JsonElement;

import java.util.function.Function;

/**
 * A function that maps a JSON document to a mutated version of the document.
 */
public interface DocumentMutation extends Function<JsonElement, JsonElement> {
}
