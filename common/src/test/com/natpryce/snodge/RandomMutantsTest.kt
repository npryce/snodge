package com.natpryce.snodge

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RandomMutantsTest {
    val rng = Random.Default
    val mutagen = { _: Random, original: String -> sequenceOf(lazyOf(original+"-1"), lazyOf(original+"-2")) }
    
    @Test
    fun random_mutants_of_multiple_originals() {
        val mutants = rng.mutants(mutagen, 4, listOf("x", "y"))
        assertEquals(setOf("x-1", "x-2", "y-1", "y-2"), mutants.toSet())
    }
    
    @Test
    fun random_mutants_of_one_original() {
        val mutants = rng.mutants(mutagen, 4, "x")
        assertEquals(setOf("x-1", "x-2"), mutants.toSet())
    }
    
    @Test
    fun one_mutant() {
        val mutant = rng.mutant(mutagen, "x")
        assertTrue(mutant == "x-1" || mutant == "x-2")
    }
}
