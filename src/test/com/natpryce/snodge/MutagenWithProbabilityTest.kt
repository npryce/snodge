package com.natpryce.snodge

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isEmpty
import org.junit.Test

class MutagenWithProbabilityTest {
    @Test
    fun `mutates with given probability`() {
        val mutagen = always("bob").withProbability(0.25)
    
        assertThat(mutagen(randomReturning(0.0), "alice").firstOrNull()?.value, equalTo("bob"))
        assertThat(mutagen(randomReturning(0.1), "alice").firstOrNull()?.value, equalTo("bob"))
        assertThat(mutagen(randomReturning(0.25), "alice").toList(), isEmpty)
        assertThat(mutagen(randomReturning(0.3), "alice").toList(), isEmpty)
        assertThat(mutagen(randomReturning(1.0), "alice").toList(), isEmpty)
    }
    
    fun randomReturning(value: Double) = object: java.util.Random() {
        override fun nextDouble() = value
    }
}

