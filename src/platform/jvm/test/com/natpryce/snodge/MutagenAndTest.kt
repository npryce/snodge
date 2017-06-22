package com.natpryce.snodge

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.Random


class MutagenAndTest {
    @Test
    fun `for each mutant, returns the mutant and further mutations of the original mutant`() {
        val mutagen = always("alice", "bob")
            .and { _ , mutant -> sequenceOf(lazy { mutant.toUpperCase() }, lazy {mutant.substring(0,1) }) }
        
        val mutants = mutagen(Random(), "original").map { it.value }.toSet()
        
        assertThat(mutants, equalTo(setOf(
            "alice", "ALICE", "a",
            "bob", "BOB", "b"
        )))
    }
}
