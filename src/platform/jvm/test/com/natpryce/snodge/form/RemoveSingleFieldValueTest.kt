package com.natpryce.snodge.form

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.Random


class RemoveSingleFieldValueTest {
    @Test
    fun `removes single mapping from form`() {
        val original = form("a" to "a1", "b" to "b1", "a" to "a2")
        
        val mutants = removeSingleFieldValue().invoke(Random(), original).map { it.value }.toSet()
        
        assertThat(mutants, equalTo(setOf(
            form("b" to "b1", "a" to "a2"),
            form("a" to "a1", "a" to "a2"),
            form("a" to "a1", "b" to "b1")
        )))
    }
}
