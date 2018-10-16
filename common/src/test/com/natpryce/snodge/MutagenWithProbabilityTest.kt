package com.natpryce.snodge

import kotlin.test.Test
import kotlin.test.assertEquals

class MutagenWithProbabilityTest {
    @Test
    fun mutates_with_given_probability() {
        val mutagen = always("bob").withProbability(0.25)
    
        assertEquals("bob", mutagen(randomReturning(0.0), "alice").firstOrNull()?.value)
        assertEquals("bob", mutagen(randomReturning(0.1), "alice").firstOrNull()?.value)
        assertEquals(emptyList(), mutagen(randomReturning(0.25), "alice").toList())
        assertEquals(emptyList(), mutagen(randomReturning(0.3), "alice").toList())
        assertEquals(emptyList(), mutagen(randomReturning(1.0), "alice").toList())
    }
    
    fun randomReturning(value: Double) = object: Random() {
        override fun nextDouble() = value
    }
}

