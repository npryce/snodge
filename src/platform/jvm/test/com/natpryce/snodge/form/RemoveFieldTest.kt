package com.natpryce.snodge.form

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.Random

class RemoveFieldTest {
    @Test
    fun `removes all mappings for a field`() {
        val original = form(
            "a" to "a1",
            "b" to "b1",
            "c" to "c1",
            "a" to "a2",
            "b" to "b2",
            "b" to "b3"
        )
        
        val mutants = removeField().invoke(Random(), original).map { it.value }.toSet()
        
        assertThat(mutants, equalTo(setOf(
            form(
                "b" to "b1",
                "c" to "c1",
                "b" to "b2",
                "b" to "b3"
            ),
            form(
                "a" to "a1",
                "c" to "c1",
                "a" to "a2"
            ),
            form(
                "a" to "a1",
                "b" to "b1",
                "a" to "a2",
                "b" to "b2",
                "b" to "b3"
            )
        )))
    }
}