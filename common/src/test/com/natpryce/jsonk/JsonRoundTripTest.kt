package com.natpryce.jsonk

import org.junit.Test
import kotlin.test.assertEquals


class JsonRoundTripTest() {
    @Test
    fun round_trips_JSON() {
        ExampleJsonFiles.forEach { exampleName, original ->
            assertEquals(original, original.toJsonString().toJsonElement(), exampleName)
        }
    }
}

