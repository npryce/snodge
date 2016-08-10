package com.natpryce.snodge.demo

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

import java.io.IOException
import java.io.StringReader
import java.io.StringWriter
import java.net.URI
import java.net.URISyntaxException

class RobustJsonEventFormat {
    fun serialise(event: ServiceEvent): String {
        val serialised = StringWriter()
        
        val writer = JsonWriter(serialised)
        writer.beginObject()
        writer.name("timestamp")
        writer.value(event.timestamp)
        writer.name("service")
        writer.value(event.service.toString())
        writer.name("type")
        writer.value(event.serviceState.toString())
        writer.endObject()
        writer.flush()
        
        return serialised.toString()
    }
    
    fun deserialise(input: String): ServiceEvent {
        val reader = JsonReader(StringReader(input))
        
        expect(reader, JsonToken.BEGIN_OBJECT)
        reader.beginObject()
        
        // Correct: handle properties in any order
        // Verified by JsonEventFormatSnodgeTest.jsonPropertiesCanOccurInAnyOrder
        
        var timestamp: Long? = null
        var service: URI? = null
        var serviceState: ServiceState? = null
        
        while (reader.peek() != JsonToken.END_OBJECT) {
            val fieldName = reader.nextName()
            if (fieldName == "timestamp") {
                expect(reader, JsonToken.NUMBER)
                timestamp = reader.nextLong()
            }
            else if (fieldName == "service") {
                expect(reader, JsonToken.STRING)
                try {
                    service = URI(reader.nextString())
                }
                catch (e: URISyntaxException) {
                    throw IOException("invalid URI syntax for $fieldName property")
                }
                
            }
            else if (fieldName == "type") {
                expect(reader, JsonToken.STRING)
                try {
                    serviceState = ServiceState.valueOf(reader.nextString())
                }
                catch (e: IllegalArgumentException) {
                    throw IOException("invalid event type", e)
                }
                
            }
            else {
                reader.skipValue()
            }
        }
        
        expect(reader, JsonToken.END_OBJECT)
        
        if (timestamp == null || service == null || serviceState == null) {
            throw IOException("missing field(s)")
        }
        
        return ServiceEvent(timestamp, service, serviceState)
    }
    
    private fun expect(reader: JsonReader, expectedToken: JsonToken) {
        val currentToken = reader.peek()
        if (currentToken != expectedToken) {
            throw IOException("expected $expectedToken but was $currentToken")
        }
    }
}
