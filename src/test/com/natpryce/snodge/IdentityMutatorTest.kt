package com.natpryce.snodge

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class IdentityMutatorTest {
    @Test
    fun createsASingleNonmutationOfOriginal() {
        val m = id<Int>()
        
        val original = 8
        
        val actualMutations: List<Int> = m.invoke(original, 100)
        val expectedMutations : List<Int> = listOf(original)
        
        assertThat("mutations", actualMutations, equalTo(expectedMutations))
    }
}
