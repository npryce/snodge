package com.natpryce.jsonk

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.io.StringWriter
import javax.json.Json


@RunWith(Parameterized::class)
class JsonRoundTripTest(val exampleName: String) {
    companion object {
        @Parameters(name = "{0}") @JvmStatic
        fun examples() = ExampleJsonFiles.list()
    }
    
    @Test
    fun round_trips_JSON() =
        ExampleJsonFiles.load(exampleName) { original ->
            assertEquals(original, original.toJsonString().toJsonElement())
        }
}

