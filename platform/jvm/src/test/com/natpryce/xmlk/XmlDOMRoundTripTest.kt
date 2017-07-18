package com.natpryce.xmlk

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters


@RunWith(Parameterized::class)
class XmlDOMRoundTripTest(val exampleName: String) {
    @Test
    fun `round trips XML`() =
        ExampleXmlFiles.load(exampleName) { original ->
            assertEquals(original, original.toDOM().toXmlDocument())
        }
    
    companion object {
        @Parameters(name = "{0}") @JvmStatic
        fun examples() = ExampleXmlFiles.list()
    }
}
