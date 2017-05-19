package com.natpryce.snodge.demo

import com.google.gson.JsonPrimitive
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.snodge.json.AddObjectProperty
import com.natpryce.snodge.json.JsonMutagen
import com.natpryce.snodge.json.JsonNodeMutagen
import com.natpryce.snodge.json.ReorderObjectProperties
import com.natpryce.snodge.json.allJsonMutagens
import com.natpryce.snodge.json.forStrings
import com.natpryce.snodge.mutants
import org.junit.Test
import java.io.IOException
import java.net.URI
import java.util.Random

class JsonEventFormatSnodgeTest {
    
    // use JsonEventFormat to see the test detect defects
//    val format = JsonEventFormat();
    val format = RobustJsonEventFormat()
    
    internal var originalEvent = ServiceEvent(
        System.currentTimeMillis(),
        URI.create("iotp://192.168.1.47/sensor/temp"),
        ServiceState.READY)
    
    @Test
    fun parsesEventSuccessfullyOrThrowsIOException() {
        Random().mutants(allJsonMutagens().forStrings(), mutationCount, format.serialise(originalEvent)).forEach {
            assertParsesEventSuccessfullyOrThrowsIOException(it)
        }
    }
    
    @Test
    fun jsonPropertiesCanOccurInAnyOrder() {
        assertSerialisationUnaffectedBy(ReorderObjectProperties())
    }
    
    @Test
    fun ignoresUnrecognisedProperties() {
        assertSerialisationUnaffectedBy(AddObjectProperty(JsonPrimitive("new-property-value")))
    }
    
    private fun assertSerialisationUnaffectedBy(mutagen: JsonNodeMutagen) {
        Random().mutants(JsonMutagen(mutagen).forStrings(), 1000, format.serialise(originalEvent))
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
    
    internal val mutationCount = 100
}
