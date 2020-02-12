package com.natpryce.snodge

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class MutagenCombineTest {
    @Test
    fun combines_two_mutagens_returning_mutants_from_all_of_them() {
        val mutagen = always("alice") + always("bob") + always("carol")
        val mutants = mutagen(Random.Default, "original").map { it.value }.toSet()
        
        assertEquals(setOf("alice", "bob", "carol"), mutants)
    }
}