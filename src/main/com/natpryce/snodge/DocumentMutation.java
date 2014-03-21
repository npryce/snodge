package com.natpryce.snodge;

import com.google.common.base.Function;
import com.google.gson.JsonElement;

public interface DocumentMutation extends Function<JsonElement, JsonElement> {
}
