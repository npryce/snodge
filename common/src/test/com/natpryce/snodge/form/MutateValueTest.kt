package com.natpryce.snodge.form

import com.natpryce.snodge.Random
import com.natpryce.snodge.always
import kotlin.test.Test
import kotlin.test.assertEquals


class MutateValueTest {
    @Test
    fun mutate_value_of_form_field() {
        val original = form("a" to "a1", "b" to "b1", "a" to "a2")
        
        val mutants = mutateValue(always("x")).invoke(Random(), original).map { it.value }.toSet()
        
        assertEquals(
            setOf(
                form("a" to "x", "b" to "b1", "a" to "a2"),
                form("a" to "a1", "b" to "x", "a" to "a2"),
                form("a" to "a1", "b" to "b1", "a" to "x")
            ),
            mutants)
    }
}
