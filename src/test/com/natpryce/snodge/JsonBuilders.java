package com.natpryce.snodge;

import com.google.gson.*;
import com.natpryce.snodge.internal.JsonFunctions;

import java.util.Map;

import static com.natpryce.snodge.internal.JsonFunctions.entry;
import static java.util.Arrays.asList;

public class JsonBuilders {
    @SafeVarargs
	public static JsonObject object(Map.Entry<String, Object>... properties) {
		return toJsonObject(asList(properties));
	}

	public static Map.Entry<String, Object> withNullField(String name) {
		return entry(name, null);
	}

	public static Map.Entry<String, Object> withField(String name, String value) {
		return entry(name, (Object) value);
	}

	public static Map.Entry<String, Object> withField(String name, Integer value) {
		return entry(name, (Object) value);
	}

    public static Map.Entry<String, Object> withField(String name, JsonElement value) {
		return entry(name, (Object) value);
	}

    public static JsonArray list(Object... elements) {
		final JsonArray jsonArray = new JsonArray();
		for (Object element : elements)
		{
			jsonArray.add(asJsonElement(element));
		}
		return jsonArray;
	}

	private static JsonObject toJsonObject(Iterable<Map.Entry<String, Object>> entries)
	{
		JsonObject json = new JsonObject();
		for (Map.Entry<String, Object> property : entries) {
			json.add(property.getKey(), asJsonElement(property.getValue()));
		}
		return json;
	}

	private static JsonElement asJsonElement(Object element)
	{
		if (element == null) {
			return JsonNull.INSTANCE;
		}
		else if (element instanceof JsonElement) {
			return (JsonElement)element;
		}
		else if (element instanceof String) {
			return new JsonPrimitive((String)element);
		}
		else if (element instanceof Boolean) {
			return new JsonPrimitive((Boolean)element);
		}
		else if (element instanceof Number) {
			return new JsonPrimitive((Number)element);
		}
		else if (element instanceof Character) {
			return new JsonPrimitive((Character)element);
		}
		else {
			throw new IllegalArgumentException("cannot turn a " + element.getClass().getName() + " to a JsonElement");
		}
	}
}
