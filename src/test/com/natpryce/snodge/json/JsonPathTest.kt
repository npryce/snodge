package com.natpryce.snodge.json

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.snodge.json.JsonPath.Companion
import org.junit.Test

class JsonPathTest {
    @Test
    fun canQueryElementsOfPath() {
        val path = JsonPath("a", 1, "b", 2, "c")
        
        assertThat(path.size(), equalTo(5))
        assertThat(path.at(0), equalTo("a" as Any))
        assertThat(path.at(1), equalTo(1 as Any))
        assertThat(path.at(2), equalTo("b" as Any))
        assertThat(path.at(3), equalTo(2 as Any))
        assertThat(path.at(4), equalTo("c" as Any))
    }
    
    @Test
    fun negativeElementsIndexFromEndOfPath() {
        val path = JsonPath("a", 1, "b", 2, "c")
        
        assertThat(path.at(-5), equalTo("a" as Any))
        assertThat(path.at(-4), equalTo(1 as Any))
        assertThat(path.at(-3), equalTo("b" as Any))
        assertThat(path.at(-2), equalTo(2 as Any))
        assertThat(path.at(-1), equalTo("c" as Any))
    }
    
    @Test
    @Throws(Exception::class)
    fun returnsSameHashCodeForSamePathNoMatterHowItIsConstructed() {
        val path = JsonPath("a", 1, "b", 2, "c")
        
        assertThat(path.hashCode(), equalTo(Companion.root.extend("a").extend(1).extend("b").extend(2).extend("c").hashCode()))
        assertThat(path.hashCode(), equalTo(Companion.root.extend("a", 1, "b", 2, "c").hashCode()))
    }
    
    @Test
    @Throws(Exception::class)
    fun pathsAreEqualNoMatterHowTheyAreConstructed() {
        val path = JsonPath("a", 1, "b", 2, "c")
        
        assertThat(path, equalTo(Companion.root.extend("a").extend(1).extend("b").extend(2).extend("c")))
        assertThat(path, equalTo(Companion.root.extend("a", 1, "b", 2, "c")))
    }
    
    @Test
    @Throws(Exception::class)
    fun canReplaceElementAtPath() {
        val original = obj(
            "a" to list(
                        obj(
                            "x" to 10,
                            "y" to 20),
                        obj(
                            "x" to 100,
                            "y" to 200)),
            "b" to "bubbles")
        
        assertReplacement(original, JsonPath("a", 1, "x"), JsonPrimitive(-99), obj(
            "a" to list(
                        obj(
                            "x" to 10,
                            "y" to 20),
                        obj(
                            "x" to -99,
                            "y" to 200)),
            "b" to "bubbles"))
        
        assertReplacement(original, JsonPath.root, JsonPrimitive("just this"), JsonPrimitive("just this"))
    }
    
    @Test
    @Throws(Exception::class)
    fun canReplaceElementInSingletonArrayAtPath() {
        val original = list(1, 2, 3, list(1))
        
        assertReplacement(original, JsonPath(3, 0), JsonPrimitive(-99),
            list(1, 2, 3, list(-99)))
    }
    
    @Test
    fun replacingAnObjectFieldDoesNotChangeOrderOfElementsWhenSerialised() {
        val original = obj(
            "a" to 1,
            "x" to 2,
            "b" to 3,
            "y" to 4)
        
        assertThat(original.toString(), equalTo("{\"a\":1,\"x\":2,\"b\":3,\"y\":4}"))
        
        val replaced = JsonPath("x").replace(original, JsonPrimitive(99)) as JsonObject
        
        assertThat(replaced.toString(), equalTo("{\"a\":1,\"x\":99,\"b\":3,\"y\":4}"))
    }
    
    private fun assertReplacement(original: JsonElement, path: JsonPath, splice: JsonElement, expected: JsonElement) {
        assertThat(path.replace(original, splice), equalTo(expected))
    }
}
