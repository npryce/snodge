package com.natpryce.snodge.bytes

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isEmpty
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.Random
import com.natpryce.snodge.repeat
import org.junit.Assert.assertTrue
import org.junit.Test

class ByteArraySpliceTest {
    @Test
    fun `splices bytes into array`() {
        val mutagen = repeat(100, splice(byteArrayOf(1, 2)))
        val original = byteArrayOf(100, 101, 102, 103)
        
        val spliceLocations = mutableSetOf<Int>()
        
        mutagen(Random(), original).map { it.value }.forEach {
            assertTrue("original: $original, mutant: $it", 1 in it && 2 in it)
            
            spliceLocations += it.indexOf(1)
        }
        
        assertThat("bytes should be spliced into array at different locations", spliceLocations, !isEmpty)
    }
    
    @Test
    fun `can mutate the replaced bytes`() {
        val sliceMutagen: Mutagen<ByteArray> =
            fun(_: Random, original: ByteArray) =
                sequenceOf(lazy { original.map { (it + 10).toByte() }.toByteArray() })
        
        val mutagen = repeat(100, splice(sliceMutagen))
        val original = byteArrayOf(1, 2, 3, 4)
        
        mutagen(Random(), original).map { it.value }.forEach { mutant ->
            assertTrue("original: $original, mutant: $mutant", mutant.find { it > 10 } != null)
        }
    }
}
