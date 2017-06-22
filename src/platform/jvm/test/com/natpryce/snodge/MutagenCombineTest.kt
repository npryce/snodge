package com.natpryce.snodge

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class MutagenCombineTest {
    @Test
    fun `combines two mutagens, returning mutants from all of them`() {
        val mutagen = always("alice") + always("bob") + always("carol")
        val mutants = mutagen(Random(), "original").map { it.value }.toSet()
        
        assertThat(mutants, equalTo(setOf("alice", "bob", "carol")))
    }
}