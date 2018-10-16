package com.natpryce.snodge

import kotlin.test.Test
import kotlin.test.assertEquals


class MutagenAndTest {
    @Test
    fun for_each_mutant_returns_the_mutant_and_further_mutations_of_the_mutant() {
        val mutagen = always("alice", "bob")
            .and { _, mutant -> sequenceOf(lazy { mutant.toUpperCase() }, lazy { mutant.substring(0, 1) }) }
        
        val mutants = mutagen(Random(), "original").map { it.value }.toSet()
        
        assertEquals(
            setOf(
                "alice", "ALICE", "a",
                "bob", "BOB", "b"
            ),
            mutants)
    }
}
