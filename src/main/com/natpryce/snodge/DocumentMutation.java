package com.natpryce.snodge;

import com.google.common.base.Function;
import com.google.gson.JsonElement;

/**
 * A function that maps a JSON document to a mutated version of the document.
 */
public interface DocumentMutation extends Function<JsonElement, JsonElement> {
}
