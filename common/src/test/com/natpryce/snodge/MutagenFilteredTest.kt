package com.natpryce.snodge

import kotlin.test.Test
import kotlin.test.assertEquals

class MutagenFilteredTest {
    @Test
    fun applies_a_mutagen_only_if_the_original_meets_some_predicate() {
        val mutagen = always("bob").filtered { original -> original == "alice" }
    
        assertEquals("bob", mutagen(Random(), "alice").firstOrNull()?.value)
        assertEquals(emptyList(), mutagen(Random(), "carol").toList())
    }
}