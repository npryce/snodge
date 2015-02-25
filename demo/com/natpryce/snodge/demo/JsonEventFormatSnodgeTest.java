package com.natpryce.snodge.demo;

import com.google.gson.JsonPrimitive;
import com.natpryce.snodge.JsonMutator;
import com.natpryce.snodge.Mutagen;
import com.natpryce.snodge.mutagens.AddObjectProperty;
import com.natpryce.snodge.mutagens.ReorderObjectProperties;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class JsonEventFormatSnodgeTest {
    static final int mutationCount = 100;

//    JsonEventFormat format = new JsonEventFormat();
    // Replace the line below with the line above to see the test catch failures
    RobustJsonEventFormat format = new RobustJsonEventFormat();

    ServiceEvent originalEvent = new ServiceEvent(
            System.currentTimeMillis(),
            URI.create("iotp://192.168.1.47/sensor/temp"),
            ServiceEvent.Type.READY);

    @Test
    public void parsesEventSuccessfullyOrThrowsIOException() throws IOException {
        new JsonMutator().forStrings().mutate(format.serialise(originalEvent), mutationCount)
                .forEach(this::assertParsesEventSuccessfullyOrThrowsIOException);
    }

    @Test
    public void jsonPropertiesCanOccurInAnyOrder() throws IOException {
        assertSerialisationUnaffectedBy(new ReorderObjectProperties());
    }

    @Test
    public void ignoresUnrecognisedProperties() throws IOException {
        assertSerialisationUnaffectedBy(new AddObjectProperty(new JsonPrimitive("new-property-value")));
    }

    private void assertSerialisationUnaffectedBy(Mutagen mutagen) throws IOException {
        new JsonMutator(mutagen).forStrings().mutate(format.serialise(originalEvent), mutationCount)
                .forEach(this::assertParsedEventEqualToOriginal);
    }

    private void assertParsedEventEqualToOriginal(String json) {
        try {
            assertThat("deserialised JSON: " + json, format.deserialise(json), equalTo(originalEvent));
        }
        catch (Exception e) {
            fail("deserialisation failed for JSON: " + json, e);
        }
    }

    private void assertParsesEventSuccessfullyOrThrowsIOException(String json) {
        try {
            format.deserialise(json);
        }
        catch (IOException e) {
            // allowed
        }
        catch (Exception e) {
            fail("unexpected exception for JSON input: " + json, e);
        }
    }

    private void fail(String detailMessage, Exception e) {
        AssertionError failure = new AssertionError(detailMessage);
        failure.initCause(e);
        throw failure;
    }
}
