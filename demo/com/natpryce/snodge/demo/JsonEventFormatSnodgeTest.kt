package com.natpryce.snodge.demo

import com.google.gson.JsonPrimitive
import com.natpryce.snodge.JsonMutator
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.forStrings
import com.natpryce.snodge.mutagens.AddObjectProperty
import com.natpryce.snodge.mutagens.ReorderObjectProperties
import org.hamcrest.Matchers.equalTo
import org.hamcrest.junit.MatcherAssert.assertThat
import org.junit.Test
import java.io.IOException
import java.net.URI

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
        JsonMutator().forStrings().mutate(format.serialise(originalEvent), mutationCount).forEach {
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
    
    private fun assertSerialisationUnaffectedBy(mutagen: Mutagen) {
        JsonMutator(mutagen).forStrings()
            .mutate(format.serialise(originalEvent), mutationCount)
            .forEach {
                assertParsedEventEqualToOriginal(it)
            }
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
    
    private fun fail(detailMessage: String, e: Exception) {
        throw AssertionError(detailMessage, e)
    }
    
    internal val mutationCount = 100
}
