package com.natpryce.snodge.json

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Random


class MutagenAtPathTest {
    private val random = Random()
    
    @Test
    fun `can limit mutagen to path`() {
        val mutagen = replaceJsonElement(JsonPrimitive("XXX")).atPath("a", "b")
    
        val doc = obj(
            "a" to obj(
                "b" to 1,
                "c" to 2),
            "d" to obj(
                "b" to 1,
                "c" to 2))
        
        val mutant = mutagen(random, doc).first().value
        
        assertThat(mutant, equalTo<JsonElement>(obj(
            "a" to obj(
                "b" to "XXX",
                "c" to 2),
            "d" to obj(
                "b" to 1,
                "c" to 2))))
    }
    
    @Test
    fun `can limit mutagen to paths by predicate`() {
        val mutagen = replaceJsonElement(JsonPrimitive("XXX")).atPath {it.endsWith("b")}
        
        val doc = obj(
            "a" to obj(
                "b" to 1,
                "c" to 2),
            "d" to obj(
                "b" to 1,
                "c" to 2))
        
        val mutations = mutagen(random, doc).take(2).map { it.value }
        
        assertTrue("a.b mutated", mutations.contains(
            obj(
                "a" to obj(
                    "b" to "XXX",
                    "c" to 2),
                "d" to obj(
                    "b" to 1,
                    "c" to 2))))
        
        assertTrue("d.b mutated", mutations.contains(
            obj(
                "a" to obj(
                    "b" to 1,
                    "c" to 2),
                "d" to obj(
                    "b" to "XXX",
                    "c" to 2))))
    }
}
