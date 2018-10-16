package com.natpryce.snodge.bytes

import com.natpryce.snodge.internal.replaceRange
import kotlin.test.Test
import kotlin.test.assertEquals


class ByteArrayReplaceRangeTest {
    @Test
    fun replaces_a_range_of_bytes_within_array() {
        val original = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val spliced = original.replaceRange(2..5, byteArrayOf(10, 11, 12))
        
        assertEquals(byteArrayOf(1, 2, 10, 11, 12, 7, 8).asList(), spliced.asList())
    }
    
    @Test
    fun replaces_a_range_of_bytes_at_start_of_array() {
        val original = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val spliced = original.replaceRange(0..3, byteArrayOf(10, 11, 12))
        
        assertEquals(byteArrayOf(10, 11, 12, 5, 6, 7, 8).asList(), spliced.asList())
    }
    
    @Test
    fun replaces_a_range_of_bytes_at_end_of_array() {
        val original = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val spliced = original.replaceRange(4..7, byteArrayOf(10, 11, 12))
        
        assertEquals(byteArrayOf(1, 2, 3, 4, 10, 11, 12).asList(), spliced.asList())
    }
}
