package com.natpryce.snodge.text

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.Random
import com.natpryce.snodge.repeat
import org.junit.Test
import kotlin.test.assertTrue

class StringSpliceTest {
    @Test
    fun splices_text_into_string() {
        val mutagen = repeat(100, splice("foo"))
        val original = "alice"
        
        val spliceLocations = mutableSetOf<Int>()
        
        mutagen(Random(), original).map { it.value }.forEach {
            assertTrue("original: $original, mutant: $it") {it.contains("foo")}
            
            spliceLocations += it.indexOf("foo")
        }
        
        assertTrue("text should be spliced into string at different locations") {spliceLocations.isNotEmpty()}
    }
    
    @Test
    fun can_mutate_the_replaced_substring() {
        val toUpperCase: Mutagen<String> = fun(_: Random, original: String) =
            sequenceOf(lazy { original.toUpperCase() })
        
        val mutagen = repeat(100, splice(toUpperCase))
        val original = "alice"
        
        mutagen(Random(), original).map { it.value }.forEach { mutant ->
            assertTrue("original: $original, mutant: $mutant") {mutant.any { it == it.toUpperCase() }}
        }
    }
}
