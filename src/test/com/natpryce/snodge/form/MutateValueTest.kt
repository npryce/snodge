package com.natpryce.snodge.form

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.snodge.always
import org.junit.Test
import java.util.Random


class MutateValueTest {
    @Test
    fun `mutate value of form field`() {
        val original = form("a" to "a1", "b" to "b1", "a" to "a2")
    
        val mutants = mutateValue(always("x")).invoke(Random(), original).map { it.value }.toSet()
        
        assertThat(mutants, equalTo(setOf(
            form("a" to "x", "b" to "b1", "a" to "a2"),
            form("a" to "a1", "b" to "x", "a" to "a2"),
            form("a" to "a1", "b" to "b1", "a" to "x")
        )))
    }
    
}
