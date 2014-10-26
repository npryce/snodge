package com.natpryce.snodge.demo;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;

/*
 * Defects in this class are corrected in the RobustJsonEventFormat
 */
public class JsonEventFormat  {
    public String serialise(ServiceEvent event) throws IOException {
        StringWriter serialised = new StringWriter();

        JsonWriter writer = new JsonWriter(serialised);
        writer.beginObject();
        writer.name("timestamp");
        writer.value(event.timestamp);
        writer.name("service");
        writer.value(event.service.toString());
        writer.name("type");
        writer.value(event.type.toString());
        writer.endObject();
        writer.flush();

        return serialised.toString();
    }

    public ServiceEvent deserialise(String input) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(input));

        expect(reader, JsonToken.BEGIN_OBJECT);
        reader.beginObject();

        // Defect: cannot handle properties in any order
        // Caught by JsonEventFormatSnodgeTest.jsonPropertiesCanOccurInAnyOrder

        expectProperty(reader, "timestamp", JsonToken.NUMBER);
        Long timestamp = reader.nextLong();

        expectProperty(reader, "service", JsonToken.STRING);
        // Defect: throws IllegalArgumentException on invalid URI syntax.
        // Caught by JsonEventFormatSnodgeTest.parsesEventSuccessfullyOrThrowsIOException
        URI service = URI.create(reader.nextString());

        expectProperty(reader, "type", JsonToken.STRING);
        // Defect: throws IllegalArgumentException on unrecognised enum value.  Caught by
        // Caught by JsonEventFormatSnodgeTest.parsesEventSuccessfullyOrThrowsIOException
        ServiceEvent.Type eventType = ServiceEvent.Type.valueOf(reader.nextString());

        // Defect: fails if new fields added to protocol.
        // Caught by JsonEventFormatSnodgeTest.ignoresUnrecognisedProperties
        expect(reader, JsonToken.END_OBJECT);

        return new ServiceEvent(timestamp, service, eventType);
    }

    private void expectProperty(JsonReader reader, String expectedName, JsonToken expectedJsonType) throws IOException {
        if (reader.peek() != JsonToken.NAME || !reader.nextName().equals(expectedName) || reader.peek() != expectedJsonType) {
            throw new IOException("expected property named " + expectedName + " of type " + expectedJsonType.name());
        }
    }

    private void expect(JsonReader reader, JsonToken expectedToken) throws IOException {
        final JsonToken currentToken = reader.peek();
        if (currentToken != expectedToken) {
            throw new IOException("expected " + expectedToken + " but was " + currentToken);
        }
    }
}
