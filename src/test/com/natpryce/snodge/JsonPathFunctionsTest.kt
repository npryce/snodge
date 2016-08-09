package com.natpryce.snodge

import com.natpryce.snodge.JsonPath.functions.endsWith
import com.natpryce.snodge.JsonPath.functions.startsWith
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonPathFunctionsTest {
    @Test
    fun pathEndsWith() {
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), endsWith("a", "b", "c"))
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), endsWith("b", "c"))
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), endsWith("c"))
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), endsWith())
        
        assertPathMeetsPredicate(JsonPath.of(), endsWith())
        
        assertPathMeetsPredicate(JsonPath.of("a", "b", 1), endsWith(1))
        assertPathMeetsPredicate(JsonPath.of("a", "b", 1), endsWith("b", 1))
        
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), not(endsWith("x", "a", "b", "c")))
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), not(endsWith("a", "b", "c", "x")))
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), not(endsWith("x")))
        assertPathMeetsPredicate(JsonPath.of("a", "b", 1), not(endsWith("x")))
        
        assertPathMeetsPredicate(JsonPath.of(), not(endsWith("x")))
        
        // Different to JavaScript
        assertPathMeetsPredicate(JsonPath.of("a", "b", "1"), not(endsWith(1)))
        assertPathMeetsPredicate(JsonPath.of("a", "b", 1), not(endsWith("1")))
    }
    
    @Test
    fun pathStartsWith() {
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), startsWith("a", "b", "c"))
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), startsWith("a", "b"))
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), startsWith("a"))
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), startsWith())
        
        assertPathMeetsPredicate(JsonPath.of(), startsWith())
        
        assertPathMeetsPredicate(JsonPath.of(1, "a", "b"), startsWith(1))
        assertPathMeetsPredicate(JsonPath.of(1, "a", "b"), startsWith(1, "a"))
        
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), not(startsWith("x", "a", "b", "c")))
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), not(startsWith("a", "b", "c", "x")))
        assertPathMeetsPredicate(JsonPath.of("a", "b", "c"), not(startsWith("x")))
        assertPathMeetsPredicate(JsonPath.of(1, "a", "b"), not(startsWith("x")))
        
        assertPathMeetsPredicate(JsonPath.of(), not(startsWith("x")))
        
        // Different to JavaScript
        assertPathMeetsPredicate(JsonPath.of("1", "a", "b"), not(startsWith(1)))
        assertPathMeetsPredicate(JsonPath.of(1, "a", "b"), not(startsWith("1")))
    }
    
    private fun not(p: (JsonPath)->Boolean): (JsonPath)->Boolean = {!p(it)}
    
    private fun assertPathMeetsPredicate(path: JsonPath, p: (JsonPath)->Boolean) {
        assertTrue("$path should meet predicate $p", p(path))
    }
}
