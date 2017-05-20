package com.natpryce.snodge.demo

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

import java.io.IOException
import java.io.StringReader
import java.io.StringWriter
import java.net.URI
import java.net.URISyntaxException

class RobustJsonEventFormat : EventFormat {
    override fun serialise(event: ServiceEvent): String {
        val serialised = StringWriter()
        
        val writer = JsonWriter(serialised)
        writer.beginObject()
        writer.name("timestamp")
        writer.value(event.timestamp)
        writer.name("service")
        writer.value(event.service.toString())
        writer.name("type")
        when (event.serviceState) {
            STARTING -> writer.value("STARTING")
            READY -> writer.value("READY")
            STOPPING -> writer.value("STOPPING")
            STOPPED -> writer.value("STOPPED")
            is FAILED -> {
                writer.value("FAILED")
                writer.name("error")
                writer.value(event.serviceState.error)
            }
        }.let { /* ensure exhaustive */ }
        writer.endObject()
        writer.flush()
        
        return serialised.toString()
    }
    
    override fun deserialise(input: String): ServiceEvent {
        val reader = JsonReader(StringReader(input))
        
        expect(reader, JsonToken.BEGIN_OBJECT)
        reader.beginObject()
        
        // Correct: handle properties in any order
        // Verified by JsonEventFormatSnodgeTest.jsonPropertiesCanOccurInAnyOrder
        
        var timestamp: Long? = null
        var service: URI? = null
        var serviceStateType: String? = null
        var error: String? = null
        
        while (reader.peek() != JsonToken.END_OBJECT) {
            val fieldName = reader.nextName()
            when (fieldName) {
                "timestamp" -> {
                    expect(reader, JsonToken.NUMBER)
                    timestamp = reader.nextLong()
                }
                "service" -> {
                    expect(reader, JsonToken.STRING)
                    try {
                        service = URI(reader.nextString())
                    }
                    catch (e: URISyntaxException) {
                        throw IOException("invalid URI syntax for $fieldName property")
                    }
                }
                "type" -> {
                    expect(reader, JsonToken.STRING)
                    serviceStateType = reader.nextString()
                }
                "error" -> {
                    expect(reader, JsonToken.STRING)
                    error = reader.nextString()
                }
                else -> reader.skipValue()
            }
        }
        
        expect(reader, JsonToken.END_OBJECT)
        
        if (timestamp == null || service == null || serviceStateType == null) {
            missingFields()
        }
        
        val serviceState = when (serviceStateType) {
            "STARTING" -> STARTING
            "READY" -> READY
            "STOPPING" -> STOPPING
            "STOPPED" -> STOPPED
            "FAILED" -> error?.let(::FAILED) ?: missingFields()
            else -> throw IOException("unknown service state: $serviceStateType")
        }
        
        return ServiceEvent(timestamp, service, serviceState)
    }
    
    private fun missingFields(): Nothing {
        throw IOException("missing field(s)")
    }
    
    private fun expect(reader: JsonReader, expectedToken: JsonToken) {
        val currentToken = reader.peek()
        if (currentToken != expectedToken) {
            throw IOException("expected $expectedToken but was $currentToken")
        }
    }
}
