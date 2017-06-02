package com.natpryce.snodge

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.Random

class MutagenRepeatTest {
    @Test
    fun `applies a mutagen multiple times`() {
        val mutagen = repeat(3, always("a", "b"))
        
        val mutants = mutagen(Random(), "original").map { it.value }.toList()
        
        assertThat(mutants, equalTo(listOf("a", "b", "a", "b", "a", "b")))
    }
}
