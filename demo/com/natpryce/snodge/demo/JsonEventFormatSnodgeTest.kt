package com.natpryce.snodge.demo

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonString
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.json.addObjectProperty
import com.natpryce.snodge.json.defaultJsonMutagens
import com.natpryce.snodge.json.forStrings
import com.natpryce.snodge.json.reorderObjectProperties
import com.natpryce.snodge.mutants
import org.junit.Test
import java.io.IOException
import java.net.URI
import java.util.Random

class JsonEventFormatSnodgeTest {
    
    // uncomment one of the formats below to see the test detect defects
//    val format = ShonkyJsonEventFormat();
//    val format = ReflectomagicJsonEventFormat()
    val format = RobustJsonEventFormat()
    
    internal val originalEvent = ServiceEvent(
        System.currentTimeMillis(),
        URI.create("iotp://192.168.1.47/sensor/temp"),
        FAILED("testing"))
    
    @Test
    fun parsesEventSuccessfullyOrThrowsIOException() {
        Random().mutants(defaultJsonMutagens().forStrings(), mutationCount, format.serialise(originalEvent)).forEach { s ->
            assertParsesEventSuccessfullyOrThrowsIOException(s)
        }
    }
    
    @Test
    fun jsonPropertiesCanOccurInAnyOrder() {
        assertSerialisationUnaffectedBy(reorderObjectProperties())
    }
    
    @Test
    fun ignoresUnrecognisedProperties() {
        assertSerialisationUnaffectedBy(addObjectProperty(JsonString("new-property-value")))
    }
    
    private fun assertSerialisationUnaffectedBy(mutagen: Mutagen<JsonElement>) {
        Random().mutants(mutagen.forStrings(), 1000, format.serialise(originalEvent))
            .forEach { assertParsedEventEqualToOriginal(it) }
    }
    
    private fun assertParsedEventEqualToOriginal(json: String) {
        try {
            assertThat("deserialised JSON: " + json, format.deserialise(json), equalTo(originalEvent))
        }
        catch (e: Exception) {
            fail("deserialisation failed for JSON: " + json, e)
        }
    }
    
    private fun assertParsesEventSuccessfullyOrThrowsIOException(json: String) {
        try {
            format.deserialise(json)
        }
        catch (e: IOException) {
            // allowed
        }
        catch (e: Exception) {
            fail("unexpected exception for JSON input: " + json, e)
        }
        
    }
    
    private fun fail(detailMessage: String, e: Exception): Nothing = throw AssertionError(detailMessage, e)
    
    internal val mutationCount = 1000
}
