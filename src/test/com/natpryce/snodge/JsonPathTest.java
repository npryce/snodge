package com.natpryce.snodge;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Test;

import static com.natpryce.snodge.JsonBuilders.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class JsonPathTest
{
    @Test
    public void canQueryElementsOfPath() {
        JsonPath path = JsonPath.of("a", 1, "b", 2, "c");

        assertThat(path.size(), equalTo(5));
        assertThat(path.at(0), equalTo((Object)"a"));
        assertThat(path.at(1), equalTo((Object)1));
        assertThat(path.at(2), equalTo((Object)"b"));
        assertThat(path.at(3), equalTo((Object)2));
        assertThat(path.at(4), equalTo((Object)"c"));
    }

    @Test
    public void negativeElementsIndexFromEndOfPath() {
        JsonPath path = JsonPath.of("a", 1, "b", 2, "c");

        assertThat(path.at(-5), equalTo((Object)"a"));
        assertThat(path.at(-4), equalTo((Object)1));
        assertThat(path.at(-3), equalTo((Object)"b"));
        assertThat(path.at(-2), equalTo((Object)2));
        assertThat(path.at(-1), equalTo((Object)"c"));
    }

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

	@Test
	public void canReplaceElementInSingletonArrayAtPath() throws Exception
	{
		JsonElement original = list(1,2,3,list(1));

		assertReplacement(original, JsonPath.of(3,0), new JsonPrimitive(-99),
                list(1,2,3,list(-99)));
	}

    @Test
    public void replacingAnObjectFieldDoesNotChangeOrderOfElementsWhenSerialised() {
        JsonObject original = object(
                withField("a", 1),
                withField("x", 2),
                withField("b", 3),
                withField("y", 4));

        assertThat(original.toString(), equalTo("{\"a\":1,\"x\":2,\"b\":3,\"y\":4}"));

        JsonObject replaced = (JsonObject) JsonPath.of("x").replace(original, new JsonPrimitive(99));

        assertThat(replaced.toString(), equalTo("{\"a\":1,\"x\":99,\"b\":3,\"y\":4}"));
    }

	private void assertReplacement(JsonElement original, JsonPath path, JsonElement splice, JsonElement expected)
	{
		assertThat(path.replace(original, splice), equalTo(expected));
	}
}
