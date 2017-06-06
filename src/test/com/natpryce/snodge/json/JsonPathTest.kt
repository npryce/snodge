package com.natpryce.snodge.json

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonNumber
import com.natpryce.jsonk.JsonObject
import com.natpryce.jsonk.toJsonString
import com.natpryce.snodge.json.JsonPath.Companion.root
import org.junit.Test

class JsonPathTest {
    @Test
    fun `can query elements of path`() {
        val path = JsonPath("a", 1, "b", 2, "c")
        
        assertThat(path.size(), equalTo(5))
        assertThat(path[0], equalTo("a" as Any))
        assertThat(path[1], equalTo(1 as Any))
        assertThat(path[2], equalTo("b" as Any))
        assertThat(path[3], equalTo(2 as Any))
        assertThat(path[4], equalTo("c" as Any))
    }
    
    @Test
    fun `negative indices index from end of path`() {
        val path = JsonPath("a", 1, "b", 2, "c")
        
        assertThat(path[-5], equalTo("a" as Any))
        assertThat(path[-4], equalTo(1 as Any))
        assertThat(path[-3], equalTo("b" as Any))
        assertThat(path[-2], equalTo(2 as Any))
        assertThat(path[-1], equalTo("c" as Any))
    }
    
    @Test
    fun `returns same hash code for same path no matter how it is constructed`() {
        val path = JsonPath("a", 1, "b", 2, "c")
        
        assertThat(path.hashCode(), equalTo(root.extend("a").extend(1).extend("b").extend(2).extend("c").hashCode()))
        assertThat(path.hashCode(), equalTo(root.extend("a", 1, "b", 2, "c").hashCode()))
    }
    
    @Test
    fun `paths are equal no matter how they are constructed`() {
        val path = JsonPath("a", 1, "b", 2, "c")
        
        assertThat(path, equalTo(root.extend("a").extend(1).extend("b").extend(2).extend("c")))
        assertThat(path, equalTo(root.extend("a", 1, "b", 2, "c")))
    }
    
    @Test
    fun `can replace element at path`() {
        val original = obj(
            "a" to list(
                        obj(
                            "x" to 10,
                            "y" to 20),
                        obj(
                            "x" to 100,
                            "y" to 200)),
            "b" to "bubbles")
        
        assertReplacement(original, JsonPath("a", 1, "x"), JsonNumber(-99), obj(
            "a" to list(
                        obj(
                            "x" to 10,
                            "y" to 20),
                        obj(
                            "x" to -99,
                            "y" to 200)),
            "b" to "bubbles"))
        
        assertReplacement(original, JsonPath.root, JsonNumber("just this"), JsonNumber("just this"))
    }
    
    @Test
    fun `can replace element in singleton array at path`() {
        val original = list(1, 2, 3, list(1))
        
        assertReplacement(original, JsonPath(3, 0), JsonNumber(-99),
            list(1, 2, 3, list(-99)))
    }
    
    @Test
    fun `replacing an object field does not change order of elements when serialised`() {
        val original = obj(
            "a" to 1,
            "x" to 2,
            "b" to 3,
            "y" to 4)
        
        assertThat(original.toJsonString(), equalTo("{\"a\":1,\"x\":2,\"b\":3,\"y\":4}"))
        
        val replaced = JsonPath("x").replace(original, JsonNumber(99)) as JsonObject
        
        assertThat(replaced.toJsonString(), equalTo("{\"a\":1,\"x\":99,\"b\":3,\"y\":4}"))
    }
    
    private fun assertReplacement(original: JsonElement, path: JsonPath, splice: JsonElement, expected: JsonElement) {
        assertThat(path.replace(original, splice), equalTo(expected))
    }
}
