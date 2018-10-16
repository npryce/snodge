package com.natpryce.snodge

import kotlin.test.Test
import kotlin.test.assertEquals

class MutagenRepeatTest {
    @Test
    fun applies_a_mutagen_multiple_times() {
        val mutagen = repeat(3, always("a", "b"))
        
        val mutants = mutagen(Random(), "original").map { it.value }.toList()
        
        assertEquals(listOf("a", "b", "a", "b", "a", "b"), mutants)
    }
}
