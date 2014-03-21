package com.natpryce.snodge.internal;

import com.google.common.base.Function;
import com.google.gson.JsonElement;
import com.natpryce.snodge.DocumentMutation;

public class ElementAtPathMutation implements DocumentMutation {
    private final JsonPath path;
    private final Function<? super JsonElement, ? extends JsonElement> elementTransform;

    public ElementAtPathMutation(JsonPath path, Function<? super JsonElement, ? extends JsonElement> elementTransform) {
        this.path = path;
        this.elementTransform = elementTransform;
    }

    @Override
    public JsonElement apply(JsonElement document) throws RuntimeException {
        return path.map(document, elementTransform);
    }
}
