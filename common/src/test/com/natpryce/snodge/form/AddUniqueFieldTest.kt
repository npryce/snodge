package com.natpryce.snodge.form

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals


class AddUniqueFieldTest {
    @Test
    fun adds_field_with_new_value_from_those_given() {
        val original = form("a" to "1")
        val mutagen = addUniqueField(listOf("10", "11", "12"), basename = "x")
        
        val mutants = mutagen(Random.Default, original).map { it.value }.toSet()
        
        assertEquals(setOf(form("a" to "1", "x" to "10", "x" to "11", "x" to "12")), mutants)
    }
    
    @Test
    fun ensures_new_field_name_is_different_from_any_existing_fields() {
        val original = form("x" to "1")
        val mutagen = addUniqueField(listOf("10", "11"), basename = "x")
        
        val mutants = mutagen(Random.Default, original).map { it.value }.toSet()
        
        assertEquals(setOf(form("x" to "1", "x_1" to "10", "x_1" to "11")), mutants)
    }
}
