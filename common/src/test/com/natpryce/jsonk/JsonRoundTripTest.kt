package com.natpryce.jsonk

import org.junit.Test
import kotlin.test.assertEquals


class JsonRoundTripTest() {
    @Test
    fun round_trips_JSON() {
        ExampleJsonFiles.forEach { exampleName, original ->
            assertEquals(expected = original, actual = original.toJsonString().toJsonElement(), message = exampleName)
        }
    }
}

