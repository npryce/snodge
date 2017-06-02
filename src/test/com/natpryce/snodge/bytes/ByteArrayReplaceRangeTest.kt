package com.natpryce.snodge.bytes

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.snodge.internal.replaceRange
import org.junit.Test


class ByteArrayReplaceRangeTest {
    @Test
    fun `replaces a range of bytes within array`() {
        val original = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val spliced = original.replaceRange(2..5, byteArrayOf(10, 11, 12))
        
        assertThat(spliced.asList(), equalTo(byteArrayOf(1, 2, 10, 11, 12, 7, 8).asList()))
    }
    
    @Test
    fun `replaces a range of bytes at start of array`() {
        val original = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val spliced = original.replaceRange(0..3, byteArrayOf(10, 11, 12))
        
        assertThat(spliced.asList(), equalTo(byteArrayOf(10, 11, 12, 5, 6, 7, 8).asList()))
    }
    
    @Test
    fun `replaces a range of bytes at end of array`() {
        val original = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val spliced = original.replaceRange(4..7, byteArrayOf(10, 11, 12))
        
        assertThat(spliced.asList(), equalTo(byteArrayOf(1, 2, 3, 4, 10, 11, 12).asList()))
    }
}
