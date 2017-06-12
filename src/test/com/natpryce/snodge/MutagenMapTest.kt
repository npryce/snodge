package com.natpryce.snodge

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.Random


class MutagenMapTest {
    @Test
    fun `applies a function once to the original`() {
        val mutagen = map<Int> { n -> -n }
        
        assertThat(mutagen(Random(), 10).toList().map { it.value }, equalTo(listOf(-10)))
    }
}
