package com.natpryce.jsonk

import kotlin.test.Test
import kotlin.test.assertEquals


class JsonRoundTripTest() {
    @Test
    fun round_trips_JSON() {
        ExampleJsonFiles.forEach { exampleName, original ->
            assertEquals(expected = original, actual = original.toJsonString().toJsonElement(), message = exampleName)
        }
    }
}

