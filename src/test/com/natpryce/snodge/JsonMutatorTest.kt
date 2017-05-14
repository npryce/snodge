package com.natpryce.snodge

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.present
import com.natpryce.snodge.mutagens.AddArrayElement
import com.natpryce.snodge.mutagens.AddObjectProperty
import org.junit.Assert.assertTrue
import org.junit.Test
import java.nio.charset.Charset
import java.util.*

class JsonMutatorTest {
    internal var rng = Random()
    
    private val mutagen = combine(
        AddObjectProperty(JsonNull.INSTANCE),
        AddArrayElement(JsonNull.INSTANCE))
    
    @Test
    fun canAddNullObjectProperty() {
        val doc = obj(
            withField("alice", 1),
            withField("bob", 2))
        
        val mutations = rng.mutateJson(doc, 1, mutagen)
        
        assertThat("should only be one mutation", mutations.size, equalTo(1))
        
        assertThat(mutations.first(), equalTo(obj(
            withField("alice", 1),
            withField("bob", 2),
            withNullField("x")) as JsonElement))
    }
    
    @Test
    fun canAddNullArrayProperty() {
        val doc = list(1, 2, 3)
        
        val mutations = rng.mutateJson(doc, 1, mutagen)
        
        assertThat("should be one mutation",
            mutations, equalTo<List<JsonElement>>(listOf(list(1, 2, 3, null))))
    }
    
    @Test
    fun canReturnMultipleMutations() {
        val doc = obj(
            withField("num", 1),
            withField("list", list(1, 2, 3)))
        
        val mutatedDocs = rng.mutateJson(doc, 2, mutagen)
        
        assertThat("number of mutations", mutatedDocs.size, equalTo(2))
        
        assertTrue(mutatedDocs.contains(obj(
            withField("num", 1),
            withField("list", list(1, 2, 3)),
            withNullField("x"))))
        
        assertTrue(mutatedDocs.contains(obj(
            withField("num", 1),
            withField("list", list(1, 2, 3, null)))))
    }
    
    @Test
    fun returnsARandomSampleOfAllPossibleMutations() {
        val doc = list(list(1, 2), list(list(3, 4), list(5, 6, list(7, 8)), list(9, 10)), list(11, 12))
        
        rng.setSeed(0)
        val mutatedDocs = rng.mutateJson(doc, 2, mutagen)
        
        assertThat("number of mutations", mutatedDocs.size, equalTo(2))
        
        rng.setSeed(99)
        assertThat(rng.mutateJson(doc, 2, mutagen), !equalTo(rng.mutateJson(doc, 2, mutagen)))
    }
    
    @Test
    fun willNotReturnMoreMutationsThanCanBeGeneratedByTheMutagens() {
        val doc = list(list(1, 2), list(3, 4))
        
        assertThat("number of mutations", rng.mutateJson(doc, 10, mutagen).size, equalTo(3))
    }
    
    @Test
    fun canMutateJsonText() {
        val gson = Gson()
        val original = gson.toJson(obj(
            withField("num", 1),
            withField("list", list(1, 2, 3))))
        
        val mutated = rng.mutateJson(original, 1, mutagen).first()
        
        assertThat(mutated, !equalTo(original))
    
        gson.canParse(mutated)
    }
    
    @Test
    fun canMutateEncodedJsonText() {
        val charset = Charset.forName("UTF-8")
        
        val gson = Gson()
        val originalString = gson.toJson(obj(
            withField("num", 1),
            withField("list", list(1, 2, 3))))
        
        val originalBytes = originalString.toByteArray(charset)
        val mutatedBytes = rng.mutateEncodedJson(originalBytes, charset, 2).first()
        
        assertThat(mutatedBytes, !equalTo(originalBytes))
        
        val mutatedString = String(mutatedBytes, charset)
        
        gson.canParse(mutatedString)
    }
    
    private fun Gson.canParse(mutated: String) {
        // Does not blow up
        fromJson(mutated, JsonElement::class.java)
    }
}
