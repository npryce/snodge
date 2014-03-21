package com.natpryce.snodge.internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.Test;

import static com.natpryce.snodge.JsonBuilders.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class JsonPathTest
{
	@Test
	public void returnsSameHashCodeForSamePathNoMatterHowItIsConstructed() throws Exception
	{
		JsonPath path = JsonPath.of("a", 1, "b", 2, "c");

		assertEquals(path.hashCode(), JsonPath.root.extend("a").extend(1).extend("b").extend(2).extend("c").hashCode());
		assertEquals(path.hashCode(), JsonPath.root.extend("a", 1, "b", 2, "c").hashCode());
	}

	@Test
	public void pathsAreEqualNoMatterHowTheyAreConstructed() throws Exception
	{
		JsonPath path = JsonPath.of("a", 1, "b", 2, "c");

		assertEquals(path, JsonPath.root.extend("a").extend(1).extend("b").extend(2).extend("c"));
		assertEquals(path, JsonPath.root.extend("a", 1, "b", 2, "c"));
	}

	@Test
	public void canReplaceElementAtPath() throws Exception
	{
		JsonElement original = object(
				withField("a", list(
						object(
								withField("x", 10),
								withField("y", 20)),
						object(
								withField("x", 100),
								withField("y", 200)))),
				withField("b", "bubbles"));

		assertReplacement(original, JsonPath.of("a",1,"x"), new JsonPrimitive(-99), object(
				withField("a", list(
						object(
								withField("x", 10),
								withField("y", 20)),
						object(
								withField("x", -99),
								withField("y", 200)))),
				withField("b", "bubbles")));

		assertReplacement(original, JsonPath.root, new JsonPrimitive("just this"), new JsonPrimitive("just this"));
	}

	private void assertReplacement(JsonElement original, JsonPath path, JsonElement splice, JsonElement expected)
	{
		assertThat(path.replace(original, splice), equalTo(expected));
	}
}
