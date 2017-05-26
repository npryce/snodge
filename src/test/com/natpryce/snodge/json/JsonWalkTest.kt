package com.natpryce.snodge.json

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonPrimitive
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class JsonWalkTest {
    @Test
    fun `returns object properties`() {
        assertWalk(obj("a" to 1, "b" to 2),
            JsonPath.root,
            JsonPath("a"),
            JsonPath("b"))
    }
    
    @Test
    fun `returns nested object properties`() {
        assertWalk(obj("a" to 1, "b" to obj("c" to 2)),
            JsonPath.root,
            JsonPath("a"),
            JsonPath("b"),
            JsonPath("b", "c"))
    }
    
    @Test
    fun `returns array elements`() {
        assertWalk(list("a", "b"),
            JsonPath.root,
            JsonPath(0),
            JsonPath(1))
    }
    
    @Test
    fun `returns nested array elements`() {
        assertWalk(list(list(1, 2), list(3, 4)),
            JsonPath.root,
            JsonPath(0),
            JsonPath(0, 0),
            JsonPath(0, 1),
            JsonPath(1),
            JsonPath(1, 0),
            JsonPath(1, 1))
    }
    
    @Test
    fun `big hairy json example`() {
        val json = obj(
            "a" to obj(
                "b" to list(1, 2, 3)),
            "c" to list(
                obj("d" to 1),
                obj("d" to 2)))
        
        assertWalk(json,
            JsonPath.root,
            JsonPath("a"),
            JsonPath("a", "b"),
            JsonPath("a", "b", 0),
            JsonPath("a", "b", 1),
            JsonPath("a", "b", 2),
            JsonPath("c"),
            JsonPath("c", 0),
            JsonPath("c", 0, "d"),
            JsonPath("c", 1),
            JsonPath("c", 1, "d"))
    }
    
    @Test
    fun `returns root for json primitive`() {
        assertWalk(JsonPrimitive("bob"),
            JsonPath.root)
    }
    
    @Test
    fun `returns root for json null`() {
        assertWalk(JsonNull.INSTANCE,
            JsonPath.root)
    }
    
    @Test
    fun `returns root for empty object`() {
        assertWalk(obj(),
            JsonPath.root)
    }
    
    @Test
    fun `returns root for empty array`() {
        assertWalk(list(),
            JsonPath.root)
    }
    
    private fun assertWalk(json: JsonElement, vararg expected: JsonPath) {
        val actualAsStrings = json.walk().map { it.toString() }.toSortedSet()
        val expectedAsStrings = expected.map { it.toString() }.toSortedSet()
        
        assertThat(actualAsStrings, equalTo(expectedAsStrings))
    }
}
