package com.natpryce.snodge

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isEmpty
import org.junit.Test

class MutagenFilteredTest {
    @Test
    fun `applies a mutagen only if the original meets some predicate`() {
        val mutagen = always("bob").filtered { original -> original == "alice" }
    
        assertThat(mutagen(Random(), "alice").firstOrNull()?.value, equalTo("bob"))
        assertThat(mutagen(Random(), "carol").toList(), isEmpty)
    }
}