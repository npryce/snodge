package com.natpryce.snodge.demo;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

public class RobustJsonEventFormat {
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

        // Correct: handle properties in any order
        // Verified by JsonEventFormatSnodgeTest.jsonPropertiesCanOccurInAnyOrder

        Long timestamp = null;
        URI service = null;
        ServiceEvent.Type eventType = null;

        while (reader.peek() != JsonToken.END_OBJECT) {
            String fieldName = reader.nextName();
            if (fieldName.equals("timestamp")) {
                expect(reader, JsonToken.NUMBER);
                timestamp = reader.nextLong();
            }
            else if (fieldName.equals("service")) {
                expect(reader, JsonToken.STRING);
                try {
                    service = new URI(reader.nextString());
                } catch (URISyntaxException e) {
                    throw new IOException("invalid URI syntax for " + fieldName + " property");
                }
            }
            else if (fieldName.equals("type")) {
                expect(reader, JsonToken.STRING);
                try {
                    eventType = ServiceEvent.Type.valueOf(reader.nextString());
                }
                catch (IllegalArgumentException e) {
                    throw new IOException("invalid event type", e);
                }
            }
            else {
                reader.skipValue();
            }
        }

        expect(reader, JsonToken.END_OBJECT);

        if (timestamp == null || service == null || eventType == null) {
            throw new IOException("missing field(s)");
        }

        return new ServiceEvent(timestamp, service, eventType);
    }

    private void expect(JsonReader reader, JsonToken expectedToken) throws IOException {
        final JsonToken currentToken = reader.peek();
        if (currentToken != expectedToken) {
            throw new IOException("expected " + expectedToken + " but was " + currentToken);
        }
    }
}
