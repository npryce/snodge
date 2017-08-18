package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonNull
import com.natpryce.snodge.Random
import com.natpryce.snodge.mutant
import com.natpryce.snodge.mutants
import com.natpryce.snodge.plus
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class JsonMutagenTest {
    val random = Random()
    val mutagen = addObjectProperty(JsonNull) + addArrayElement(JsonNull)
    
    @Test
    fun can_add_null_object_property() {
        val doc = obj(
            "alice" to 1,
            "bob" to 2)
        
        assertEquals(
            actual = random.mutant(mutagen, doc),
            expected = obj(
                "alice" to 1,
                "bob" to 2,
                "x" to null))
    }
    
    @Test
    fun can_add_null_array_property() {
        val doc = list(1, 2, 3)
        
        assertEquals(
            actual = random.mutant(mutagen, doc),
            expected = list(1, 2, 3, null))
    }
    
    @Test
    fun can_return_multiple_mutations() {
        val doc = obj(
            "num" to 1,
            "list" to list(1, 2, 3))
        
        val mutatedDocs = random.mutants(mutagen, 2, doc)
        
        assertEquals(
            message = "number of mutations",
            actual = mutatedDocs.size,
            expected = 2)
        
        assertTrue(mutatedDocs.contains(obj(
            "num" to 1,
            "list" to list(1, 2, 3),
            "x" to null)))
        
        assertTrue(mutatedDocs.contains(obj(
            "num" to 1,
            "list" to list(1, 2, 3, null))))
    }
    
    @Test
    fun returns_a_random_sample_of_all_possible_mutations() {
        val doc = list(list(1, 2), list(list(3, 4), list(5, 6, list(7, 8)), list(9, 10)), list(11, 12))
        
        random.setSeed(0)
        val mutatedDocs = random.mutants(mutagen, 2, original = doc)
        
        assertEquals(
            message = "number of mutations",
            actual = mutatedDocs.size,
            expected = 2)
        
        random.setSeed(99)
        assertNotEquals(
            random.mutants(mutagen, 2, original = doc),
            random.mutants(mutagen, 2, original = doc))
    }
    
    @Test
    fun will_not_return_more_mutations_than_can_be_generated_by_the_mutagens() {
        val doc = list(list(1, 2), list(3, 4))
        
        assertEquals(
            message = "number of mutations",
            actual = random.mutants(mutagen, 10, original = doc).size,
            expected = 3)
    }
    
}
