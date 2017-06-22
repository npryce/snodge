package com.natpryce.snodge.text

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.isEmpty
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.Random
import com.natpryce.snodge.repeat
import org.junit.Assert.assertTrue
import org.junit.Test

class StringSpliceTest {
    @Test
    fun `splices text into string`() {
        val mutagen = repeat(100, splice("foo"))
        val original = "alice"
        
        val spliceLocations = mutableSetOf<Int>()
        
        mutagen(Random(), original).map { it.value }.forEach {
            assertThat("original: $original, mutant: $it", it, containsSubstring("foo"))
            
            spliceLocations += it.indexOf("foo")
        }
        
        assertThat("text should be spliced into string at different locations", spliceLocations, !isEmpty)
    }
    
    @Test
    fun `can mutate the replaced substring`() {
        val toUpperCase: Mutagen<String> = fun(_: Random, original: String) =
            sequenceOf(lazy { original.toUpperCase() })
        
        val mutagen = repeat(100, splice(toUpperCase))
        val original = "alice"
        
        mutagen(Random(), original).map { it.value }.forEach { mutant ->
            assertTrue("original: $original, mutant: $mutant", mutant.find { it.isUpperCase() } != null)
        }
    }
}
