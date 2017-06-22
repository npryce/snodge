package com.natpryce.snodge.form

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.Random


class AddUniqueFieldTest {
    @Test
    fun `adds field with new value from those given`() {
        val original = form("a" to "1")
        val mutagen = addUniqueField(listOf("10", "11", "12"), basename = "x")
        
        val mutants = mutagen.invoke(Random(), original).map { it.value }.toSet()
        
        assertThat(mutants, equalTo(setOf(
            form("a" to "1", "x" to "10", "x" to "11", "x" to "12")
        )))
    }
    
    @Test
    fun `ensures new field name is different from any existing fields`() {
        val original = form("x" to "1")
        val mutagen = addUniqueField(listOf("10", "11"), basename = "x")
        
        val mutants = mutagen.invoke(Random(), original).map { it.value }.toSet()
        
        assertThat(mutants, equalTo(setOf(
            form("x" to "1", "x_1" to "10", "x_1" to "11")
        )))
    }
}
