package com.natpryce.jsonk

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.io.File
import java.io.StringWriter
import javax.json.Json


@RunWith(Parameterized::class)
class JsonRoundTripTests(val exampleResourceName: String) {
    companion object {
        @Parameters(name = "{0}") @JvmStatic
        fun examples(): List<String> =
            File(JsonRoundTripTests::class.java.getResource(".").path)
                .listFiles { f -> f.name.endsWith(".json") }
                .map { it.name }
    }
    
    @Test
    fun `round trips JSON`() {
        val roundTrippedViaJavaxJson = StringWriter().also { sw ->
            Json.createWriter(sw).write(
                Json.createReader(exampleResourceReader()).use { r -> r.readValue() })
        }.toString()
        
        val roundTrippedViaJsonK = exampleResourceReader().use { it.readJsonElement() }.toJsonString()
        
        assertEquals(roundTrippedViaJavaxJson, roundTrippedViaJsonK)
    }
    
    private fun exampleResourceReader() =
        javaClass.getResourceAsStream(exampleResourceName).reader()
}
