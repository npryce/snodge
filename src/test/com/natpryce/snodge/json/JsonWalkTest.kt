package com.natpryce.snodge.json

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonPrimitive
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class JsonWalkTest {
    @Test
    fun returnsObjectProperties() {
        assertWalk(obj("a" to 1, "b" to 2),
            JsonPath.root,
            JsonPath.of("a"),
            JsonPath.of("b"))
    }
    
    @Test
    fun returnsNestedObjectProperties() {
        assertWalk(obj("a" to 1, "b" to obj("c" to 2)),
            JsonPath.root,
            JsonPath.of("a"),
            JsonPath.of("b"),
            JsonPath.of("b", "c"))
    }
    
    @Test
    fun returnsArrayElements() {
        assertWalk(list("a", "b"),
            JsonPath.root,
            JsonPath.of(0),
            JsonPath.of(1))
    }
    
    @Test
    fun returnsNestedArrayElements() {
        assertWalk(list(list(1, 2), list(3, 4)),
            JsonPath.root,
            JsonPath.of(0),
            JsonPath.of(0, 0),
            JsonPath.of(0, 1),
            JsonPath.of(1),
            JsonPath.of(1, 0),
            JsonPath.of(1, 1))
    }
    
    @Test
    fun bigHairyJsonExample() {
        val json = obj(
            "a" to obj(
                "b" to list(1, 2, 3)),
            "c" to list(
                obj("d" to 1),
                obj("d" to 2)))
        
        assertWalk(json,
            JsonPath.root,
            JsonPath.of("a"),
            JsonPath.of("a", "b"),
            JsonPath.of("a", "b", 0),
            JsonPath.of("a", "b", 1),
            JsonPath.of("a", "b", 2),
            JsonPath.of("c"),
            JsonPath.of("c", 0),
            JsonPath.of("c", 0, "d"),
            JsonPath.of("c", 1),
            JsonPath.of("c", 1, "d"))
    }
    
    @Test
    fun returnsRootForJsonPrimitive() {
        assertWalk(JsonPrimitive("bob"),
            JsonPath.root)
    }
    
    @Test
    fun returnsRootForJsonNull() {
        assertWalk(JsonNull.INSTANCE,
            JsonPath.root)
    }
    
    @Test
    fun returnsRootForEmptyObject() {
        assertWalk(obj(),
            JsonPath.root)
    }
    
    @Test
    fun returnsRootForEmptyArray() {
        assertWalk(list(),
            JsonPath.root)
    }
    
    private fun assertWalk(json: JsonElement, vararg expected: JsonPath) {
        val actualAsStrings = json.walk().map { it.toString() }.toSortedSet()
        val expectedAsStrings = expected.map { it.toString() }.toSortedSet()
        
        assertThat(actualAsStrings, equalTo(expectedAsStrings))
    }
}
