package com.natpryce.snodge

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.stream.Collectors.toList

class IdentityMutatorTest {
    @Test
    fun createsASingleNonmutationOfOriginal() {
        val m = Mutator.id<Int>()
        
        val original = 8
        
        val actualMutations: List<Int> = m.mutate(original, 100).collect(toList<Int>())
        val expectedMutations : List<Int> = listOf(original)
        
        assertThat("mutations", actualMutations, equalTo(expectedMutations))
    }
}
