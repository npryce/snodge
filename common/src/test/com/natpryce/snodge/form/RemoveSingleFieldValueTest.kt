package com.natpryce.snodge.form

import com.natpryce.snodge.Random
import kotlin.test.Test
import kotlin.test.assertEquals


class RemoveSingleFieldValueTest {
    @Test
    fun removes_single_mapping_from_form() {
        val original = form("a" to "a1", "b" to "b1", "a" to "a2")
        
        val mutants = removeSingleFieldValue().invoke(Random(), original).map { it.value }.toSet()
        
        assertEquals(
            setOf(
                form("b" to "b1", "a" to "a2"),
                form("a" to "a1", "a" to "a2"),
                form("a" to "a1", "b" to "b1")
            ),
            mutants)
    }
}
