package com.natpryce.snodge.form

import com.natpryce.snodge.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class RemoveFieldTest {
    @Test
    fun removes_all_mappings_for_a_field() {
        val original = form(
            "a" to "a1",
            "b" to "b1",
            "c" to "c1",
            "a" to "a2",
            "b" to "b2",
            "b" to "b3"
        )
        
        val mutants = removeField().invoke(Random(), original).map { it.value }.toSet()
        
        assertEquals(
            setOf(
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
            ),
            mutants)
    }
}