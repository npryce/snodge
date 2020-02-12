package com.natpryce.snodge

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals


class MutagenMapTest {
    @Test
    fun applies_a_function_once_to_the_original() {
        val mutagen = map<Int> { n -> -n }
        
        assertEquals(listOf(-10), mutagen(Random.Default, 10).toList().map { it.value })
    }
    
    @Test
    fun for_strings() {
        val mutagen = map(String::reversed)
        
        assertEquals(listOf("cba"), mutagen(Random.Default, "abc").toList().map { it.value })
    }
}
