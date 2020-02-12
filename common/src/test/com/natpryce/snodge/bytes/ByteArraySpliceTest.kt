package com.natpryce.snodge.bytes

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.repeat
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class ByteArraySpliceTest {
    @Test
    fun splices_bytes_into_array() {
        val mutagen = repeat(100, splice(byteArrayOf(1, 2)))
        val original = byteArrayOf(100, 101, 102, 103)
        
        val spliceLocations = mutableSetOf<Int>()
        
        mutagen(Random.Default, original).map { it.value }.forEach {
            assertTrue(1 in it && 2 in it, "original: $original, mutant: $it")
            
            spliceLocations += it.indexOf(1)
        }
        
        assertTrue(spliceLocations.size > 1, "bytes should be spliced into array at different locations")
    }
    
    @Test
    fun can_mutate_the_replaced_bytes() {
        val sliceMutagen: Mutagen<ByteArray> =
            fun(_: Random, original: ByteArray) =
                sequenceOf(lazy { original.map { (it + 10).toByte() }.toByteArray() })
        
        val mutagen = repeat(100, splice(sliceMutagen))
        val original = byteArrayOf(1, 2, 3, 4)
        
        mutagen(Random.Default, original).map { it.value }.forEach { mutant ->
            assertTrue(mutant.find { it > 10 } != null, "original: $original, mutant: $mutant")
        }
    }
}
