package com.natpryce.snodge

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.natpryce.snodge.mutagens.ReplaceJsonElement
import org.hamcrest.Matchers.equalTo
import org.hamcrest.junit.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test

class MutagenAtPathTest {
    @Test
    @Throws(Exception::class)
    fun canLimitMutagenToPath() {
        val mutator = JsonMutator(
            atPath(JsonPath.of("a", "b"), ReplaceJsonElement(JsonPrimitive("XXX"))))
        
        val doc = obj(
            withField("a", obj(
                withField("b", 1),
                withField("c", 2))),
            withField("d", obj(
                withField("b", 1),
                withField("c", 2))))
        
        val mutant = mutator.mutate(doc, 1).first()
        
        assertThat(mutant, equalTo(obj(
            withField("a", obj(
                withField("b", "XXX"),
                withField("c", 2))),
            withField("d", obj(
                withField("b", 1),
                withField("c", 2)))) as JsonElement))
    }
    
    @Test
    @Throws(Exception::class)
    fun canLimitMutagenToPathsByPredicate() {
        val mutator = JsonMutator(
            atPath(JsonPath.functions.endsWith("b"), ReplaceJsonElement(JsonPrimitive("XXX"))))
        
        val doc = obj(
            withField("a", obj(
                withField("b", 1),
                withField("c", 2))),
            withField("d", obj(
                withField("b", 1),
                withField("c", 2))))
        
        val mutations = mutator.mutate(doc, 2)
        
        assertTrue("a.b mutated", mutations.contains(
            obj(
                withField("a", obj(
                    withField("b", "XXX"),
                    withField("c", 2))),
                withField("d", obj(
                    withField("b", 1),
                    withField("c", 2))))))
        
        assertTrue("d.b mutated", mutations.contains(
            obj(
                withField("a", obj(
                    withField("b", 1),
                    withField("c", 2))),
                withField("d", obj(
                    withField("b", "XXX"),
                    withField("c", 2))))))
    }
}
